package com.example.beu2w1project;

import com.example.beu2w1project.entities.Postazione;
import com.example.beu2w1project.entities.Prenotazione;
import com.example.beu2w1project.enumerated.TipoPostazione;
import com.example.beu2w1project.repositories.EdificioRepository;
import com.example.beu2w1project.repositories.PostazioneRepository;
import com.example.beu2w1project.repositories.UtenteRepository;
import com.example.beu2w1project.services.PrenotazioneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class PrenotazioneRunner implements CommandLineRunner {

    private final PostazioneRepository postazioneRepo;
    private final UtenteRepository utenteRepo;
    private final EdificioRepository edificioRepo;
    private final PrenotazioneService prenotazioneService;

    public PrenotazioneRunner(PostazioneRepository postazioneRepo,
                         UtenteRepository utenteRepo,
                         EdificioRepository edificioRepo,
                         PrenotazioneService prenotazioneService) {
        this.postazioneRepo = postazioneRepo;
        this.utenteRepo = utenteRepo;
        this.edificioRepo = edificioRepo;
        this.prenotazioneService = prenotazioneService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Benvenuto nel sistema di gestione prenotazioni");

        boolean running = true;
        while (running) {
            System.out.println("\nScegli un'opzione:");
            System.out.println("1. Cerca postazioni");
            System.out.println("2. Prenota una postazione");
            System.out.println("0. Esci");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> {
                    System.out.print("Inserisci tipo postazione (OPENSPACE, PRIVATO, SALA_RIUNIONI): ");
                    TipoPostazione tipo = TipoPostazione.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Inserisci città: ");
                    String citta = scanner.nextLine();

                    List<Postazione> trovate = postazioneRepo.findByTipoAndEdificio_Citta(tipo, citta);
                    if (trovate.isEmpty()) {
                        System.out.println("Nessuna postazione trovata.");
                    } else {
                        System.out.println("Postazioni disponibili:");
                        trovate.forEach(p -> System.out.println(p.getId() + " - " + p.getDescrizione()));
                    }
                }
                case "2" -> {
                    System.out.print("Inserisci username: ");
                    String username = scanner.nextLine();

                    System.out.print("ID postazione da prenotare: ");
                    long idPostazione = Long.parseLong(scanner.nextLine());

                    System.out.print("Data prenotazione (YYYY-MM-DD): ");
                    LocalDate data = LocalDate.parse(scanner.nextLine());

                    try {
                        Prenotazione pren = prenotazioneService.prenota(username, idPostazione, data);
                        System.out.println("Prenotazione riuscita! Dettagli: " + pren);
                    } catch (Exception e) {
                        System.err.println("Errore: " + e.getMessage());
                    }
                }
                case "0" -> {
                    running = false;
                    System.out.println("Chiusura programma.");
                }
                default -> System.out.println("Scelta non valida.");
            }
        }

        scanner.close();
    }
}
