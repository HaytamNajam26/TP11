package com.example.tp11;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "comptes", collectionResourceRel = "comptes", itemResourceRel = "compte")
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    
    @RestResource(path = "/byType")
    public List<Compte> findByType(@Param("t") TypeCompte type);
}

