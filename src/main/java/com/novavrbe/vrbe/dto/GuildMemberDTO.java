package com.novavrbe.vrbe.dto;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "GUILDMEMBER")
@Data
public class GuildMemberDTO implements Serializable {

    @Id
    @Column
    private Integer CHARACTER_ID;
    @Column
    private Integer ROLE_ID;

}
