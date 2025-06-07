package com.example.beu2w1project.entities;

import com.example.beu2w1project.enumerated.TipoPostazione;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
@Data
@Entity
public class Postazione {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String codice;
    private String descrizione;
    @Enumerated(EnumType.STRING)
    private TipoPostazione tipo;
    @Column(name = "max_occupanti")
    private int maxOccupanti;
    @ManyToOne
    private Edificio edificio;
    @OneToMany(mappedBy = "postazione")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Prenotazione>prenotazioni;

}
