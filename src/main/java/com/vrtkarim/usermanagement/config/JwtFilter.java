package com.vrtkarim.usermanagement.config;

import com.vrtkarim.usermanagement.entity.User;
import com.vrtkarim.usermanagement.exception.UserNotFoundException;
import com.vrtkarim.usermanagement.service.JwtService;
import com.vrtkarim.usermanagement.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter  {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserService userService;
    @Autowired
    public JwtFilter(HandlerExceptionResolver handlerExceptionResolver, JwtService jwtService, UserService userService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader==null){
            chain.doFilter(request, response);
            System.out.println(request);

        }else{
            try{
                final String token = authHeader.replace("Bearer ", "");
                final String useremail = jwtService.extractUsername(token);


                if (!useremail.isEmpty()) {
                    System.out.println("inside if statement");
                    User user = userService.findByEmail(useremail).orElseThrow(() -> new UserNotFoundException(useremail));
                    System.out.println(user);
                    System.out.println(jwtService.isTokenValid(token, user));
                    if(jwtService.isTokenValid(token, user)){

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
                chain.doFilter(request, response);

            }catch (Exception e){
                handlerExceptionResolver.resolveException(request, response, null, e);
            }
        }


    }
}

