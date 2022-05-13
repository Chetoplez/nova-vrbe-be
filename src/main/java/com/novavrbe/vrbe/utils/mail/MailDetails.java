package com.novavrbe.vrbe.utils.mail;

import lombok.Data;

@Data
public class MailDetails {
    private String recipient;
    private String subject;
    private String body;
    private String nickname;
}
