package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.charactercontroller.SmallCharacter;
import com.novavrbe.vrbe.models.forumcontroller.Comment;
import com.novavrbe.vrbe.models.forumcontroller.Forum;
import com.novavrbe.vrbe.models.forumcontroller.Post;
import com.novavrbe.vrbe.models.forumcontroller.SubForum;

import java.util.ArrayList;
import java.util.Objects;

public class ForumUtils {
    public static ArrayList<ForumDTO> prepareForumList(Iterable<ForumDTO> dtOs) {
        ArrayList<ForumDTO> arrayForum = new ArrayList<>();

        for (ForumDTO el :
                dtOs) {
            arrayForum.add(el);
        }
        return arrayForum;
    }

    public static ForumDTO createForumDto(Forum forum) {
        ForumDTO newForumDto = new ForumDTO();

        newForumDto.setName(forum.getName());
        newForumDto.setDescription(forum.getDescription());
        newForumDto.setAdminOnly(forum.isAdminOnly());

        newForumDto.setOwnedBy(forum.getOwnedBy());
        newForumDto.setForumType(forum.getForumType());
        return newForumDto;
    }

    public static SubForumDTO createSubforumDTO(SubForum subForum) {
        SubForumDTO dto = new SubForumDTO();
        dto.setAdminOnly(subForum.isAdminOnly());
        dto.setName(subForum.getName());
        dto.setForumId(subForum.getForumId());
        dto.setOwnedBy( subForum.getOwnedBy() == null ? -1 : subForum.getOwnedBy());
        dto.setRankVisibility(subForum.getRankVisibility());
        dto.setSubforumType(subForum.getSubforumType());
        return dto;
    }

    public static ArrayList<SubForumDTO> prepareSubforumList(Iterable<SubForumDTO> dtos, V_GuildMembers guildMember, boolean admin) {
        ArrayList<SubForumDTO> lista = new ArrayList<>();
        Integer guildId = guildMember == null ? -1 : guildMember.getGuildId(); //mi serve per filtrare i subforum
        Integer rankPg = guildMember == null ? 10 : guildMember.getGuildLevel(); // mi serve per filtrare in base al livello del pg
        for (SubForumDTO dto: dtos) {
            if( (dto.getOwnedBy() == -1  && rankPg >= dto.getRankVisibility())
                    || (Objects.equals(dto.getOwnedBy(), guildId) && rankPg >= dto.getRankVisibility() )
                    || admin)
                lista.add(dto);
        }
        return lista;
    }

    public static PostDTO createPostDto(Post post) {
        PostDTO dto = new PostDTO();
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setSubforumId(post.getSubforumId());
        dto.setForumId(post.getForumId());
        dto.setAuthor(post.getAuthor().getCharacterId());
        dto.setPinned(post.isPinned());
        dto.setClosed(post.isClosed());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setLastModified(post.getCreatedAt());
        return dto;
    }

    public static ArrayList<PostDTO> preparePostList(Iterable<PostDTO> dtos) {
        ArrayList<PostDTO> postList = new ArrayList<>();
        for (PostDTO dto: dtos) {
            postList.add(dto);
        }
        return postList;
    }

    public static CommentDTO createCommentDto(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setAuthor(comment.getAuthor().getCharacterId());
        dto.setBody(comment.getBody());
        dto.setPostId(comment.getPostId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setRelatedComment(comment.getRelatedComment() == null ? null : comment.getRelatedComment());
        return dto;
    }

    public static Post preparePostDetails(PostDTO postDTO, CharacterDto charDto) {
        Post dettaglio = new Post();
        SmallCharacter autore = new SmallCharacter();
        autore.setCharacterImg(charDto.getCharacterImg());
        autore.setCharacterName(charDto.getCharacterName());
        autore.setCharacterId(charDto.getCharacterId());
        dettaglio.setAuthor(autore);

        dettaglio.setForumId(postDTO.getForumId());
        dettaglio.setBody(postDTO.getBody());
        dettaglio.setClosed(postDTO.isClosed());
        dettaglio.setPinned(postDTO.isPinned());
        dettaglio.setTitle(postDTO.getTitle());
        dettaglio.setCreatedAt(postDTO.getCreatedAt());
        dettaglio.setLastModified(postDTO.getLastModified());
        dettaglio.setSubforumId(postDTO.getSubforumId());
        dettaglio.setPostId(postDTO.getPostId());
        return dettaglio;
    }

    public static Comment prepareCommentList(CommentDTO commento, CharacterDto charDto) {
        Comment comment = new Comment();
        SmallCharacter smallCharacter = new SmallCharacter();
        smallCharacter.setCharacterId(charDto.getCharacterId());
        smallCharacter.setCharacterName(charDto.getCharacterName());
        smallCharacter.setCharacterImg(charDto.getCharacterImg());

        comment.setCommentId(commento.getCommentID());
        comment.setAuthor(smallCharacter);
        comment.setBody(commento.getBody());
        comment.setPostId(commento.getPostId());
        comment.setRelatedComment(commento.getRelatedComment() == null ? -1 : commento.getRelatedComment());
        comment.setCreatedAt(commento.getCreatedAt());
        return comment;
    }

    public static SubForum prepareSubforumFromDto(SubForumDTO dto) {
        SubForum forum = new SubForum();
        forum.setName(dto.getName());
        forum.setAdminOnly(dto.isAdminOnly());
        forum.setForumId(dto.getForumId());
        forum.setSubforumType(dto.getSubforumType());
        return forum;
    }
}
