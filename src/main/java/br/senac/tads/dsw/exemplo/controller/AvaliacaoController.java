package br.senac.tads.dsw.exemplo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.senac.tads.dsw.exemplo.model.Avaliacao;
import br.senac.tads.dsw.exemplo.repository.AvaliacaoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoRepository repository;

    public AvaliacaoController(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Avaliacao> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        Optional<Avaliacao> avaliacaoBuscada = repository.findById(id);

        if (avaliacaoBuscada.isPresent()) {
            return ResponseEntity.ok(avaliacaoBuscada.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody @Valid Avaliacao novaAvaliacao) {
        Avaliacao avaliacaoSalva = repository.save(novaAvaliacao);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(avaliacaoSalva.getId())
                .toUri();

        return ResponseEntity.created(location).body(avaliacaoSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizarAvaliacao(@PathVariable Long id,
                                                         @RequestBody @Valid Avaliacao avaliacaoAtualizada) {
        
        Optional<Avaliacao> avaliacaoBuscada = repository.findById(id);

        if (avaliacaoBuscada.isPresent()) {
            Avaliacao avaliacaoExistente = avaliacaoBuscada.get();

            avaliacaoExistente.setAutor(avaliacaoAtualizada.getAutor());
            avaliacaoExistente.setNota(avaliacaoAtualizada.getNota());

            Avaliacao avaliacaoSalva = repository.save(avaliacaoExistente);
            return ResponseEntity.ok(avaliacaoSalva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarAvaliacao(@PathVariable Long id) {
        Optional<Avaliacao> avaliacaoBuscada = repository.findById(id);

        if (avaliacaoBuscada.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}