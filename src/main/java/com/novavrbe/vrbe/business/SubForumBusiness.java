package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.ForumDTO;
import com.novavrbe.vrbe.dto.GenericUserDto;
import com.novavrbe.vrbe.dto.SubForumDTO;
import com.novavrbe.vrbe.dto.V_GuildMembers;
import com.novavrbe.vrbe.models.enumerations.Roles;
import com.novavrbe.vrbe.models.forumcontroller.*;
import com.novavrbe.vrbe.repositories.impl.ForumRepositoryService;
import com.novavrbe.vrbe.repositories.impl.GuildRepositoryService;
import com.novavrbe.vrbe.repositories.impl.SubforumRepositoryService;
import com.novavrbe.vrbe.repositories.impl.UserRepositoryService;
import com.novavrbe.vrbe.utils.ForumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class SubForumBusiness {
    @Autowired
    private SubforumRepositoryService subforumRepositoryService;
    @Autowired
    private ForumRepositoryService forumRepositoryService;
    @Autowired
    private UserRepositoryService userService;
    @Autowired
    private GuildRepositoryService guildService;


    /**
     * Crea una sotto sezione del forum. Se si tratta di un forum che è ADMIN ONLY, si dovrà essere admin per poter creare la sottosezione.
     * Altrimenti la creazione è sempre consentita.
     * @param request
     * @return
     */
    public ResponseEntity<CreateSubForumResponse> createSubForum(CreateSubForumRequest request) {
        ResponseEntity<CreateSubForumResponse> response;
        CreateSubForumResponse subForumResponse = new CreateSubForumResponse();
        ForumDTO forum = forumRepositoryService.getForumById(request.getSubForum().getForumId());

        if(isProtectedForum(request.getSubForum().getForumId())){
            if(!StringUtils.hasText(request.getSubForum().getName()) || request.getSubForum().getName().length()>150){
                //non andiamo bene
                subForumResponse.setMessage("Il nome è obbligatorio ma non deve superare i 150 caratteri");
                subForumResponse.setSubforumId(-1);
                response = new ResponseEntity<>(subForumResponse, HttpStatus.BAD_REQUEST);
                return response;
            }
            //Qui devo essere un ADMIN per inserire il nuovo post
            if(!isAdmin(request.getChId())){
                subForumResponse.setMessage("Non sei autorizzato a creare una sezione");
                subForumResponse.setSubforumId(-1);
                response = new ResponseEntity<>(subForumResponse, HttpStatus.UNAUTHORIZED);
                return response;
            }
            //creo un subforum di un forum ADMIN
            SubForumDTO newSub = ForumUtils.createSubforumDTO(request.getSubForum(), forum.getOwnedBy());
            Integer subId =  subforumRepositoryService.createSubForum(newSub);
            subForumResponse.setSubforumId(subId);
            subForumResponse.setMessage("Nuova sezione Creata con Successo");
            response = new ResponseEntity<>(subForumResponse,HttpStatus.OK);
            return response;
        }
        if(StringUtils.hasText(request.getSubForum().getName()) || request.getSubForum().getName().length()<150){
        SubForumDTO newSub = ForumUtils.createSubforumDTO(request.getSubForum(),forum.getOwnedBy());
        Integer subId =  subforumRepositoryService.createSubForum(newSub);
        subForumResponse.setSubforumId(subId);
        subForumResponse.setMessage("Nuova sezione Creata con Successo");
        response = new ResponseEntity<>(subForumResponse,HttpStatus.OK);
        }else{
            subForumResponse.setMessage("Il nome è obbligatorio ma non deve superare i 150 caratteri");
            subForumResponse.setSubforumId(-1);
            response = new ResponseEntity<>(subForumResponse, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Verifica se un characherId è un admin o meno
     * @param chId il characterId del pg
     * @return vero se il chId è admin , false altrimenti
     */
    private Boolean isAdmin(Integer chId) {
        GenericUserDto userDto =  userService.findUsersById(new BigDecimal(chId));
        return userDto.getRole().equals(Roles.ROLE_ADMIN.name());
    }

    /**
     * Controlla che un subForum appartenga ad un forum di admin ed in caso verifica che tu lo sia
     * @param forumId
     * @return true se è un forum per soli admin
     */
    private boolean isProtectedForum(Integer forumId) {
        ForumDTO dto = forumRepositoryService.getForumById(forumId);
        return dto.isAdminOnly();
    }


    /**
     * Ritorna tutte le sezioni di un forum, controllando di specifico le tue grant come Character
     * @param request
     * @return un Arralist di SubforumDTO
     */
    public ResponseEntity<GetSubForumResponse> getSubforum(GetSubforumRequest request) {
        ResponseEntity<GetSubForumResponse> response;
        GetSubForumResponse subForumResponse = new GetSubForumResponse();
        boolean admin = isAdmin(Integer.parseInt(request.getChId()));
        V_GuildMembers guildMember = guildService.getGuildMember(Integer.parseInt(request.getChId()));
        if(!StringUtils.hasText(request.getForumId())){
            response = new ResponseEntity<>(subForumResponse, HttpStatus.BAD_REQUEST);
            return response;
        }

        ArrayList<SubForum> subForumList = new ArrayList<>();
        Iterable<SubForumDTO> dtos = null;
        Integer fId = Integer.parseInt(request.getForumId());
        if(isProtectedForum(fId)){
            if(!admin){
                response = new ResponseEntity<>(subForumResponse, HttpStatus.UNAUTHORIZED);
                return response;
            }
        }
        ForumDTO forum = forumRepositoryService.getForumById(fId);
        dtos = subforumRepositoryService.getSubforum(fId, admin);
        subForumList = ForumUtils.prepareSubforumList(dtos, guildMember , admin , forumRepositoryService , Integer.parseInt(request.getChId()));
        subForumResponse.setSubForums(subForumList);
        subForumResponse.setForumName(forum.getName());
        response = new ResponseEntity<>(subForumResponse,HttpStatus.OK);
        return response;
    }


    /**
     * Modifica il nome e le proprietà di una sezione, per ADMIN soltanto
     * @param request
     * @return
     */
    public ResponseEntity<CreateSubForumResponse> editSubForum(EditSubForumRequest request) {
        ResponseEntity<CreateSubForumResponse> response;
        CreateSubForumResponse subForumResponse = new CreateSubForumResponse();

        if(!isAdmin(request.getChId())){
            subForumResponse.setMessage("non sei autorizzato a fare Edit");
            subForumResponse.setSubforumId(-1);
            response = new ResponseEntity<>(subForumResponse,HttpStatus.FORBIDDEN);
            return response;
        }
        if(!StringUtils.hasText(request.getSubForum().getName()) || request.getSubForum().getName().length()>150){
            subForumResponse.setMessage("Il nome deve essere presente e la sua lunghezza minore di 150 caratteri");
            subForumResponse.setSubforumId(-1);
            response = new ResponseEntity<>(subForumResponse,HttpStatus.BAD_REQUEST);
            return response;
        }
        SubForumDTO dto = ForumUtils.createSubforumDTO(request.getSubForum(), request.getSubForum().getOwnedBy());
        dto.setSubforumId(request.getSubforumId());
        subForumResponse.setSubforumId(subforumRepositoryService.editSubForum(dto));
        subForumResponse.setMessage("Sezione Modificata");
        response = new ResponseEntity<>(subForumResponse,HttpStatus.OK);
        return response;
    }

    /**
     * Elimina un subforum
     * @param request
     * @return
     */
    public ResponseEntity<DeleteSubforumResponse> deleteSubforum(DeleteSubforumRequest request) {
        ResponseEntity<DeleteSubforumResponse> response;
        DeleteSubforumResponse subforumResponse = new DeleteSubforumResponse();
        if(!isAdmin(request.getChId())){
            subforumResponse.setMessage("non sei autorizzato a cancellare la sezione");
            subforumResponse.setDeleted(false);
            response = new ResponseEntity<>(subforumResponse,HttpStatus.UNAUTHORIZED);
            return response;
        }
        subforumRepositoryService.deleteSubforum(request.getSubForumId());
        subforumResponse.setMessage("Sezione Eliminata con successo");
        subforumResponse.setDeleted(true);
        response = new ResponseEntity<>(subforumResponse, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<GetSubforumDetailResponse> getSubforumDetail(String subforumId) {
        ResponseEntity<GetSubforumDetailResponse> response;
        GetSubforumDetailResponse detailResponse = new GetSubforumDetailResponse();
        if(!StringUtils.hasText(subforumId)){
            response = new ResponseEntity<>(detailResponse,HttpStatus.BAD_REQUEST);
            return response;
        }
        SubForumDTO dto = subforumRepositoryService.findSubforum(Integer.parseInt(subforumId));
        SubForum sub  = ForumUtils.prepareSubforumFromDto(dto);
        detailResponse.setSubForum(sub);
        response = new ResponseEntity<>(detailResponse,HttpStatus.OK);
        return response;
    }
}
