package com.example.beu2w1project.services;

import com.example.beu2w1project.entities.Postazione;
import com.example.beu2w1project.entities.Prenotazione;
import com.example.beu2w1project.entities.Utente;
import com.example.beu2w1project.repositories.PostazioneRepository;
import com.example.beu2w1project.repositories.PrenotazioneRepository;
import com.example.beu2w1project.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepo;

    @Autowired
    private UtenteRepository utenteRepo;

    @Autowired
    private PostazioneRepository postazioneRepo;

    public Prenotazione prenota(String username, Long idPostazione, LocalDate data) {
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Postazione postazione = postazioneRepo.findById(idPostazione)
                .orElseThrow(() -> new RuntimeException("Postazione non trovata"));

        if (prenotazioneRepo.existsByUtenteAndDataPrenotazione(utente, data)) {
            throw new RuntimeException("Hai già una prenotazione per questa data.");
        }

        if (prenotazioneRepo.existsByPostazioneAndDataPrenotazione(postazione, data)) {
            throw new RuntimeException("Postazione già prenotata per questa data.");
        }

        Prenotazione pren = new Prenotazione();
        pren.setUtente(utente);
        pren.setPostazione(postazione);
        pren.setDataPrenotazione(data);
        return prenotazioneRepo.save(pren);
    }



}
