package com.novavrbe.vrbe.models;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String name;
    private String lastname;
    private Date birthday;
    private String email;
    private String gender;
    private String nickname;
}
