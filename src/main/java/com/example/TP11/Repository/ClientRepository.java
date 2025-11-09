package com.example.TP11.Repository;

import com.example.TP11.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
}
