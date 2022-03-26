package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.CommentBusiness;
import com.novavrbe.vrbe.business.ForumBusiness;
import com.novavrbe.vrbe.business.PostBusiness;
import com.novavrbe.vrbe.business.SubForumBusiness;
import com.novavrbe.vrbe.models.forumcontroller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    ForumBusiness forumBusiness;
    @Autowired
    SubForumBusiness subForumBusiness;
    @Autowired
    PostBusiness postBusiness;
    @Autowired
    CommentBusiness commentBusiness;

    @GetMapping("/getallforums/chId={chId}")
    public ResponseEntity<GetForumResponse> getAllForums(@PathVariable Integer chId){
        return forumBusiness.getAllForums(chId);
    }

    @PutMapping("/create")
    public ResponseEntity<CreateForumResponse> createNewForum(@RequestBody CreateForumRequest request){
        return forumBusiness.createNewForum(request);
    }

    @PatchMapping("/edit")
    public ResponseEntity<CreateForumResponse> updateForum(@RequestBody UpdateForumRequest request){
        return forumBusiness.updateForum(request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteForumResponse> deleteForum(@RequestBody UpdateForumRequest request){
        return forumBusiness.deleteForum(request);
    }

    @PutMapping("/subforum/create")
    public ResponseEntity<CreateSubForumResponse> createNewSubforum(@RequestBody CreateSubForumRequest request){
        return subForumBusiness.createSubForum(request);
    }

    @PatchMapping("/subforum/edit")
    public ResponseEntity<CreateSubForumResponse> editSubforum(@RequestBody EditSubForumRequest request){
        return subForumBusiness.editSubForum(request);
    }

    @PostMapping("/subforum/getall")
    public ResponseEntity<GetSubForumResponse> getSubforum(@RequestBody GetSubforumRequest request){
        return subForumBusiness.getSubforum(request);
    }

    @GetMapping("/subforum/getDetail/{subforumId}")
    public ResponseEntity<GetSubforumDetailResponse> getSubforumDetail(@PathVariable String subforumId){
        return subForumBusiness.getSubforumDetail(subforumId);
    }

    @DeleteMapping("/subforum/delete")
    public ResponseEntity<DeleteSubforumResponse> deleteSubforum(@RequestBody DeleteSubforumRequest request){
        return subForumBusiness.deleteSubforum(request);
    }


    @PutMapping("/post/create")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request){
        return postBusiness.createNewPost(request);
    }

    @GetMapping("/post/getall/{subforumId}")
    public ResponseEntity<GetPostListResponse> getPostList(@PathVariable String subforumId){
        return postBusiness.getPostList(subforumId);
    }

    @GetMapping("/post/postdetail/{postId}")
    public ResponseEntity<GetPostDetailResponse> getPostDetail (@PathVariable String postId ){
        return postBusiness.getPostDetail(postId);
    }

    @PatchMapping("/post/edit")
    public ResponseEntity<CreatePostResponse> editPost(@RequestBody EditPostRequest request){
        return postBusiness.editPost(request);
    }

    @DeleteMapping("/post/delete")
    public ResponseEntity<DeletePostResponse> deletePost (@RequestBody DeletePostRequest request){
        return postBusiness.deletePost(request);
    }


    @PutMapping("/comment/create")
    public ResponseEntity<CreateCommentResponse> commentPost(@RequestBody CreateCommentRequest request){
        return commentBusiness.commentPost(request);
    }

    @GetMapping("/comment/getpostcomment/{postId}")
    public ResponseEntity<GetPostCommentResponse> getPostComments(@PathVariable String postId){
        return commentBusiness.getPostComments(postId);
    }

    @GetMapping("/comment/getrelated/{commentId}")
    public ResponseEntity<GetPostCommentResponse> getRelatedComments(@PathVariable String commentId){
        return commentBusiness.getRelatedComments(commentId);
    }

}
