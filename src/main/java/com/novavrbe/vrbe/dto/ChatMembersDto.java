package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ChatMembers")
@Data
public class ChatMembersDto {
    @Id
    private String chatId;
    @Column
    private Integer characterId;
}
