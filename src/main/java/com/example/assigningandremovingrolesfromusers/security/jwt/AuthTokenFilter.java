package com.example.assigningandremovingrolesfromusers.security.jwt;

import com.example.assigningandremovingrolesfromusers.security.user.HotelUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter{
        @Autowired
        private JwtUtils jwtUtils;
        @Autowired
        private HotelUserDetailsService hotelUserDetailsService;
        private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {

            try{
                String jwt = parseJwt(request);
                if(jwt != null && jwtUtils.validateToken(jwt)){
                    String email = jwtUtils.getUserNameFromToken(jwt);
                    UserDetails userDetails = hotelUserDetailsService.loadUserByUsername(email);
                    var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }catch (Exception ex){
                logger.error("Can no set user authentication : {} ", ex.getMessage());
            }
            filterChain.doFilter(request,response);
        }

        private String parseJwt(HttpServletRequest request) {
            String headerAuth = request.getHeader("Authorization");
            if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
                return headerAuth.substring(7);
            }
            return null;
        }
}
