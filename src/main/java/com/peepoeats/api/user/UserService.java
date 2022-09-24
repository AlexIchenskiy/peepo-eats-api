package com.peepoeats.api.user;

import com.peepoeats.api.JWT.JwtResponse;
import com.peepoeats.api.user.DTO.UserRegistrationRequestDTO;

public interface UserService {

    User add(User user);

    User findByUsername(String username);

    JwtResponse register(UserRegistrationRequestDTO user) throws Exception;

}
