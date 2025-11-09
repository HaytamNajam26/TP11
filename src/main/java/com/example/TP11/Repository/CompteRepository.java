package com.example.TP11.Repository;

import com.example.TP11.Entities.Compte;
import com.example.TP11.Entities.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "comptes")
public interface CompteRepository extends JpaRepository<Compte, Long> {

    // Recherche personnalisée par type
    @RestResource(path = "byType")
    List<Compte> findByType(@Param("t") TypeCompte type);
}
