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
import br.senac.tads.dsw.exemplo.model.Avaliacao;
import br.senac.tads.dsw.exemplo.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/produtos")

public class AvaliacaoController{     
    private final AvaliacaoController repository;

    public AvaliacaoController(AvaliacaoController repository) {
        this.repository = repository;
    }
 @GetMapping
    public List<Avaliacao> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        
        Optional<Avaliacao> AvaliacaoBuscada = repository.findById(id);

        if (AvaliacaoBuscada.isPresent()) {
            return ResponseEntity.ok(AvaliacaoBuscada.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizarAvaliacao(@PathVariable Long id,
                                            @RequestBody @Valid Avaliacao AvaliacaoAtualizada) {
        
        Optional<Avaliacao> AvaliacaoBuscada = repository.findById(id);

        if (AvaliacaoBuscada.isPresent()) {
            Avaliacao produtoExistente = AvaliacaoBuscada.get();

            AvaliacaoExistente.setNome(AvaliacaoAtualizada.getNome());
            AvaliacaoExistente.setPreco(AvaliacaoAtualizada.getPreco());

            Avaliacao AvaliacaoSalva = repository.save(AvaliacaoExistente);
            
            return ResponseEntity.ok(AvaliacaoSalva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarAvaliacao(@PathVariable Long id) {

        Optional<Avaliacao> AvaliacaoBuscada = repository.findById(id);

        if (AvaliacaoBuscada.isPresent()) {
            repository.deleteById(id);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}