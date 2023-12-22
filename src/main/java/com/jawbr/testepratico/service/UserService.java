package com.jawbr.testepratico.service;

import com.jawbr.testepratico.dto.request.UserRequestDTO;
import com.jawbr.testepratico.exception.InvalidParameterException;
import com.jawbr.testepratico.repository.UserRepository;
import com.jawbr.testepratico.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void createUser(UserRequestDTO userRequestDTO) {
        isUsernameTaken(userRequestDTO);

        User user = new User();
        user.setUsername(userRequestDTO.username());
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        user.setActive(true);
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Procurar User por username
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("username ou senha inválida.");
        }

        Collection<SimpleGrantedAuthority> authority = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authority);
    }

    private void isUsernameTaken(UserRequestDTO userRequest) {
        if(Optional.ofNullable(userRepository.findByUsername(userRequest.username())).isPresent()) {
            throw new InvalidParameterException(
                    String.format("Username '%s' is taken!", userRequest.username()));
        }
    }
}
