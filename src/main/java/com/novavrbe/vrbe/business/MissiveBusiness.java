package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.MissivaDto;
import com.novavrbe.vrbe.models.missivecontroller.*;
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
     * @param inbox
     * @return una lista di Missiva object >
     */
    public ResponseEntity<GetMissiveResponse> getMissive(Integer chId, Boolean inbox) {
        ResponseEntity<GetMissiveResponse> response;
        GetMissiveResponse missiveResponse = new GetMissiveResponse();
        ArrayList<Missiva> inboxList = new ArrayList<>();

        if(chId == null){
            response = new ResponseEntity<>(missiveResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        //let's go to the database!
        List<MissivaDto> missivaDtos ;
        if(inbox){
            missivaDtos = missiveRepositoryService.getInbox(chId);
        } else{
            missivaDtos = missiveRepositoryService.getSentBox(chId);
        }
        //now, for each object we create a Missiva Model
        for (MissivaDto dto :  missivaDtos ) {
            CharacterDto fromDto = characterRepositoryService.retrieveCharacterFromId(dto.getChFrom());
            CharacterDto toDto = characterRepositoryService.retrieveCharacterFromId(Integer.parseInt(dto.getChTo()));

            Missiva missiva =  MissivaUtils.prepareMissivafromDto(dto, fromDto, toDto, inbox);
            inboxList.add(missiva);
        }
        missiveResponse.setMissiveList(inboxList);
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

    /**
     * Controlla se nella inbox ci sono dei messaggi con isRead = 0
     * @param chId
     * @return
     */
    public ResponseEntity<CheckInboxResponse> checkInbox(Integer chId) {
        ResponseEntity<CheckInboxResponse> response;
        CheckInboxResponse inboxResponse = new CheckInboxResponse();
        if(chId == null){
            response = new ResponseEntity<>(inboxResponse,HttpStatus.BAD_REQUEST);
            return response;
        }
        ArrayList<MissivaDto> newMailDto =  missiveRepositoryService.checkNewMail(chId);
        inboxResponse.setNewMail(!newMailDto.isEmpty());
        inboxResponse.setNNewMail(newMailDto.size());

        response = new ResponseEntity<>(inboxResponse,HttpStatus.OK);
        return response;
    }

    /**
     * Modifica la lettura delle missive
     * @param request
     * @return
     */
    public ResponseEntity<ReadMissivaResponse> readMissive(ReadMissivaRequest request) {
        ResponseEntity<ReadMissivaResponse> response;
        ReadMissivaResponse missivaResponse = new ReadMissivaResponse();
        if(request.getIdMissive() == null || request.getIdMissive().size() == 0){
            response = new ResponseEntity<>(missivaResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        ArrayList<MissivaDto> toReadDto = missiveRepositoryService.getMissiveList(request.getIdMissive() , request.getChId());
        for (MissivaDto dto: toReadDto ) {
            if(dto.getChTo().equalsIgnoreCase(request.getChId()))
                dto.setRead(true);
        }
        missiveRepositoryService.saveAll(toReadDto);
        missivaResponse.setMessaggio("Missive lette");
        missivaResponse.setSucces(true);
        response = new ResponseEntity<>(missivaResponse,HttpStatus.OK);
        return response;
    }


    public ResponseEntity<DeleteMissiveResponse> deleteMissive(DeleteMissiveRequest request) {
        ResponseEntity<DeleteMissiveResponse> response;
        DeleteMissiveResponse missivaResponse = new DeleteMissiveResponse();
        if(request.getIdMissive() == null || request.getIdMissive().size() == 0){
            response = new ResponseEntity<>(missivaResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        ArrayList<MissivaDto> toDeleteDto = missiveRepositoryService.getMissiveList(request.getIdMissive(), request.getChId());
        if(request.isInbox()){
            //sto cancellando missive in cui sono il destinatario
            for (MissivaDto dto: toDeleteDto ) {
                dto.setDeletedTo(true);
            }
        }else {
            for (MissivaDto dto: toDeleteDto ) {
                dto.setDeletedFrom(true);
            }
        }

        missiveRepositoryService.saveAll(toDeleteDto);
        missivaResponse.setMessaggio("Missive eliminate");
        missivaResponse.setSucces(true);
        response = new ResponseEntity<>(missivaResponse,HttpStatus.OK);
        return response;
    }

}
