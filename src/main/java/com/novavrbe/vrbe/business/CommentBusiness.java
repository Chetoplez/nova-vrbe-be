package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.CommentDTO;
import com.novavrbe.vrbe.dto.PostDTO;
import com.novavrbe.vrbe.models.forumcontroller.Comment;
import com.novavrbe.vrbe.models.forumcontroller.CreateCommentRequest;
import com.novavrbe.vrbe.models.forumcontroller.CreateCommentResponse;
import com.novavrbe.vrbe.models.forumcontroller.GetPostCommentResponse;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.repositories.impl.CommentRepositoryService;
import com.novavrbe.vrbe.repositories.impl.PostRepositoryService;
import com.novavrbe.vrbe.utils.ForumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
public class CommentBusiness {

    @Autowired
    CommentRepositoryService commentService;
    @Autowired
    PostRepositoryService postRepositoryService;
    @Autowired
    CharacterRepositoryService characterRepositoryService;

    /**
     * Commeta un post
     * @param request
     * @return
     */
    public ResponseEntity<CreateCommentResponse> commentPost(CreateCommentRequest request) {
        ResponseEntity<CreateCommentResponse> response;
        CreateCommentResponse commentResponse = new CreateCommentResponse();
        if(request.getPostId() == null || request.getChId() == null || request.getComment() == null){
            //spiacente , bad reqeust
            commentResponse.setMessage("Il formato della richiesta è errato");
            commentResponse.setCommentId(-1);
            response = new ResponseEntity<>(commentResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        //verifichiamo se il post è chiuso
        if(isPostClosed(request.getPostId())){
            commentResponse.setMessage("Vietato commentare un post Chiuso");
            commentResponse.setCommentId(-1);
            response = new ResponseEntity<>(commentResponse, HttpStatus.UNAUTHORIZED);
            return response;
        }
        //a quanto pare va tutto bene. Inseriamo il commento
        CommentDTO newDto =  ForumUtils.createCommentDto(request.getComment());
        Integer idCommento = commentService.createComment(newDto);
        commentResponse.setCommentId(idCommento);
        commentResponse.setMessage("Commento creato");
        response = new ResponseEntity<>(commentResponse,HttpStatus.OK);
        return response;
    }

    private boolean isPostClosed(Integer postId) {
        PostDTO dto = postRepositoryService.getPost(postId);
        return dto.isClosed();
    }

    public ResponseEntity<GetPostCommentResponse> getPostComments(String postId) {
        ResponseEntity<GetPostCommentResponse> response;
        GetPostCommentResponse postCommentResponse = new GetPostCommentResponse();
        ArrayList<Comment> commentList = new ArrayList<>();
        if(!StringUtils.hasText(postId)){
            response = new ResponseEntity<>(postCommentResponse,HttpStatus.BAD_REQUEST);
            return response;
        }
        Iterable<CommentDTO> commentDTOs = commentService.getPostComments(Integer.parseInt(postId));
        for (CommentDTO commento: commentDTOs ) {
            CharacterDto charDto = characterRepositoryService.retrieveCharacterFromId(commento.getAuthor());
            if(commento.getRelatedComment() == null)
                commentList.add(ForumUtils.prepareCommentList(commento,charDto));
        }
        postCommentResponse.setCommentList(commentList);
        response = new ResponseEntity<>(postCommentResponse, HttpStatus.OK);
        return response;
    }

    public ResponseEntity<GetPostCommentResponse> getRelatedComments(String commentId) {
        ResponseEntity<GetPostCommentResponse> response;
        GetPostCommentResponse commentResponse = new GetPostCommentResponse();
        ArrayList<Comment> commentList = new ArrayList<>();
        if(!StringUtils.hasText(commentId)){
            response = new ResponseEntity<>(commentResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        Iterable<CommentDTO> dtos = commentService.getRelatedComments(Integer.parseInt(commentId));
        for (CommentDTO commento: dtos ) {
            CharacterDto charDto = characterRepositoryService.retrieveCharacterFromId(commento.getAuthor());
            commentList.add(ForumUtils.prepareCommentList(commento,charDto));
        }
        commentResponse.setCommentList(commentList);
        response = new ResponseEntity<>(commentResponse,HttpStatus.OK);
        return response;
    }
}
