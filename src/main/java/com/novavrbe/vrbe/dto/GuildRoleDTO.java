package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "GUILDROLE")
@Data
@Entity
public class GuildRoleDTO {
    @Id
    @Column
    private Integer role_id;
    @Column
    private Integer guild_id;
    @Column
    private String name;
    @Column
    private Integer salary;
    @Column(name = "MANAGER")
    private Boolean isManager;
    @Column
    private String role_img;
    @Column
    private String description;
    @Column
    private Integer guild_level;
}
