package com.example.assigningandremovingrolesfromusers.security.user;

import com.example.assigningandremovingrolesfromusers.entities.User;
import com.example.assigningandremovingrolesfromusers.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class HotelUserDetailsService implements UserDetailsService{
        private final IUserRepository userRepository;
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User user = (User) userRepository.findByUserEmail(email)
                    .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
            return HotelUserDetails.buildUserDetails(user);
        }
}
