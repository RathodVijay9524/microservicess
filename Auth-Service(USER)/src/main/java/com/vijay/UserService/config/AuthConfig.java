package com.vijay.UserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService getUserDetailsService(){
     /*   UserDetails normalUser= User.withUsername("normal")
                .password(passwordEncoder().encode("normal"))
                .roles("NORMAL").build();

        UserDetails adminUser=User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN").build();

        UserDetails user=User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER").build();   */

        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests().requestMatchers("/order/**","/product/**").permitAll()
                .and()
                .authorizeRequests().requestMatchers("/user/**","/order/**").authenticated()
                .and()
                .formLogin(form-> form
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(getUserDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


}
