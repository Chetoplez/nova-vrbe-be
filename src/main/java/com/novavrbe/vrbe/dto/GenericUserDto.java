package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "GenericUser")
@Entity
@Data
public class GenericUserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
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
    @Column
    private String role;
}
