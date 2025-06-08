package com.example.beu2w1project;

import com.example.beu2w1project.entities.Edificio;
import com.example.beu2w1project.entities.Postazione;
import com.example.beu2w1project.entities.Prenotazione;
import com.example.beu2w1project.entities.Utente;
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
        System.out.print("Inserisci il tuo nome: ");
        String nome = scanner.nextLine();


        System.out.print("Inserisci uno username: ");
        String username = scanner.nextLine();
        Optional<Utente> existingUser = utenteRepo.findByUsername(username);
        Utente utente;

        if (existingUser.isPresent()) {
            utente = existingUser.get();
            System.out.println("Bentornato " + utente.getNomeCompleto() + "!");
        } else {
            utente = new Utente();
            utente.setNomeCompleto(nome);
            utente.setUsername(username);
            utente = utenteRepo.save(utente);
            System.out.println("Utente registrato con successo!");
        }

        System.out.println("Utente: " + utente.getUsername() + " - " + utente.getNomeCompleto());
        boolean running = true;
        while (running) {
            System.out.println("\nScegli un'opzione:");
            System.out.println("1. Prenota una postazione");
            System.out.println("2. Menu amministratore");
            System.out.println("0. Esci");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> {
                    List<Postazione> postazioni = postazioneRepo.findAll();
                    if (postazioni.isEmpty()) {
                        System.out.println("Nessuna postazione disponibile.");
                        break;
                    } else {
                        System.out.println("Postazioni disponibili:");
                        postazioni.forEach(p -> System.out.println(
                                "ID: " + p.getId() +
                                        ", Tipo: " + p.getTipo() +
                                        ", Descrizione: " + p.getDescrizione() +
                                        ", Max persone: " + p.getMaxOccupanti() +
                                        ", Edificio: " + (p.getEdificio() != null ? p.getEdificio().getNome() : "N/A")+
                                        ", Città: " + (p.getEdificio() != null ? p.getEdificio().getCitta() : "N/A")
                        ));
                    }
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
                case "2" -> {
                    adminMenu(scanner);
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

    private void adminMenu(Scanner scanner) {
        boolean adminRunning = true;

        while (adminRunning) {
            System.out.println("\nMenu Amministratore:");
            System.out.println("1. Crea nuova postazione");
            System.out.println("2. Visualizza tutte le postazioni");
            System.out.println("3. Visualizza tutti gli edifici");
            System.out.println("4. Visualizza tutte le prenotazioni");
            System.out.println("0. Torna indietro");

            String sceltaAdmin = scanner.nextLine();

            switch (sceltaAdmin) {
                case "1" -> {
                    System.out.print("Inserisci tipo postazione (OPENSPACE, PRIVATO, SALA_RIUNIONI): ");
                    TipoPostazione tipo = TipoPostazione.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Inserisci descrizione: ");
                    String descrizione = scanner.nextLine();

                    System.out.print("Inserisci numero max persone: ");
                    int maxPersone = Integer.parseInt(scanner.nextLine());

                    System.out.print("Inserisci ID edificio: ");
                    long idEdificio = Long.parseLong(scanner.nextLine());

                    var edificio = edificioRepo.findById(idEdificio);
                    if (edificio.isEmpty()) {
                        System.out.println("Edificio non trovato!");
                        break;
                    }

                    Postazione nuovaPostazione = new Postazione();
                    nuovaPostazione.setTipo(tipo);
                    nuovaPostazione.setDescrizione(descrizione);
                    nuovaPostazione.setMaxOccupanti(maxPersone);
                    nuovaPostazione.setEdificio(edificio.get());

                    postazioneRepo.save(nuovaPostazione);
                    System.out.println("Postazione creata con successo!");
                }
                case "2" -> {
                    List<Postazione> postazioni = postazioneRepo.findAll();
                    if (postazioni.isEmpty()) {
                        System.out.println("Nessuna postazione trovata.");
                    } else {
                        postazioni.forEach(p -> System.out.println(
                                "ID: " + p.getId() +
                                        ", Tipo: " + p.getTipo() +
                                        ", Descrizione: " + p.getDescrizione() +
                                        ", Max persone: " + p.getMaxOccupanti() +
                                        ", Edificio: " + (p.getEdificio() != null ? p.getEdificio().getNome() : "N/A")
                        ));
                    }
                }
                case "3" -> {
                    List<Edificio> edifici = edificioRepo.findAll();
                    if (edifici.isEmpty()) {
                        System.out.println("Nessun edificio presente.");
                    } else {
                        System.out.println("Elenco edifici:");
                        edifici.forEach(e -> System.out.println("ID: " + e.getId() + ", Nome: " + e.getNome() + ", Città: " + e.getCitta()));
                    }
                }
                case "4" -> {
                    List<Prenotazione> prenotazioni = prenotazioneService.findAll();
                    if (prenotazioni.isEmpty()) {
                        System.out.println("Nessuna prenotazione presente.");
                    } else {
                        System.out.println("Elenco prenotazioni:");
                        prenotazioni.forEach(pr -> System.out.println(
                                "ID: " + pr.getId() +
                                        ", Utente: " + (pr.getUtente() != null ? pr.getUtente().getUsername() : "Nessuno") +
                                        ", Postazione: " + (pr.getPostazione() != null ? pr.getPostazione().getDescrizione() : "Nessuna") +
                                        ", Data: " + pr.getDataPrenotazione()
                        ));
                    }
                }
                case "0" -> adminRunning = false;
                default -> System.out.println("Scelta non valida.");
            }
        }
    }
}
