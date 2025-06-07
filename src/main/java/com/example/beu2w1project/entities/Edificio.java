package com.example.beu2w1project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
@Data
@Entity
public class Edificio {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String indirizzo;
    private String citta;
    @OneToMany(mappedBy = "edificio")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Postazione> postazioni;
}

