package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.GenericUserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;

public class UserUtils {

    public static GenericUserDto createGenericUser(PasswordEncoder passwordEncoder, String email, String password, String nickname){
        GenericUserDto user = new GenericUserDto();


        user.setEmail(email);
        user.setNickname(nickname);
        user.setRole("USER");
        user.setComposedsecret(passwordEncoder.encode(password));

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
