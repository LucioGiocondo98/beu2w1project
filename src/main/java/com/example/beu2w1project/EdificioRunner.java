package com.example.beu2w1project;

import com.example.beu2w1project.entities.Edificio;
import com.example.beu2w1project.repositories.EdificioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EdificioRunner implements CommandLineRunner {

    private final EdificioRepository edificioRepo;

    public EdificioRunner(EdificioRepository edificioRepo) {
        this.edificioRepo = edificioRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (edificioRepo.count() == 0) {
            Edificio e1 = new Edificio();
            e1.setNome("Palazzo Ncrediiible");
            e1.setCitta("Napoli");
            e1.setIndirizzo("via Medina");
            edificioRepo.save(e1);

            Edificio e2 = new Edificio();
            e2.setNome("Palazzo Ncredible2");
            e2.setCitta("Roma");
            e2.setIndirizzo("Corso Roma");
            edificioRepo.save(e2);

        }
    }
}