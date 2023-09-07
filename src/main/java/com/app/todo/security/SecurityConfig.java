package com.app.todo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.awt.desktop.SystemEventListener;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf -> csrf.disable())).authorizeHttpRequests(requests -> requests
                                .requestMatchers(HttpMethod.GET, "/todo", "/todo/{id}", "/actuator/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/todo").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/todo/{id}").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/todo/{id}").authenticated())
                                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.builder().username("user")
                        .password(passwordEncoder().encode("admin"))
                        .build();
        System.out.println(user.getUsername()+user.getPassword());
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
