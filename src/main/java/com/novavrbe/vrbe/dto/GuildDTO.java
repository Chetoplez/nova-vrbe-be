package com.novavrbe.vrbe.dto;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URL;

@Table(name = "GUILD")
@Data
@Entity
public class GuildDTO {
    @Id
    @Column(name = "GUILD_ID")
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String guild_img;
    @Column(name = "ATTIVO")
    private Boolean isVisible;
    @Column
    private String statute;
    @Column
    private String announcement;
}
