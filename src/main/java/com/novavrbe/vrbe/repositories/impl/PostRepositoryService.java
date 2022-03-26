package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.PostDTO;
import com.novavrbe.vrbe.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostRepositoryService {

    @Autowired
    private PostRepository postRepository;

    public Integer createNewPost(PostDTO dto) {
        PostDTO dtoNew = postRepository.save(dto);
        return dtoNew.getPostId();
    }

    /**
     * Torna tutti i post di quel subforum
     * @param subforumId l'id del subforum
     * @return
     */
    public Iterable<PostDTO> getPostList(int subforumId) {
        return postRepository.findBySubforumId(subforumId);
    }

    public PostDTO getPost(Integer postId) {
       Optional<PostDTO> dto = postRepository.findById(postId);
       return dto.isPresent() ? dto.get() : null;
    }

    /**
     * Modifica il body e il titolo di un post
     * @param oldPost
     * @return
     */
    public Integer editPost(PostDTO oldPost) {
        PostDTO dto = postRepository.save(oldPost);
        return dto.getPostId();
    }

    /**
     * Cancella un post
     * @param postId
     * @return
     */
    public boolean deletePost(Integer postId) {
        boolean deleted = false;
        Optional<PostDTO> dto =  postRepository.findById(postId);
        if(dto.isPresent()){
            postRepository.delete(dto.get());
            deleted = true ;
        }
        return deleted;
    }
}
