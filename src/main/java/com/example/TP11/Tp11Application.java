package com.example.TP11;

import com.example.TP11.Entities.Compte;
import com.example.TP11.Entities.Client;
import com.example.TP11.Entities.TypeCompte;
import com.example.TP11.Repository.ClientRepository;
import com.example.TP11.Repository.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Date;

@SpringBootApplication
public class Tp11Application {

    public static void main(String[] args) {
        SpringApplication.run(Tp11Application.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository,
                            ClientRepository clientRepository,
                            RepositoryRestConfiguration restConfiguration) {

        return args -> {

            restConfiguration.exposeIdsFor(Client.class, Compte.class);

            Client c1 = clientRepository.save(new Client(null, "Amal", "amal@gmail.com", null));
            Client c2 = clientRepository.save(new Client(null, "Ali", "ali@gmail.com", null));

            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c2));

            compteRepository.findAll().forEach(System.out::println);
        };
    }
}
