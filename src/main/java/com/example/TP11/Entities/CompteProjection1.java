package com.example.TP11.Entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "solde", types = Compte.class)
public interface CompteProjection1 {
    double getSolde();
}
