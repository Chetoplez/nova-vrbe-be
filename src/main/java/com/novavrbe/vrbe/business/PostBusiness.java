package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.ForumDTO;
import com.novavrbe.vrbe.dto.PostDTO;
import com.novavrbe.vrbe.models.forumcontroller.*;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.repositories.impl.ForumRepositoryService;
import com.novavrbe.vrbe.repositories.impl.PostRepositoryService;
import com.novavrbe.vrbe.utils.ForumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
public class PostBusiness {

    @Autowired
    private PostRepositoryService postRepositoryService;
    @Autowired
    private CharacterRepositoryService characterRepositoryService;
    @Autowired
    private ForumRepositoryService forumRepositoryService;

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
     * Controlla che un subForum appartenga ad un forum di admin ed in caso verifica che tu lo sia
     * @param forumId
     * @return true se è un forum per soli admin
     */
    private boolean isProtectedForum(Integer forumId) {
        ForumDTO dto = forumRepositoryService.getForumById(forumId);
        return dto.isAdminOnly();
    }

    /**
     * Crea un nuovo post in una sezione.
     * @param request
     * @return
     */
    public ResponseEntity<CreatePostResponse> createNewPost(CreatePostRequest request) {
        ResponseEntity<CreatePostResponse> response;
        CreatePostResponse createPostResponse = new CreatePostResponse();
        if(!StringUtils.hasText(request.getPost().getTitle()) || !StringUtils.hasText(request.getPost().getBody())){
            createPostResponse.setPostId(-1);
            createPostResponse.setMessage("Il titolo o il corpo del post devono essere riempiti");
            response = new ResponseEntity<>(createPostResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        if(isProtectedForum(request.getPost().getForumId())){
            if(!isAdmin(request.getChId())){
                createPostResponse.setPostId(-1);
                createPostResponse.setMessage("Non sei autorizzato");
                response = new ResponseEntity<>(createPostResponse, HttpStatus.UNAUTHORIZED);
                return response;
            }
            PostDTO dto = ForumUtils.createPostDto(request.getPost());
            Integer postId =  postRepositoryService.createNewPost(dto);
            createPostResponse.setPostId(postId);
            createPostResponse.setMessage("Nuovo post creato");
            response = new ResponseEntity<>(createPostResponse, HttpStatus.OK);
            return response;
        }
        PostDTO dto = ForumUtils.createPostDto(request.getPost());
        Integer postId =  postRepositoryService.createNewPost(dto);
        createPostResponse.setPostId(postId);
        createPostResponse.setMessage("Nuovo post creato");
        response = new ResponseEntity<>(createPostResponse, HttpStatus.OK);
        return response;
    }

    /**
     * Torna La lista dei post afferenti a quella sezione
     * @param subforumId l'id del subforum di cui stiamo chiedendo i post
     * @return un array list con i post e i loro dati , oppure postList = []
     */
    public ResponseEntity<GetPostListResponse> getPostList(String subforumId) {
        ResponseEntity<GetPostListResponse> response;
        GetPostListResponse postResponse = new GetPostListResponse();
        ArrayList<PostDTO> postList = new ArrayList<>();
        if(!StringUtils.hasText(subforumId)){
            response = new ResponseEntity<>(postResponse,HttpStatus.BAD_REQUEST);
            return response;
        }
        Iterable<PostDTO> dtos = postRepositoryService.getPostList(Integer.parseInt(subforumId));
        postList = ForumUtils.preparePostList(dtos);
        postResponse.setPostList(postList);
        response = new ResponseEntity<>(postResponse,HttpStatus.OK);
        return response;
    }

    /**
     * Permette di modificare il post. La modifica totale la può fare solo un admin mentre un characher normale può solo modificare il body
     * @param request la richiesta fatta con CharacherId, postId da modificare e un oggetto Post
     * @return
     */
    public ResponseEntity<CreatePostResponse> editPost(EditPostRequest request) {
        ResponseEntity<CreatePostResponse> response;
        CreatePostResponse postResponse = new CreatePostResponse();

        if(!StringUtils.hasText(request.getEditedPost().getTitle()) || !StringUtils.hasText(request.getEditedPost().getBody()) ){
            response = new ResponseEntity<>(postResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        PostDTO oldPost = postRepositoryService.getPost(request.getPostId());
        if(oldPost == null ){
            response = new ResponseEntity<>(postResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        //vediamo se stiamo modificando un post mio o meno, innanzitutto
        if(request.getChId() == oldPost.getAuthor()){
            //il post è del mio pg, quindi posso modificare il body
            oldPost.setBody(request.getEditedPost().getBody());
            oldPost.setLastModified(request.getEditedPost().getLastModified());
        }
        if(isAdmin(request.getChId())){
            //se sono admin, posso modificare anche il titolo e le altre caratteristiche
            oldPost.setBody(request.getEditedPost().getBody());
            oldPost.setTitle(request.getEditedPost().getTitle());
            oldPost.setClosed(request.getEditedPost().isClosed());
            oldPost.setPinned(request.getEditedPost().isPinned());
            oldPost.setLastModified(request.getEditedPost().getLastModified());
        }

        Integer postId =  postRepositoryService.editPost(oldPost);
        postResponse.setPostId(postId);
        postResponse.setMessage("Post Modificato");
        response = new ResponseEntity<>(postResponse, HttpStatus.OK);
        return response;
    }

    /**
     * Torna il dettaglio di un post
     * @param postId
     * @return
     */
    public ResponseEntity<GetPostDetailResponse> getPostDetail(String postId) {
        ResponseEntity<GetPostDetailResponse> response;
        GetPostDetailResponse postResponse = new GetPostDetailResponse();
        if(!StringUtils.hasText(postId)){
            response = new ResponseEntity<>(postResponse,HttpStatus.BAD_REQUEST);
            return response;
        }
        PostDTO postDTO = postRepositoryService.getPost(Integer.parseInt(postId));
        CharacterDto charDto = characterRepositoryService.retrieveCharacterFromId(postDTO.getAuthor());
        Post detail = ForumUtils.preparePostDetails(postDTO,charDto);
        postResponse.setDetail(detail);
        response = new ResponseEntity<>(postResponse,HttpStatus.OK);
        return response;
    }
}
