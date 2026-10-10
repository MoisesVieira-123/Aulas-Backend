
package moises.teste.controller;

import moises.teste.dto.AlunoRequest;
import moises.teste.dto.AlunoResponse;
import moises.teste.entity.Aluno;
import moises.teste.repository.AlunoRepository;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    // POST: criar aluno
    @PostMapping
    public ResponseEntity<AlunoResponse> create(
            @RequestBody AlunoRequest request) {

        Aluno aluno = new Aluno();

        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setIdade(request.getIdade());

        Aluno salvo = repository.save(aluno);

        AlunoResponse response = new AlunoResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getIdade()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET: buscar aluno por ID
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> getById(
            @PathVariable Long id) {

        Optional<Aluno> resultado = repository.findById(id);

        if (resultado.isPresent()) {

            Aluno aluno = resultado.get();

            AlunoResponse response = new AlunoResponse(
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getEmail(),
                    aluno.getIdade()
            );

            return ResponseEntity.ok(response);

        } else {

            return ResponseEntity.notFound().build();
        }
    }

    // PUT: atualizar aluno
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody AlunoRequest request) {

        Optional<Aluno> resultado = repository.findById(id);

        if (resultado.isPresent()) {

            Aluno aluno = resultado.get();

            aluno.setNome(request.getNome());
            aluno.setEmail(request.getEmail());
            aluno.setIdade(request.getIdade());

            repository.save(aluno);

            return ResponseEntity.noContent().build();

        } else {

            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: excluir aluno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        boolean existe = repository.existsById(id);

        if (existe) {

            repository.deleteById(id);

            return ResponseEntity.noContent().build();

        } else {

            return ResponseEntity.notFound().build();
        }
    }
}

