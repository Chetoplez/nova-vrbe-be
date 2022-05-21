package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.charactermodels.SmallCharacter;
import com.novavrbe.vrbe.models.forumcontroller.Comment;
import com.novavrbe.vrbe.models.forumcontroller.Forum;
import com.novavrbe.vrbe.models.forumcontroller.Post;
import com.novavrbe.vrbe.models.forumcontroller.SubForum;
import com.novavrbe.vrbe.repositories.impl.ForumRepositoryService;

import java.util.ArrayList;
import java.util.Objects;

public class ForumUtils {
    public static ArrayList<Forum> prepareForumList(Iterable<ForumDTO> dtOs, ForumRepositoryService forumRepositoryService, Integer chId) {
        ArrayList<Forum> arrayForum = new ArrayList<>();

            for (ForumDTO el : dtOs) {
                Forum temp = new Forum();
                temp.setForumId(el.getForumId());
                temp.setForumType(el.getForumType());
                temp.setAdminOnly(el.isAdminOnly());
                temp.setDescription(el.getDescription());
                temp.setName(el.getName());
                temp.setOwnedBy(el.getOwnedBy());
                temp.setVisualOrder(el.getVisualOrder());

                PostLettiDto letto = forumRepositoryService.getLastReadedForums(chId, el.getForumId());
                if( (letto == null && el.getLastModified() != 0) || letto != null && (letto.getLastLettura() < el.getLastModified())){
                    temp.setUnread(true);
                }else { temp.setUnread(false);}

                arrayForum.add(temp);
            }


        return arrayForum;
    }

    public static ForumDTO createForumDto(Forum forum) {
        ForumDTO newForumDto = new ForumDTO();

        newForumDto.setName(forum.getName());
        newForumDto.setDescription(forum.getDescription());
        newForumDto.setAdminOnly(forum.isAdminOnly());
        newForumDto.setVisualOrder(999);
        newForumDto.setOwnedBy(forum.getOwnedBy());
        newForumDto.setForumType(forum.getForumType());
        return newForumDto;
    }

    public static SubForumDTO createSubforumDTO(SubForum subForum, Integer owner) {
        SubForumDTO dto = new SubForumDTO();
        dto.setAdminOnly(subForum.isAdminOnly());
        dto.setName(subForum.getName());
        dto.setForumId(subForum.getForumId());
        dto.setOwnedBy(owner); //ogni sotto-sezione DEVE appartenere allo stesso owner del padre
        dto.setRankVisibility(subForum.getRankVisibility());
        dto.setSubforumType(subForum.getSubforumType());
        dto.setVisualOrder(999);
        return dto;
    }

    public static ArrayList<SubForum> prepareSubforumList(Iterable<SubForumDTO> dtos, V_GuildMembers guildMember, boolean admin, ForumRepositoryService forumRepositoryService, Integer chId) {
        ArrayList<SubForum> lista = new ArrayList<>();
        Integer guildId = guildMember == null ? -1 : guildMember.getGuildId(); //mi serve per filtrare i subforum
        Integer rankPg = guildMember == null ? 10 : guildMember.getGuildLevel(); // mi serve per filtrare in base al livello del pg
        for (SubForumDTO dto: dtos) {
            if( (dto.getOwnedBy() == -1  && rankPg >= dto.getRankVisibility())
                    || (Objects.equals(dto.getOwnedBy(), guildId) && rankPg >= dto.getRankVisibility() )
                    || admin){

                SubForum temp = new SubForum();
                temp.setSubforumId(dto.getSubforumId());
                temp.setName(dto.getName());
                temp.setSubforumType(dto.getSubforumType());
                temp.setRankVisibility(dto.getRankVisibility());
                temp.setAdminOnly(dto.isAdminOnly());
                temp.setForumId(dto.getForumId());
                temp.setOwnedBy(dto.getOwnedBy());
                temp.setVisualOrder(dto.getVisualOrder());

                PostLettiDto letto = forumRepositoryService.getLastReadedSubforum(chId, dto.getForumId() , dto.getSubforumId() );
                if( (letto == null && dto.getLastModified() != 0) || letto != null && (letto.getLastLettura() < dto.getLastModified())){
                    temp.setUnread(true);
                }else { temp.setUnread(false);}

                lista.add(temp);
            }




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
        autore.setCharacterSurname(charDto.getCharacterSurname());
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
        smallCharacter.setCharacterSurname(charDto.getCharacterSurname());
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
