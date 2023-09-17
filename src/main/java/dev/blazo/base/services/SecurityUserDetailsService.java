package dev.blazo.base.services;

import java.util.Optional;

import org.springframework.security.core.userdetails. UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.blazo.base.entities.User;
import dev.blazo.base.repositories.UserRepository;
import dev.blazo.base.security.SecurityUser;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new SecurityUser(user.get());
    }

}
