package dev.blazo.base.utils;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.blazo.base.entities.Authority;
import dev.blazo.base.entities.User;
import dev.blazo.base.repositories.AuthorityRepository;
import dev.blazo.base.repositories.UserRepository;

/**
 * Clase de inicialización de la aplicación.
 * 
 * Esta clase se ejecuta al inicio de la aplicación y se utiliza para
 * inicializar la base de datos con datos de prueba.
 */
@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (authorityRepository.count() == 0) {
            authorityRepository.saveAll(List.of(new Authority(AuthorityName.ADMIN), new Authority(AuthorityName.READ),
                    new Authority(AuthorityName.WRITE)));
        }

        if (userRepository.count() == 0) {
            Optional<Authority> admin = authorityRepository.findByName(AuthorityName.ADMIN);
            Optional<Authority> write = authorityRepository.findByName(AuthorityName.WRITE);
            Optional<Authority> read = authorityRepository.findByName(AuthorityName.READ);

            if (!admin.isPresent() || !write.isPresent() || !read.isPresent())
                return;

            var encoders = PasswordEncoderFactories.createDelegatingPasswordEncoder();

            userRepository.saveAll(List.of(
                    new User("blazo-admin", encoders.encode("12345a"), List.of(admin.get())),
                    new User("blazo-write", encoders.encode("12345w"), List.of(write.get())),
                    new User("blazo-read", encoders.encode("12345r"), List.of(read.get()))));
        }

    }

}
