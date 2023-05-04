package com.homepage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.homepage.user.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtDecodeFilter jwtDecodeFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtDecodeFilter jwtDecodeFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtDecodeFilter = jwtDecodeFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager);
        jwtLoginFilter.setUsernameParameter("id");
        jwtLoginFilter.setPasswordParameter("password");

        return http
            .csrf().disable()
            .httpBasic().disable()
            .authorizeHttpRequests()
	            .requestMatchers("/user/**").authenticated() // user 요청에 대해 로그인 요구
	            .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // manager 요청에 대해 manager나 admin 권한 요구
	            .requestMatchers("/admin/**").hasRole("ADMIN") // manager 요청에 대해 admin 권한 요구
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()	
            .loginPage("/user/login")	// 로그인 페이지를 제공하는 URL을 설정함
            .successForwardUrl("/main/main.do").permitAll()// 로그인 성공 URL
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authenticationManager(authenticationManager)
            .addFilterBefore(jwtDecodeFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }
}


//@Configuration
//@EnableWebSecurity
//@Log
//public class SecurityConfig {
//	
//	@Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		
//		return http
//	            .csrf().disable()
////	            .formLogin().disable()
////	            .httpBasic().disable()
//	            .authorizeHttpRequests()
//		            .requestMatchers("/user/**").authenticated()
//	                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
//	                .requestMatchers("/admin/**").hasRole("ADMIN")
//	                .anyRequest().permitAll()
//	                .and().formLogin().loginPage("/api/login")
//	            .and()
//	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//	            .authenticationManager(authenticationManager)
//	            .addFilterBefore(jwtDecodeFilter, UsernamePasswordAuthenticationFilter.class)
//	            .addFilterAt(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
//	            .build();
//  }
//	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}
