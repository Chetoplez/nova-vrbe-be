package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table
@Entity
@Data
public class GenericUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;
    @Column
    private String name;
    @Column
    private String lastname;
    @Column
    private String birthday;
    @Column
    private String email;
    @Column
    private String gender;
    @Column
    private String nickname;
    @Column
    private String salt;
    @Column
    private String composedsecret;
    @Column
    private String lastlogin;
}
