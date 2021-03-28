package com.novavrbe.vrbe.dto;

import lombok.Data;
import org.hibernate.annotations.Immutable;

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

    @Column
    private Integer ROLE_ID;

    @Column
    private Integer CHARACHER_ID;

    @Column
    private String CHARACTER_NAME;

    @Column
    private Integer GUILD_LEVEL;

    @Column
    private String GUILD_NAME;

    @Column
    private String ROLE_IMG;

}
