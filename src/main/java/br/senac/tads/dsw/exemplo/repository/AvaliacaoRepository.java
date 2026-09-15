package br.senac.tads.dsw.exemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.exemplo.model.Avaliacao;
import br.senac.tads.dsw.exemplo.model.Produto;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    
}

