package com.hcc.utils;

import com.hcc.entities.Authority;
import com.hcc.entities.User;
import com.hcc.repositories.AuthorityRepository;
import com.hcc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class UserDataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
        loadAuthorityData();
    }

    private void loadUserData() {
        if (userRepository.count() < 1) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            String password = passwordEncoder.encode("12341234");
            User user = new User(Date.valueOf(LocalDate.now()),"scott" , password);

            userRepository.save(user);
        }
    }

    private void loadAuthorityData() {
        if (authorityRepository.count() < 1) {

            Authority authority = new Authority("LEARNER_ROLE", userRepository.findByUsername("scott").get());
            authorityRepository.save(authority);
        }
    }
}
