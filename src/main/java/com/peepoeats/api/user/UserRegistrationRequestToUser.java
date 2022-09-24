package com.peepoeats.api.user;

import com.peepoeats.api.user.DTO.UserRegistrationRequestDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserRegistrationRequestToUser {

    public User mapToEntity(UserRegistrationRequestDTO userDetails) {
        User user = new User();
        user.setFirstname(userDetails.getFirstname());
        user.setLastname(userDetails.getLastname());
        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setAuthorities(Set.of(new SimpleGrantedAuthority(userDetails.getRole().toString())));
        user.setAddress(userDetails.getAddress());
        return user;
    }

}
