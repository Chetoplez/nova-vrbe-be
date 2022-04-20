package com.novavrbe.vrbe.filters;

import com.novavrbe.vrbe.dto.GenericUserDto;
import com.novavrbe.vrbe.repositories.impl.UserRepositoryService;
import com.novavrbe.vrbe.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private UserRepositoryService userRepositoryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //Esaminiamo la richiesta in ingresso per vedere se il JWT è valido
        final String authHeader = request.getHeader("Authorization");
        String username = null; String jwt = null;

        if(authHeader != null && authHeader.startsWith("Fervm ")){
            jwt = authHeader.substring(6);
            username = jwtUtils.extractUsername(jwt);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            GenericUserDto userDetails = userRepositoryService.loadUserByUsername(username);
            if(jwtUtils.validateToken(jwt,userDetails)) {
                UsernamePasswordAuthenticationToken userNameAuh = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                userNameAuh.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userNameAuh);
            }
        }

        filterChain.doFilter(request, response);

    }
}
