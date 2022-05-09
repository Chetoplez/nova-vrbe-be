package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Chat")
@Data
public class ChatDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatId;
    @Column
    private Date creationDate;
    @Column
    private Boolean active;
    @Column
    private Integer idLuogo;
    @Column
    private Boolean privateChat;
    @Column
    private Integer characterId;
    @Column
    private String expirationDate;
    @Column
    private Boolean fixed;
    @Column
    private Integer pos_x;
    @Column
    private Integer pos_y;

}
