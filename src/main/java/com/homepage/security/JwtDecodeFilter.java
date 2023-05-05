//package com.homepage.security;
//
//import java.io.IOException;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.auth0.jwt.interfaces.JWTVerifier;
//import com.homepage.user.entity.User;
//import com.homepage.user.service.UserDetailsServiceImpl;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
////로그인이 성공한 사용자에게 받은 JWT를 해석해서 누가 서버에 접근했는지 알아오는 클래스
//@Component
//public class JwtDecodeFilter extends OncePerRequestFilter {
//    private final UserDetailsServiceImpl userDetailsService;
//
//    public JwtDecodeFilter(UserDetailsServiceImpl userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String header = request.getHeader("Authorization"); // Authorization: Bearer aaa.bbb.ccc
//        if (header != null && header.startsWith("Bearer ")) {
//            try {
//                String accessToken = header.substring(7);
//
//                // JWT 해석
//                Algorithm algorithm = Algorithm.HMAC256("secret");
//                JWTVerifier verifier = JWT.require(algorithm).withIssuer("issuer").build();
//                DecodedJWT jwt = verifier.verify(accessToken);
//                String username = jwt.getSubject(); // 아이디(학번)
//
//                User user = (User) userDetailsService.loadUserByUsername(username);
//                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            } catch (JWTVerificationException exception) {
//                exception.printStackTrace();
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}