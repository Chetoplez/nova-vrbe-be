package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.SubForumDTO;
import com.novavrbe.vrbe.repositories.SubForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubforumRepositoryService {

    @Autowired
    SubForumRepository subForumRepository;

    public Integer createSubForum(SubForumDTO newSub) {
        SubForumDTO dto = subForumRepository.save(newSub);
        return dto.getSubforumId();
    }

    public Iterable<SubForumDTO> getSubforum(Integer forumId, boolean admin) {
        if(admin){
            return subForumRepository.findByForumId(forumId);
        }else {
            return subForumRepository.findByForumIdAndAdminOnlyFalse(forumId);
        }

    }

    public Integer editSubForum(SubForumDTO newSub) {
        SubForumDTO dto= subForumRepository.save(newSub);
        return dto.getSubforumId();
    }

    public void deleteSubforum(Integer subForumId) {
        subForumRepository.deleteById(subForumId);
    }

    public SubForumDTO findSubforum(int parseInt) {
        Optional<SubForumDTO> dto = subForumRepository.findById(parseInt);
        return dto.isPresent() ? dto.get() : null;
    }
}
