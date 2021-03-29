package com.novavrbe.vrbe.dto;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GUILDMEMBER")
@Data
public class GuildMemberDTO {

    @Id
    @Column
    private Integer CHARACTER_ID;
    @Column
    private Integer ROLE_ID;

}
