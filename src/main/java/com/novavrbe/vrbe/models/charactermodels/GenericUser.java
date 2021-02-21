package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class GenericUser {
    @Id
    private String id;
    private String name;
    private String lastname;
    private Date birthday;
    private String email;
    private String gender;
    private String nickname;
    private String salt;
    private String composedSecret;
    private Date lastLogin;
}
