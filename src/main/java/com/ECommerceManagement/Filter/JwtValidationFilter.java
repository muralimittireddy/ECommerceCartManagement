package com.ECommerceManagement.Filter;

import com.ECommerceManagement.Config.EcomUserDetails;
import com.ECommerceManagement.Jwtoken.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.SecretKey;
import java.io.IOException;



@Component
    public class JwtValidationFilter extends OncePerRequestFilter {
        @Autowired
        JwtUtil jwtUtil;
        @Autowired
        EcomUserDetails ecomUserDetails;
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String bearerToken = request.getHeader("JWT");
            String token=null;
            String username=null;
            UserDetails user=null;
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.substring(7);  // Remove  "Bearer " prefix
                username = jwtUtil.extractUsername(token);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication()==null)
            {
                user=ecomUserDetails.loadUserByUsername(username);
                if(user!=null && jwtUtil.validateToken(token,username))
                {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                            user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }


//                    String jwt = request.getHeader("JWT");

//            if (null != jwt) {
//                try {
//
//                    String secret = "secret";
//                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//                    if (null != secretKey) {
//                        Claims claims = Jwts.parser().verifyWith(secretKey)
//                                .build().parseSignedClaims(jwt).getPayload();
//                        String username = String.valueOf(claims.get("username"));
//                        String authorities = String.valueOf(claims.get("authorities"));
//                        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
//                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                    }
//                }
//                 catch (Exception e) {
//                    throw new BadCredentialsException("Invalid Token received!");
//                }

                filterChain.doFilter(request, response);
            }

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            return request.getServletPath().equals("/login");
        }
    }

