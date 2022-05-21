package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.ForumDTO;
import com.novavrbe.vrbe.dto.PostLettiDto;
import com.novavrbe.vrbe.repositories.ForumRepository;
import com.novavrbe.vrbe.repositories.PostLettiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForumRepositoryService {

    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private PostLettiRepository postLettiRepository;

    /**
     * Ritorna tutti i forum per l'utente che sta richiedendo
     * @param admin true se chi fa la richiesta è admin, false altrimenti
     * @return lista itarable con ForumDtos
     */
    public Iterable<ForumDTO> getAllForums(Boolean admin) {
        if(admin){
            return forumRepository.findAll();
        }else {
            return forumRepository.findByAdminOnlyFalse();
        }
    }

    /**
     * Crea un nuovo forum nel database
     * @param forum
     * @return
     */
    public Integer createNewForum(ForumDTO forum) {
        ForumDTO newForum = forumRepository.save(forum);
        return newForum.getForumId();
    }

    /**
     * Modifica un forum creato
     * @param newForumDto
     * @return
     */
    public Integer updateForum(ForumDTO newForumDto) {
        Optional<ForumDTO> dto = forumRepository.findById(newForumDto.getForumId());
        if(dto.isPresent()){
            forumRepository.save(newForumDto);
        }
        return newForumDto.getForumId();
    }

    /**
     * Elimina un forum dal database
     * @param forumId
     * @return
     */
    public boolean deleteForum(Integer forumId) {
        Optional<ForumDTO> dto = forumRepository.findById(forumId);
        if(dto.isPresent()){
            forumRepository.delete(dto.get());
            return true;
        }else return false;
    }


    public ForumDTO getForumById(Integer forumId) {
        Optional<ForumDTO> dto = forumRepository.findById(forumId);
        ForumDTO forumDTO = dto.get();
        return forumDTO;
    }

    public PostLettiDto getLastReadedForums(Integer chId, Integer forumId) {
        return  postLettiRepository.findByChIdAndForumIdGroupByForum(chId, forumId);

    }
}
