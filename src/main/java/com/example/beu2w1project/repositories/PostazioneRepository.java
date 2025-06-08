package com.example.beu2w1project.repositories;

import com.example.beu2w1project.entities.Postazione;
import com.example.beu2w1project.enumerated.TipoPostazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostazioneRepository extends JpaRepository<Postazione,Long> {
List<Postazione>findByTipoAndEdificio_CittaIgnoreCase(TipoPostazione tipo,String citta);

}
