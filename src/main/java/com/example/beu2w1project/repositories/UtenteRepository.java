package com.example.beu2w1project.repositories;

import com.example.beu2w1project.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente,String> {
    Optional<Utente> findByUsername(String username);
}
