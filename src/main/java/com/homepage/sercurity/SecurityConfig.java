package com.homepage.sercurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.java.Log;

@Configuration
@Log
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(
        		new AntPathRequestMatcher("/**")).permitAll()
	        .and()
	        	.csrf().ignoringRequestMatchers(
	                new AntPathRequestMatcher("/main/**"))
	    	.and()
	        	.formLogin()
	        	.loginPage("/login")
	        	.defaultSuccessUrl("/")
	        ;
        return http.build();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}