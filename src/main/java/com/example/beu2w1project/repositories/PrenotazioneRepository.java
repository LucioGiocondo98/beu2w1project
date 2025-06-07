package com.example.beu2w1project.repositories;

import com.example.beu2w1project.entities.Postazione;
import com.example.beu2w1project.entities.Prenotazione;
import com.example.beu2w1project.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Long> {
    boolean existsByUtenteAndDataPrenotazione(Utente utente, LocalDate dataPrenotazione);
    boolean existsByPostazioneAndDataPrenotazione(Postazione postazione, LocalDate dataPrenotazione);
}
