package com.example.monappliCaro.livre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivreRepository
        extends JpaRepository<Livre, Long> {

    Optional<Livre> findLivreBytitre(String titre);
}

