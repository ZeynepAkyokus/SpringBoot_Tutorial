package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return  args -> {
            Student zeynep = new Student( "Zeynep", "zeynep@gmail.com",
                    LocalDate.of(1998, 6, 26));

            Student ahmet = new Student("Ahmet", "ahmet@gmail.com",
                    LocalDate.of(2002, 6, 26));

            Student ali = new Student("Ali","ali@gmail.com",LocalDate.parse("1995-11-23"));

            repository.saveAll(List.of(zeynep,ahmet,ali));
        };
    }
}
