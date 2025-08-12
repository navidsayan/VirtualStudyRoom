package com.example.virtualstudyroom.config;

import com.example.virtualstudyroom.model.User;
import com.example.virtualstudyroom.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JANUARY;


@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
            User mariam = new User(

                    "Mariam",
                    LocalDate.of(2000, JANUARY, 5),
                    "mariam.jamal@gmail.com"
            );

            User alex = new User(
                    "Alex",
                    LocalDate.of(2004, JANUARY, 5),
                    "alex@gmail.com"
            );

            repository.saveAll(
                    List.of(mariam, alex)
            );
        };
    }
}
