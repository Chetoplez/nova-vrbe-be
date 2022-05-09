package com.novavrbe.vrbe.business;


import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.enumerations.Roles;
import com.novavrbe.vrbe.models.enumerations.Status;
import com.novavrbe.vrbe.models.guildcontroller.*;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.repositories.impl.GuildRepositoryService;
import com.novavrbe.vrbe.utils.GuildUtils;
import com.novavrbe.vrbe.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class GuildBusiness {

    //TODO aggiungere a tutti i metodi gestionali l'ID dell character che sta eseguendo,in modo da verificare che possegga questo diritto.

    @Autowired
    private GuildRepositoryService guildRepositoryService;

    @Autowired
    private CharacterRepositoryService characterRepositoryService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private Environment env;


    /**
     * Torna l'elenco di tutte le gilde presenti per poterle poi visitare.
     *
     * @return lista di Guild item
     */
    public ResponseEntity<GetAllGuildResponse> getAllGuilds() {
        ResponseEntity<GetAllGuildResponse> response;
        Iterable<GuildDTO> allGuilds =  guildRepositoryService.getAllGuild();
        List<Guild> guildList = GuildUtils.prepareGuildList(allGuilds);
        GetAllGuildResponse res = new GetAllGuildResponse();
        res.setGuilds(guildList);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return  response;
    }

    /**
     * Torna le informazioni della gilda
     * @param guildId L'id della gilda
     * @return GuildResponse contenente tutto quello che concerne la gilda
     */
    public ResponseEntity<GetGuildResponse> getGuild(String guildId) {
        ResponseEntity<GetGuildResponse> response;
        Guild guild;
        GuildDTO guildDTO;
        GuildBankDTO guildBankDTO;

        if(!StringUtils.hasText(guildId) ){
            response = new ResponseEntity<>(new GetGuildResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        guildDTO = guildRepositoryService.getGuildById(Integer.parseInt(guildId));
        guildBankDTO = guildRepositoryService.getGuildBank(Integer.parseInt(guildId));
        if(guildDTO != null && guildBankDTO != null){
            guild = GuildUtils.createGuildObject(guildDTO,guildBankDTO);
            GetGuildResponse guildResponse = new GetGuildResponse();
            guildResponse.setGuild(guild);
            response = new ResponseEntity<>(guildResponse, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(new GetGuildResponse(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Torna i ruoli di una determinata gilda con tutti i loro attributi
     * @param guildId l'id della gilda per cui si vuole la gerarchia
     * @return una lista dei ruoli con i loro attributi
     */
    public ResponseEntity<GetGuildRoleReponse> getGuildRole(String guildId){
        ResponseEntity<GetGuildRoleReponse> response;
        if(!StringUtils.hasText(guildId)){
            response = new ResponseEntity<>(new GetGuildRoleReponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        List<GuildRoleDTO> roleDTO = guildRepositoryService.getRoleByGuildId(Integer.parseInt(guildId));
        List<GuildRole> guildRoleList =  GuildUtils.prepareGuildRoles(roleDTO);
        GetGuildRoleReponse res = new GetGuildRoleReponse();
        res.setGuildRoleList(guildRoleList);
        response = new ResponseEntity<>(res, HttpStatus.OK);

    return  response;
    }

    /**
     * Torna i membri di una gilda
     * @param guildId L'id della gilda
     * @return I membri di una gilda con le info.
     */
    public ResponseEntity<GetGuildMemberResponse> getGuildMembers(String guildId){
        ResponseEntity<GetGuildMemberResponse> response;
        List<GuildMember> members = new ArrayList<>();
        if(!StringUtils.hasText(guildId)){
            response = new ResponseEntity<>(new GetGuildMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        List<V_GuildMembers> membersDTO = guildRepositoryService.getGuildMembers(Integer.parseInt(guildId));
        GetGuildMemberResponse res = new GetGuildMemberResponse();
        for (V_GuildMembers tmp: membersDTO) {
            GuildMember newMember = GuildUtils.getMemberfromDTO(tmp);
            members.add(newMember);
        }

        res.setMembers(members);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return  response;
    }

    /**
     * Torna le informazioni riguardo al ruolo ricoperto in una gilda dal pg
     * @param chId il mio ID
     * @return torna un oggetto GuildMember
     */
    public ResponseEntity<GetinfoRoleResponse> getInfoRole(String chId){
        ResponseEntity<GetinfoRoleResponse> response;
        if(!StringUtils.hasText(chId)){
            response = new ResponseEntity<>(new GetinfoRoleResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        V_GuildMembers member = guildRepositoryService.getGuildMember(Integer.parseInt(chId));
        GuildMember newMember = GuildUtils.getMemberfromDTO(member);
        GetinfoRoleResponse res = new GetinfoRoleResponse();
        res.setMember(newMember);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    /**
     * Inserisce un personaggio in una gilda, solo se è DISOCCUPATO. Se il pg è uno SCHIAVO, può entrare solo nei medici o nel foro.
     * @param addMemberRequest oggetto contentente i parametri per pruomuovere un character. il suo iD e l'ìd della gilda
     * @return TRUE se viene inserito, false in tutti gli altri casi
     */
    public ResponseEntity<AddMemberResponse> addMember(AddMemberRequest addMemberRequest){
        ResponseEntity<AddMemberResponse> response;
        AddMemberResponse addMemberResponse =  new AddMemberResponse();
        String characterId,roleId;
        boolean isSlave = false;
        characterId = addMemberRequest.getCharacter_id();
        roleId = addMemberRequest.getRole_id();
        if(!StringUtils.hasText(roleId) || !StringUtils.hasText(characterId) ){
            response = new ResponseEntity<>(addMemberResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        //verifico che non si stia provando ad arruolare uno schiavo in una delle corporazioni a loro vietate
        isSlave = isSlaveCharacter(Integer.parseInt(characterId));
        if(isSlave){
          GuildRoleDTO dto =  guildRepositoryService.getRoleById(Integer.parseInt(roleId));
          String idguilds = env.getProperty("guild.forbidden.slave");
          String[] test = idguilds.split("\\s*,\\s*");
          boolean match = Arrays.asList(test).contains(dto.getGuildId().toString());
          if(match){
              addMemberResponse.setMessage("Non puoi arruolare uno schiavo in questa corporazione");
              addMemberResponse.setAdded(false);
              response = new ResponseEntity<>(addMemberResponse, HttpStatus.OK);
              return response;
          }
        }
        //Controllo se ci sono tutti i permessi di fare questa operazione
        if(hasManagerRight(Integer.parseInt(addMemberRequest.getExecutorId())) ||  checkAdminRight(Integer.parseInt(addMemberRequest.getExecutorId()) ) ){
            //vedo se il pg è già dentro una gilda, come? lo cerco tra i guildMembers!
            if(!guildRepositoryService.checkEnrollment(Integer.parseInt(characterId)) ){
                //Se sono qui dentro, il pg non è arruolato da nessuna parte.
                AddMemberResponse res = new AddMemberResponse();
                res.setAdded(guildRepositoryService.addMember(Integer.parseInt(roleId), Integer.parseInt(characterId)));
                response = new ResponseEntity<>(res,HttpStatus.OK);
            }else {
                AddMemberResponse res = new AddMemberResponse();
                res.setAdded(false);
                response = new ResponseEntity<>(res,HttpStatus.OK);
            }
        }else{
            response = new ResponseEntity<>(new AddMemberResponse(), HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    public ResponseEntity<DeleteMemberResponse> deleteMember(DeleteMemberRequest deleteMemberRequest){
        ResponseEntity<DeleteMemberResponse> response ;
        if(!StringUtils.hasText(deleteMemberRequest.getCharacter_id()) || !StringUtils.hasText(deleteMemberRequest.getRole_id())){
            response = new ResponseEntity<>(new DeleteMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }


        if(hasManagerRight(Integer.parseInt(deleteMemberRequest.getExecutorId())) ||  checkAdminRight(Integer.parseInt(deleteMemberRequest.getExecutorId()) ) ){
            Integer roleId = Integer.parseInt(deleteMemberRequest.getRole_id());
            Integer cId = Integer.parseInt(deleteMemberRequest.getCharacter_id());
            guildRepositoryService.deleteMember(roleId,cId);
            DeleteMemberResponse res = new DeleteMemberResponse();
            res.setDeleted(!guildRepositoryService.checkEnrollment(cId));
            response = new ResponseEntity<>(res,HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(new DeleteMemberResponse(), HttpStatus.UNAUTHORIZED);

        }

        return response;
    }

    /**
     * Promuove un pg nella scala gerarchica della sua Gilda. Se il pg ha lo status di Schiavo, può ragiungere al massimo il livello 30
     * @param promoteMember richiesta contenente l'id del character e il l'id della gilda.
     * @return ritorna true se è stato promosso con successo, false altrimenti
     */
    public ResponseEntity<PromoteMemberResponse> promoteGuildMember(PromoteMemberRequest promoteMember){
        ResponseEntity<PromoteMemberResponse> response;
        PromoteMemberResponse promoteResponse = new PromoteMemberResponse();
        boolean isSlave = false;
        if(!StringUtils.hasText(promoteMember.getGuild_id()) || !StringUtils.hasText(promoteMember.getCharacter_id())){
            response = new ResponseEntity<>(new PromoteMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        isSlave = isSlaveCharacter(Integer.parseInt(promoteMember.getCharacter_id()));
        //se è uno schiavo devo verificare che non abbia raggiunto già il massimo della sua possibilità
        if(isSlave){
            Integer nextLevel = guildRepositoryService.getPossibleNextLevel(Integer.parseInt(promoteMember.getCharacter_id()));
            V_GuildMembers member = guildRepositoryService.getGuildMember(Integer.parseInt(promoteMember.getCharacter_id()));

            if(nextLevel >= Integer.parseInt(env.getProperty("slave.guildLevel.max"))){
                promoteResponse.setPromoted(false);
                promoteResponse.setMessage("Uno schiavo non può andare oltre il grado di "+member.getRoleName());
                response = new ResponseEntity<>(promoteResponse, HttpStatus.OK);
                return response;

            }
        }

        if(hasManagerRight(Integer.parseInt(promoteMember.getExecutorId())) ||  checkAdminRight(Integer.parseInt(promoteMember.getExecutorId()) ) ){
            Integer character_id = Integer.parseInt(promoteMember.getCharacter_id());
            if(guildRepositoryService.promoteMember(character_id)){
                updateCharacterCV(character_id);
                PromoteMemberResponse res = new PromoteMemberResponse();
                res.setPromoted(true);
                response = new ResponseEntity<>(res, HttpStatus.OK);
            }else {
                PromoteMemberResponse res = new PromoteMemberResponse();
                res.setPromoted(false);
                response = new ResponseEntity<>(res, HttpStatus.OK);
            }
        }else {
            response = new ResponseEntity<>(new PromoteMemberResponse(), HttpStatus.UNAUTHORIZED);
            return response;
        }


        return response;
    }


    /**
     * Degrada nella scala gerarchica della gilda di appartenenza un membro
     * @param degradeMemberRequest richiesta formata da roleId e Character id del membro da degradare. Non espelle.
     * @return true se è stato degradato con successo, false altrimenti
     */
    public ResponseEntity<DegradeMemberResponse> degradeGuildMember(DegradateMemberRequest degradeMemberRequest) {
        ResponseEntity<DegradeMemberResponse> response;
        if(!StringUtils.hasText(degradeMemberRequest.getGuild_id()) || !StringUtils.hasText(degradeMemberRequest.getCharacter_id())){
            response = new ResponseEntity<>(new DegradeMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }


        if(hasManagerRight(Integer.parseInt(degradeMemberRequest.getExecutorId())) ||  checkAdminRight(Integer.parseInt(degradeMemberRequest.getExecutorId()) ) ){
            Integer character_id = Integer.parseInt(degradeMemberRequest.getCharacter_id());
            if(guildRepositoryService.degradeMember(character_id)){
                updateCharacterCV(character_id);
                DegradeMemberResponse res = new DegradeMemberResponse();
                res.setDegradated(true);
                response = new ResponseEntity<>(res, HttpStatus.OK);
            }else {
                DegradeMemberResponse res = new DegradeMemberResponse();
                res.setDegradated(false);
                response = new ResponseEntity<>(res, HttpStatus.OK);
            }
        }else {
            response = new ResponseEntity<>(new DegradeMemberResponse(), HttpStatus.UNAUTHORIZED);
            return response;
        }

        return response;
    }

    /**
     * aggiorna il CV di un character.
     * @param character_id
     */
    private void updateCharacterCV(Integer character_id) {
        V_GuildMembers actualRole = guildRepositoryService.getGuildMember(character_id);
        if(actualRole != null){
            guildRepositoryService.updateCharacterCV(character_id,actualRole.getRoleId(),new java.sql.Date(new Date().getTime()));
        }
    }


    /**
     * Verifica che tipo di autorizzazioni ha il tuo personaggio rispetto alla gilda. Se vi appartiene e se ne è il capo
     * @param request
     * @return
     */
    public ResponseEntity<CheckGuildPermissionResponse> checkGuildPermission(CheckGuildPermissionRequest request) {
        ResponseEntity<CheckGuildPermissionResponse> response;
        if(!StringUtils.hasText(request.getCharacterId()) || !StringUtils.hasText(request.getGuildId())){
            response = new ResponseEntity<>(new CheckGuildPermissionResponse(),HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer charachterId = Integer.parseInt(request.getCharacterId());
        Integer guildId = Integer.parseInt(request.getGuildId());

        GuildPermission perm = guildRepositoryService.checkGuildPermission(charachterId,guildId);
        CheckGuildPermissionResponse res = new CheckGuildPermissionResponse();
        res.setManager(perm.isManager());
        res.setPresent(perm.isPresente());
        response = new ResponseEntity<>(res, HttpStatus.OK);

        return response;


    }


    /**
     * Metodo che permette di ritirare dalla banca di Gilda. Ovviamente devi essere autorizzato per poterlo farel
     * @param request
     * @return
     */
    public ResponseEntity<OperationBankResponse> withdraw(OperationBankRequest request) {

        ResponseEntity<OperationBankResponse> response;
        OperationBankResponse res = new OperationBankResponse();
        if(StringUtils.hasText(request.getCharacterId()) && StringUtils.hasText(request.getGuildId()) && StringUtils.hasText(request.getAmount())){
        if(hasManagerRight(Integer.parseInt(request.getCharacterId()))) {
           Boolean withdrawBank = (guildRepositoryService.withdraw(Integer.parseInt(request.getGuildId()), Integer.parseInt(request.getAmount())));
           Boolean depositCharacter = withdrawBank ? characterRepositoryService.deposit(Integer.parseInt(request.getCharacterId()), Integer.parseInt(request.getAmount())) : false;
           res.setOperationOk(withdrawBank && depositCharacter);
            return  new ResponseEntity<>(res, HttpStatus.OK);
         }
        else {
            res.setOperationOk(false);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        } else {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<OperationBankResponse> deposit(OperationBankRequest request) {
        ResponseEntity<OperationBankResponse> response;
        OperationBankResponse res = new OperationBankResponse();
        if (StringUtils.hasText(request.getCharacterId()) && StringUtils.hasText(request.getGuildId()) && StringUtils.hasText(request.getAmount())) {
            if (hasManagerRight(Integer.parseInt(request.getCharacterId()))) {
                Boolean withdrawCharacter = characterRepositoryService.withdraw(Integer.parseInt(request.getCharacterId()), Integer.parseInt(request.getAmount())) ;
                Boolean deposit = withdrawCharacter ? (guildRepositoryService.deposit(Integer.parseInt(request.getGuildId()), Integer.parseInt(request.getAmount()))) : false;
                res.setOperationOk(deposit && withdrawCharacter);
                return  new ResponseEntity<>(res, HttpStatus.OK);
            }
            else {
                res.setOperationOk(false);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Controlla se chi sta effettuando l'azione, ne ha il privilegio.
     * @param executorId il characterId di chi ha chiamato il metodo
     * @return true se sei il manager di quella gilda, false altrimenti
     */

    private boolean hasManagerRight(Integer executorId){
        //dal executorID prendi il character per intero e selezioni il role, con il role entri nella tabella e vedi se è un manager
        V_GuildMembers member = guildRepositoryService.getGuildMember(executorId);
        GuildRoleDTO role = null;
        if(member != null){
              role =  guildRepositoryService.getRoleById(member.getRoleId());
                return role.getIsManager();
        }
        return false;
    }

    /**
     * Torna il curriculum del pg se ne ha uno.
     * @param characterId il character id
     * @return una lista dei ruoli ricorperti con la data di arruolamento
     */
    public ResponseEntity<CharacterCvResponse> getCharacterCv(String characterId) {
        ResponseEntity<CharacterCvResponse> response;
        List<CharacterCv> curriculumPg = new ArrayList<>();
        if(!StringUtils.hasText(characterId)){
            response = new ResponseEntity<>(new CharacterCvResponse(),HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer cId = Integer.parseInt(characterId);
        List<CharacterCvDTO> cvDTOS = guildRepositoryService.getCharacterCv(cId);
        for (CharacterCvDTO dto: cvDTOS ) {
            CharacterCv tmp = new CharacterCv();
            GuildRoleDTO roleInfo;
            roleInfo = guildRepositoryService.getRoleById(dto.getRoleId());
            tmp.setImg(roleInfo.getRole_img());
            tmp.setRoleName(roleInfo.getName());
            tmp.setEnrolmentDate(dto.getEnrollmentDate());
            curriculumPg.add(tmp);
        }

        CharacterCvResponse res = new CharacterCvResponse();
        res.setCurruculumPg(curriculumPg);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    /**
     * Carica lo stipendio base associato al proprio ruolo ricoperto in gilda
     * @param request contiene il characterId del richiedente
     * @return true se tutto apposto, false altrimenti e un messaggio
     **/
    public ResponseEntity<GetSalaryResponse> getSalary(GetSalaryRequest request) {
        ResponseEntity<GetSalaryResponse> response;
        if(!StringUtils.hasText(request.getCharacterId())){
            response = new ResponseEntity<>(new GetSalaryResponse(),HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer chId = Integer.parseInt(request.getCharacterId());
        GetSalaryResponse res = guildRepositoryService.getSalary(chId);
        response = new ResponseEntity<>(res, HttpStatus.OK);
        return response;
    }

    /**
     * Questo metodo deve verificare che chi sta richiedendo questa operazione, abbia i diritti di farlo come ADMIN
     * @param idExecutor il characterID di chi sta facendo l'operazione
     * @return true se può compierla , false altrimenti
     */
    private boolean checkAdminRight(int idExecutor){
        CharacterDto user = characterRepositoryService.retrieveCharacterFromId(idExecutor);
        List<String> privilegi = Arrays.asList(user.getRole().split("\\s*,\\s*"));
        return privilegi.contains(Roles.ROLE_ADMIN.name());
    }

    /**
     * Verifica che un pg sia o meno nella condizione di schiavo
     * @param chId l'id del pg in questione
     * @return true se è schiavo , false altrimenti
     */
    private boolean isSlaveCharacter(int chId){
        CharacterDto dto = characterRepositoryService.retrieveCharacterFromId(chId);
        return dto.getStatus().equals(Status.SCHIAVO.name());
    }


}
