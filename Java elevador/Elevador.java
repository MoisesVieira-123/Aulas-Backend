import java.util.Scanner;

public class Elevador extends RestricoesElevador implements AcoesElevador {

    RestricoesElevador restricoesElevador = new RestricoesElevador();
    
     public void QuantidadePessoas() {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a quantidade de pessoas no elevador: ");
        int quantidadePessoas = scanner.nextInt();
        restricoesElevador.setQuantidadePessoas(quantidadePessoas);  
    }

    @Override 
    public void Subir() {

        restricoesElevador.setCapacidadeMaxima(5);

        if(restricoesElevador.getQuantidadePessoas() < restricoesElevador.getCapacidadeMaxima()) {

            System.out.println("O elevador subiu um andar e agora está no andar: " + (restricoesElevador.getAndarAtual() + 1));           
        
        } else {
            System.out.println("O elevador não pode subir, pois a quantidade de pessoas excede a capacidade máxima.");
        }
    }
}
