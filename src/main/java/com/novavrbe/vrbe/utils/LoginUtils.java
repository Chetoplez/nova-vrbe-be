package com.novavrbe.vrbe.utils;

import com.google.common.hash.Hashing;
import com.novavrbe.vrbe.models.charactermodels.GenericUser;

import java.nio.charset.StandardCharsets;

public class LoginUtils {

    public static String composePassword(GenericUser user, String password){
        String psw = "";
        psw = password.concat(user != null ? user.getSalt() : "");

        psw = Hashing.sha256().hashString(psw, StandardCharsets.UTF_8).toString();

        return psw;
    }

    public static boolean canLogin(String password, GenericUser user){
        boolean canLog = false;

        String psw = composePassword(user, password);
        canLog = psw.equals(user.getComposedsecret());

        return canLog;
    }

}