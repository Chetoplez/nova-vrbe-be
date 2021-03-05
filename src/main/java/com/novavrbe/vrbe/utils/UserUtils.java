package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.GenericUserDto;

import java.security.SecureRandom;
import java.util.Base64;

public class UserUtils {

    public static GenericUserDto createGenericUser(String name, String lastname, String birthday, String gender, String email, String password, String nickname){
        GenericUserDto user = new GenericUserDto();

        user.setName(name);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setGender(gender);
        user.setBirthday(birthday);
        user.setNickname(nickname);
        user.setSalt(createSalt());
        user.setComposedsecret(LoginUtils.composePassword(user, password));

        return user;
    }

    public static void cleanUserSensitiveData(GenericUserDto user){
        user.setSalt("");
        user.setComposedsecret("");
    }

    public static String createSalt(){
        String salt = null;

        byte[] saltBytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(saltBytes);

        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

        salt = encoder.encodeToString(saltBytes);

        return salt;
    }

}
