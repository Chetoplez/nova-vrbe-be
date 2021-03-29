package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_GUILDMEMBERS")
@Data
public class GuildMemberListDTO {

    @Id
    @Column(name = "GUILD_ID")
    private Integer GUILD_ID;

    @Column(name = "ROLE_ID")
    private Integer ROLE_ID;

    @Column(name = "CHARACTER_ID")
    private Integer CHARACTER_ID;

    @Column(name = "CHARACTER_NAME")
    private String CHARACTER_NAME;

    @Column(name = "GUILD_LEVEL")
    private Integer GUILD_LEVEL;

    @Column(name = "GUILD_NAME")
    private String GUILD_NAME;

    @Column(name = "ROLE_IMG")
    private String ROLE_IMG;

}
