
package moises.teste.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoRequest {

    private String nome;
    private String email;
    private Integer idade;
}

