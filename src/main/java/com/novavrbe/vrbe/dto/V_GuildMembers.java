package com.novavrbe.vrbe.dto;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity

@Immutable
@Data
public class V_GuildMembers implements Serializable {


    @Column(name = "GUILD_ID")
    private Integer guildId;
    @Id
    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "CHARACTER_ID")
    private Integer characterId;

    @Column(name = "CHARACTER_NAME")
    private String characterName;

    @Column(name = "GUILD_LEVEL")
    private Integer guildLevel;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "ROLE_IMG")
    private String roleImg;

}
