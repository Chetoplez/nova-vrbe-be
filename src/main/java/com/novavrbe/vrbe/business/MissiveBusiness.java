package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.MissivaDto;
import com.novavrbe.vrbe.models.missivecontroller.GetMissiveResponse;
import com.novavrbe.vrbe.models.missivecontroller.Missiva;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.repositories.impl.MissiveRepositoryService;
import com.novavrbe.vrbe.utils.MissivaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MissiveBusiness {

    @Autowired
    MissiveRepositoryService missiveRepositoryService;
    @Autowired
    CharacterRepositoryService characterRepositoryService;

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

    //public ResponseEntity<SendMissiveResponse> sendMissiva(SendMissivaRequest request) {
    //}
//
    //public ResponseEntity<DeleteMissiveResponse> deleteMissive(DeleteMissiveRequest request) {
    //}
//
    //public ResponseEntity<CheckInboxResponse> checkInbox(String chId) {
    //}
}
