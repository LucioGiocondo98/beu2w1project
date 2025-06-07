package com.example.beu2w1project.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"utente_username","dataPrenotazione"})})
public class Prenotazione {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Utente utente;
    @ManyToOne
    private Postazione postazione;
    @Column(name = "data_prenotazione")
    private LocalDate dataPrenotazione;

}
