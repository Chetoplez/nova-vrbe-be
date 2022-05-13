package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.GenericUserDto;
import com.novavrbe.vrbe.models.enumerations.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class UserUtils {

    public static GenericUserDto createGenericUser(PasswordEncoder passwordEncoder, String email, String password, String nickname){
        GenericUserDto user = new GenericUserDto();


        user.setEmail(email);
        user.setNickname(nickname);
        user.setRole(Roles.ROLE_USER.name());
        user.setComposedsecret(passwordEncoder.encode(password));
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        try {
            Date lastLogin = sdfDate.parse(strDate);
            user.setLastlogin(lastLogin.getTime());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return user;
    }

    public static void cleanUserSensitiveData(GenericUserDto user){

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
