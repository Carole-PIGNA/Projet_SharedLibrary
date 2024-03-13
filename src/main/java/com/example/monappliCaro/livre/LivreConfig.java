package com.example.monappliCaro.livre;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class LivreConfig {
    @Bean
    CommandLineRunner commandLineRunner(LivreRepository repository){
        return args ->{
            Livre Garden = new Livre(
                    "Caro" ,
                    "The Garden Within" ,
                    "Dr anita. P" ,
                    LocalDate.of(2023, 12, 1)

            );
            Livre Vivre_Heureux = new Livre(
                    "Caro" ,
                    "Vivre Heureux avec son enfant" ,
                    "Dr Catherine Gueguen" ,
                    LocalDate.of(2019, 12, 1)

            );

            repository.saveAll(
                    List.of(Garden, Vivre_Heureux)

            );
        };
    }
}
