package com.novavrbe.vrbe.dto;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Data
public class V_GuildMembers {

    @Column(name = "GUILDID")
    private Integer guildId;

    @Column(name = "ROLEID")
    private Integer roleId;
    @Id
    @Column(name = "CHARACTERID")
    private Integer characterId;

    @Column(name = "CHARACTERNAME")
    private String characterName;

    @Column(name = "CHARACTERSURNAME")
    private String characterSurname;

    @Column(name = "GUILDLEVEL")
    private Integer guildLevel;

    @Column(name = "ROLENAME")
    private String roleName;

    @Column(name = "ROLEIMG")
    private String roleImg;

    @Column(name = "ROLESALARY")
    private Integer roleSalary;

}
