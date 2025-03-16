package com.ramadan.dayone.demo.config;

import com.ramadan.dayone.demo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration

public class ApplicatonConfiguration {
    private final UserRepository userRepository;
    public ApplicatonConfiguration(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Bean
    UserDetailsService userDetailsService() {
        return
                userRepository::findByEmail;
    }
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration. getAuthenticationManager();
    }
    @Bean
    AuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         authProvider.setUserDetailsService(userDetailsService());
         authProvider.setPasswordEncoder(passwordEncoder());
         return authProvider;
    }
}
