package com.example.tp11;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "solde", types = Compte.class)
public interface CompteProjection {
    Long getId();
    double getSolde();
}

