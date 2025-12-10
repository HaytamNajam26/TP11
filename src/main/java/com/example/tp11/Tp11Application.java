package com.example.tp11;

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
    CommandLineRunner start(CompteRepository compteRepository, ClientRepository clientRepository, RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Compte.class, Client.class);
            // Configuration de la pagination par défaut
            restConfiguration.setDefaultPageSize(10);
            restConfiguration.setMaxPageSize(100);
            
            // Initialisation des clients
            Client c1 = clientRepository.save(new Client(null, "Amal", null, null));
            Client c2 = clientRepository.save(new Client(null, "Ali", null, null));
            
            // Initialisation des comptes associés aux clients
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c2));
            

        };
    }

}

