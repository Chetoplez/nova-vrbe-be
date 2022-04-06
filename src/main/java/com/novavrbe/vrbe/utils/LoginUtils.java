package com.novavrbe.vrbe.utils;

import com.google.common.hash.Hashing;
import com.novavrbe.vrbe.dto.GenericUserDto;

import java.nio.charset.StandardCharsets;

public class LoginUtils {

    public static String composePassword(GenericUserDto user, String password){
        String psw = "";
        psw = password.concat(user != null ? "" : "");

        psw = Hashing.sha256().hashString(psw, StandardCharsets.UTF_8).toString();

        return psw;
    }

    public static boolean canLogin(String password, GenericUserDto user){
        boolean canLog = false;

        String psw = composePassword(user, password);
        canLog = psw.equals(user.getComposedsecret());

        return canLog;
    }

}
