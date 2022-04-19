package com.example.maktabapi_exam.companent;

import com.example.maktabapi_exam.entity.User;
import com.example.maktabapi_exam.entity.enums.RoleEnum;
import com.example.maktabapi_exam.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
     PasswordEncoder passwordEncoder;
    @Value("${spring.sql.init.mode}")
    private String mode;
    @Autowired
    UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")){

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoleEnum(RoleEnum.USER);
            userRepository.save(user);

            User admin = new User();
            admin.setRoleEnum(RoleEnum.ADMIN);
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(admin);
        }
    }


}
