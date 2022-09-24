package com.peepoeats.api.user;

import com.peepoeats.api.JWT.JwtResponse;
import com.peepoeats.api.JWT.JwtTokenUtil;
import com.peepoeats.api.user.DTO.UserRegistrationRequestDTO;
import com.peepoeats.api.user.exception.UserAlreadyExistsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRegistrationRequestToUser mapper;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           JwtTokenUtil jwtTokenUtil, UserRegistrationRequestToUser mapper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.mapper = mapper;
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public JwtResponse register(UserRegistrationRequestDTO userDetails) throws Exception {
        // check if username/email is already registered
        if (userRepository.findByUsername(userDetails.getUsername()) != null) {
            throw new UserAlreadyExistsException(userDetails.getUsername());
        } else if (userRepository.findByEmail(userDetails.getEmail()) != null) {
            throw new UserAlreadyExistsException(userDetails.getEmail());
        } else {
            User user = mapper.mapToEntity(userDetails);
            this.add(user);
            authenticate(user.getUsername(), user.getPassword(), user.getAuthorities());
            return new JwtResponse(jwtTokenUtil.generateToken(user));
        }
    }

    private void authenticate(String username, String password, Set<GrantedAuthority> authorities) throws Exception {
        // TODO: bad credentials exception after login implementation
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, password, authorities));
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

}
