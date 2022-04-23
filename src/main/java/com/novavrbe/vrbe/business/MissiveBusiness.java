package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.MissivaDto;
import com.novavrbe.vrbe.models.missivecontroller.GetMissiveResponse;
import com.novavrbe.vrbe.models.missivecontroller.Missiva;
import com.novavrbe.vrbe.models.missivecontroller.SendMissivaRequest;
import com.novavrbe.vrbe.models.missivecontroller.SendMissiveResponse;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.repositories.impl.MissiveRepositoryService;
import com.novavrbe.vrbe.utils.MissivaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MissiveBusiness {

    @Autowired
    MissiveRepositoryService missiveRepositoryService;
    @Autowired
    CharacterRepositoryService characterRepositoryService;

    /**
     * Torna la inbox di un personaggio
     * @param chId ul character Id del pg
     * @return una lista di Missiva object >
     */
    public ResponseEntity<GetMissiveResponse> getMissive(Integer chId) {
        ResponseEntity<GetMissiveResponse> response;
        GetMissiveResponse missiveResponse = new GetMissiveResponse();
        ArrayList<Missiva> inbox = new ArrayList<>();

        if(chId == null){
            response = new ResponseEntity<>(missiveResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        //let's go to the database!
        List<MissivaDto> missivaDtos = missiveRepositoryService.getInbox(chId);
        //now, for each object we create a Missiva Model
        for (MissivaDto dto :  missivaDtos ) {
            CharacterDto fromDto = characterRepositoryService.retrieveCharacterFromId(dto.getChFrom());
            CharacterDto toDto = characterRepositoryService.retrieveCharacterFromId(chId);

            Missiva missiva =  MissivaUtils.prepareMissivafromDto(dto, fromDto, toDto);
            inbox.add(missiva);
        }
        missiveResponse.setMissiveList(inbox);
        response = new ResponseEntity<>(missiveResponse,HttpStatus.OK);
        return response;
    }

    /**
     * Spedisce una lettera a Babbo Natale
     * @param request
     * @return
     */
    public ResponseEntity<SendMissiveResponse> sendMissiva(SendMissivaRequest request) {
        ResponseEntity<SendMissiveResponse> response;
        SendMissiveResponse missiveResponse = new SendMissiveResponse();
        Missiva missiva = request.getMissiva();
        if(!StringUtils.hasText(missiva.getBody()) || !StringUtils.hasText(missiva.getSubject()) || missiva.getFrom() == null || missiva.getTo() == null ){
            response = new ResponseEntity<>(missiveResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        MissivaDto dto = MissivaUtils.prepareMissivaDto(missiva);
        MissivaDto sent  = missiveRepositoryService.sendMissiva(dto);
        missiveResponse.setSent(sent != null);
        missiveResponse.setMessage("Missiva inviata correttamente");
        response = new ResponseEntity<>(missiveResponse, HttpStatus.OK);
        return response;
    }


    //public ResponseEntity<DeleteMissiveResponse> deleteMissive(DeleteMissiveRequest request) {
    //}
//
    //public ResponseEntity<CheckInboxResponse> checkInbox(String chId) {
    //}
}
