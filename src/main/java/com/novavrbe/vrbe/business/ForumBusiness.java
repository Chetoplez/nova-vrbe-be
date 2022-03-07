package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.ForumDTO;
import com.novavrbe.vrbe.models.forumcontroller.*;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.repositories.impl.ForumRepositoryService;
import com.novavrbe.vrbe.utils.ForumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
public class ForumBusiness {

    @Autowired
    private ForumRepositoryService forumRepositoryService;

    @Autowired
    private CharacterRepositoryService characterRepositoryService;

    /**
     * Ritorna tutti i forum che l'utente è abilitato a vedere.
     * @param request Contiene l'id del richiedente
     * @return tutti i forum che può vedere il richiedente
     */
    public ResponseEntity<GetForumResponse> getAllForums(GetForumRequest request) {
        ResponseEntity<GetForumResponse> response;
        if(request.getChId() == null){
            response = new ResponseEntity<>(new GetForumResponse(), HttpStatus.BAD_REQUEST);
            return  response;
        }
        Iterable<ForumDTO> DTOs = forumRepositoryService.getAllForums(isAdmin(request.getChId()));
        ArrayList<ForumDTO> allForum = ForumUtils.prepareForumList(DTOs);
        GetForumResponse res = new GetForumResponse();
        res.setForumsList(allForum);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    /**
     * Crea una nuova sezione nei forum
     * @param request l'ggetto forum composto da nome, visibilità e appartenenza
     * @return l'id del nuovo forum creato
     */
    public ResponseEntity<CreateForumResponse> createNewForum(CreateForumRequest request) {
        ResponseEntity<CreateForumResponse> response;
        CreateForumResponse forumResponse = new CreateForumResponse();
        if(!isAdmin(request.getChId())){
            forumResponse.setForumId(-1);
            forumResponse.setMessage("Non sei autorizzato");
            //TODO qui dovremmo loggare questo tentativo
            response = new ResponseEntity<>(forumResponse, HttpStatus.UNAUTHORIZED);
            return response;
        }
        if(!StringUtils.hasText(request.getForum().getName())){
            forumResponse.setForumId(-1);
            forumResponse.setMessage("Il nome del forum è obbligatorio, non deve superare i 150 caratteri");
            response = new ResponseEntity<>(forumResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        //Se sono qui, la richiesta è accettabile
        ForumDTO newForumDto = ForumUtils.createForumDto(request.getForum());
        Integer newForumId = forumRepositoryService.createNewForum(newForumDto);
        forumResponse.setForumId(newForumId);
        forumResponse.setMessage("Nuovo Forum Creato correttamente");
        response = new ResponseEntity<>(forumResponse,HttpStatus.OK);
        return response;

    }


    /**
     * controlla che l'utente abbia il ruolo di ADMIN per poter vedere alcune bacheche
     * @param chId
     * @return
     */
    private Boolean isAdmin(Integer chId) {
       CharacterDto characterDto =  characterRepositoryService.retrieveCharacterFromId(chId);
       return characterDto.getRole().equals("ADMIN");
    }

    /**
     * Un metodo per modificare o editare un forum già creato
     * @param request serve sempre il character id con ruolo admin e il forum editato
     * @return true o false
     */
    public ResponseEntity<CreateForumResponse> updateForum(UpdateForumRequest request) {
        ResponseEntity<CreateForumResponse> response;
        CreateForumResponse forumResponse = new CreateForumResponse();
        if(!isAdmin(request.getChId())){
            forumResponse.setForumId(-1);
            forumResponse.setMessage("Non sei autorizzato");
            //TODO qui dovremmo loggare questo tentativo
            response = new ResponseEntity<>(forumResponse, HttpStatus.UNAUTHORIZED);
            return response;
        }
        if(!StringUtils.hasText(request.getNewForum().getName())){
            forumResponse.setForumId(request.getNewForum().getForumId());
            forumResponse.setMessage("Il nome del forum è obbligatorio, non deve superare i 150 caratteri");
            response = new ResponseEntity<>(forumResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        //Se sono qui, la richiesta è accettabile

        Integer newForumId = forumRepositoryService.updateForum(request.getNewForum());
        forumResponse.setForumId(newForumId);
        forumResponse.setMessage("Forum Modificato Correttamente");
        response = new ResponseEntity<>(forumResponse,HttpStatus.OK);
        return response;
    }

    /**
     * Elimina un forum dal database
     * @param request
     * @return
     */
    public ResponseEntity<DeleteForumResponse> deleteForum(UpdateForumRequest request) {
        ResponseEntity<DeleteForumResponse> response;
        DeleteForumResponse forumResponse = new DeleteForumResponse();
        if (!isAdmin(request.getChId())) {
            forumResponse.setDeleted(false);
            forumResponse.setMessage("Non sei autorizzato");
            //TODO qui dovremmo loggare questo tentativo
            response = new ResponseEntity<>(forumResponse, HttpStatus.UNAUTHORIZED);
            return response;
        }
        //Se sono qui, la richiesta è accettabile
        boolean deleted = forumRepositoryService.deleteForum(request.getNewForum().getForumId());
        forumResponse.setDeleted(deleted);
        forumResponse.setMessage(deleted ? "Forum Eliminato": "Qualcosa non ha funzionato");
        response = new ResponseEntity<>(forumResponse,HttpStatus.OK);
        return response;

    }


}
