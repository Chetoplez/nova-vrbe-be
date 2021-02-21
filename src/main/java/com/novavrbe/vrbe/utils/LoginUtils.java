package com.novavrbe.vrbe.utils;

import com.google.common.hash.Hashing;
import com.novavrbe.vrbe.models.charactermodels.User;

import java.nio.charset.StandardCharsets;

public class LoginUtils {

    public static String composePassword(User user, String password){
        String psw = "";
        psw = password.concat(user != null ? user.getSalt() : "");

        psw = Hashing.sha256().hashString(psw, StandardCharsets.UTF_8).toString();

        return psw;
    }

    public static boolean canLogin(String password, User user){
        boolean canLog = false;

        String psw = composePassword(user, password);
        canLog = psw.equals(user.getComposedsecret());

        return canLog;
    }
}
