
package moises.teste.service;

import moises.teste.dto.AlunoRequest;
import moises.teste.entity.Aluno;
import moises.teste.repository.AlunoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void update(Long id, AlunoRequest request) {

        Aluno aluno = repository.findById(id)
                .orElseThrow();

        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setIdade(request.getIdade());

        repository.save(aluno);
    }

    @Transactional
    public void deleteById(Long id) {

        boolean existe = repository.existsById(id);

        if (!existe) {
            throw new NoSuchElementException(
                    "Aluno não encontrado com ID: " + id
            );
        }

        repository.deleteById(id);
    }
}

