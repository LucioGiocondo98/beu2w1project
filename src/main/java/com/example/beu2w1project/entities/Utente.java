package com.example.beu2w1project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
@Data
@Entity
public class Utente {
@Id
    private String username;
@Column(name = "nome_completo")
private String nomeCompleto;
private String email;
@OneToMany(mappedBy = "utente")
@ToString.Exclude
@EqualsAndHashCode.Exclude
private List<Prenotazione>prenotazioni;
}
