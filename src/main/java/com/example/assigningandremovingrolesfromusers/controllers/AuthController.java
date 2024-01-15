package com.example.assigningandremovingrolesfromusers.controllers;

import com.example.assigningandremovingrolesfromusers.dto.JwtResponse;
import com.example.assigningandremovingrolesfromusers.dto.LoginRequest;
import com.example.assigningandremovingrolesfromusers.entities.User;
import com.example.assigningandremovingrolesfromusers.exception.UserAlreadyExistException;
import com.example.assigningandremovingrolesfromusers.security.jwt.JwtUtils;
import com.example.assigningandremovingrolesfromusers.security.user.HotelUserDetails;
import com.example.assigningandremovingrolesfromusers.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
        private final IUserService userService;
        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;

        @GetMapping("/register")
        public ResponseEntity<?> userRegister(User user){
            try{
                userService.registerUser(user);
                return ResponseEntity.ok("Registration is successfully");
            }catch (UserAlreadyExistException ex){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
            }
        }

        @PostMapping("/login")
        public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtTokenForUser(authentication);
            HotelUserDetails hotelUserDetails = (HotelUserDetails) authentication.getPrincipal();
            List<String> roles = hotelUserDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority).toList();
            return ResponseEntity.ok(new JwtResponse(
                    hotelUserDetails.getId(),
                    hotelUserDetails.getEmail(),
                    jwt,
                    roles ));
        }
}
