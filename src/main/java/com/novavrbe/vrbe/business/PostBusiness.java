package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.forumcontroller.*;
import com.novavrbe.vrbe.repositories.impl.*;
import com.novavrbe.vrbe.utils.ForumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class PostBusiness {

    @Autowired
    private PostRepositoryService postRepositoryService;
    @Autowired
    private CharacterRepositoryService characterRepositoryService;
    @Autowired
    private ForumRepositoryService forumRepositoryService;
    @Autowired
    private UserRepositoryService userService;
    @Autowired
    private SubforumRepositoryService subforumRepositoryService;
    @Autowired
    private ForumRepositoryService forumService;

    /**
     * controlla che l'utente abbia il ruolo di ADMIN per poter vedere alcune bacheche
     * @param chId
     * @return
     */
    private Boolean isAdmin(Integer chId) {
       GenericUserDto dto =  userService.findUsersById(new BigDecimal(chId));
        return dto.getRole().equals("ROLE_ADMIN");
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
        updateLastLettura(dto);
        response = new ResponseEntity<>(createPostResponse, HttpStatus.OK);
        return response;
    }

    /**
     * Torna La lista dei post afferenti a quella sezione
     * @param subforumId l'id del subforum di cui stiamo chiedendo i post
     * @return un array list con i post e i loro dati , oppure postList = []
     */
    public ResponseEntity<GetPostListResponse> getPostList(String subforumId, String chId) {
        ResponseEntity<GetPostListResponse> response;
        GetPostListResponse postResponse = new GetPostListResponse();
        ArrayList<Post> postList = new ArrayList<>();
        if(!StringUtils.hasText(subforumId)){
            response = new ResponseEntity<>(postResponse,HttpStatus.BAD_REQUEST);
            return response;
        }
        Iterable<PostDTO> dtos = postRepositoryService.getPostList(Integer.parseInt(subforumId));
        for (PostDTO dto : dtos ) {
            CharacterDto charDto = characterRepositoryService.retrieveCharacterFromId(dto.getAuthor());
            if(charDto == null) continue;
            Post detail = ForumUtils.preparePostDetails(dto,charDto , forumRepositoryService, Integer.parseInt(chId));
            postList.add(detail);
        }

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
     * Torna il dettaglio di un post per poterlo visualizzare scritto
     * @param postId id del post da visualizzare
     * @return oggetto POST con tutte le infot
     */
    public ResponseEntity<GetPostDetailResponse> getPostDetail(String postId, String chId) {
        ResponseEntity<GetPostDetailResponse> response;
        GetPostDetailResponse postResponse = new GetPostDetailResponse();
        if(!StringUtils.hasText(postId)){
            response = new ResponseEntity<>(postResponse,HttpStatus.BAD_REQUEST);
            return response;
        }
        PostDTO postDTO = postRepositoryService.getPost(Integer.parseInt(postId));
        CharacterDto charDto = characterRepositoryService.retrieveCharacterFromId(postDTO.getAuthor());
        Post detail = ForumUtils.preparePostDetails(postDTO,charDto);
        forumRepositoryService.updateLastLetturaPost(postDTO, chId);
        postResponse.setDetail(detail);
        response = new ResponseEntity<>(postResponse,HttpStatus.OK);
        return response;
    }

    /**
     * Cancella un post dal databse, solo se ne sei il propietario o sei un admin
     * @param request ci sono il chId e il postId per i controlli del caso
     * @return true se il post è stato cancellato, false altrimenti
     */
    public ResponseEntity<DeletePostResponse> deletePost(DeletePostRequest request) {
        ResponseEntity<DeletePostResponse> response;
        DeletePostResponse deleteResponse = new DeletePostResponse();
        if(request.getPostId() == null || request.getChId() == null){
            response = new ResponseEntity<>(deleteResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        //Puoi eliminare un post solo se sei il proprietario o un admin
        PostDTO dto = postRepositoryService.getPost(request.getPostId());
        if(dto != null && (Objects.equals(dto.getAuthor(), request.getChId()) || isAdmin(request.getChId()))){
          deleteResponse.setDeleted(postRepositoryService.deletePost(request.getPostId()));
          String message = deleteResponse.isDeleted() ? "Post Eliminato" : "Post non eliminato";
          deleteResponse.setMessage(message);
        }else{
            deleteResponse.setDeleted(false);
            deleteResponse.setMessage("Non sei l'autore del post. Vietato cancellare");
        }
        response = new ResponseEntity<>(deleteResponse,HttpStatus.OK);

        return  response;
    }

    /**
     * Chiude un post per disabilitare le risposte
     * @param request il postID
     * @return
     */
    public ResponseEntity<CreatePostResponse> closePost(ClosePostReques request) {
        ResponseEntity<CreatePostResponse> response;
        CreatePostResponse postResponse = new CreatePostResponse();
        if(request.getPostId() == null){
            response = new ResponseEntity<>(postResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        PostDTO dto = postRepositoryService.getPost(request.getPostId());
        dto.setClosed(true);
        postRepositoryService.closePost(dto);
        postResponse.setPostId(request.getPostId());
        postResponse.setMessage("il post "+dto.getTitle()+"è stato chiuso");
        response = new ResponseEntity<>(postResponse, HttpStatus.OK);
        return response;
    }

    /**
     * Aggiorna il timestamp di scrittura di Subforum e Forum
     * @param newDto
     */
    private void updateLastLettura (PostDTO newDto){

        SubForumDTO subForum = subforumRepositoryService.findSubforum(newDto.getSubforumId());
        subForum.setLastModified(newDto.getLastModified());
        subforumRepositoryService.editSubForum(subForum);

        ForumDTO forum = forumService.getForumById(newDto.getForumId());
        forum.setLastModified(newDto.getLastModified());
        forumService.updateForum(forum);
    }
}
