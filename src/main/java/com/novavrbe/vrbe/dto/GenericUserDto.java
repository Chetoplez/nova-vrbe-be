package com.novavrbe.vrbe.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Table(name = "GenericUser")
@Entity
@Data
public class GenericUserDto  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private BigDecimal id;
    @Column
    private String email;
    @Column
    private String nickname;
    @Column
    private String composedsecret;
    @Column
    private String lastlogin;
    @Column
    private String role;

    @Override
    public List<GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        List<String> roles = Arrays.asList(this.role.split(","));
        for (String role : roles ) {
            list.add(new SimpleGrantedAuthority(role));
        }
        return list;

    }

    @Override
    public String getPassword() {
        return composedsecret;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
