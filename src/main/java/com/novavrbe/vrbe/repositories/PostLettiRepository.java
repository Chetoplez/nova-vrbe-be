package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.PostLettiDto;
import com.novavrbe.vrbe.models.forumcontroller.IdPostLetti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLettiRepository extends JpaRepository<PostLettiDto, IdPostLetti> {
        @Query(value = "select pl.chId, pl.postId,pl.forumId,pl.subforumId, pl.lastLettura from post_letti pl where pl.chId = ?1 and pl.forumId= ?2 group by pl.forumId" , nativeQuery = true)
        PostLettiDto findByChIdAndForumIdGroupByForum(Integer chId, Integer forumId);
}
