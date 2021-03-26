package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "GUILDBANK")
@Data
@Entity
public class GuildBankDTO {
    @Id
    @Column(name = "GUILD_ID")
    private Integer guildId;
    @Column
    private Integer amount;
}
