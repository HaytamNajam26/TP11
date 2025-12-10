package com.example.tp11;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "clients", collectionResourceRel = "clients", itemResourceRel = "client")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}

