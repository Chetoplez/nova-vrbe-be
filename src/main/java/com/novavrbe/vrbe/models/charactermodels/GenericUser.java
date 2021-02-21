package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table
public class GenericUser {
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String lastname;
    @Column
    private Date birthday;
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
    private Date lastlogin;
}
