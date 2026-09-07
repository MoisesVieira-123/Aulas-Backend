import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        Usuario user1 = new Usuario();

        while(true){
            System.out.println("\n====================================");
            System.out.println("Bem-vindo ao Banco Digital!");
            System.out.println("Digite o que deseja fazer: ");
            System.out.println("1 - Sacar");
            System.out.println("2 - Trabalhar");
            System.out.println("3 - Sair do sistema\n");


            Scanner scanner = new Scanner(System.in);
            int escolha = scanner.nextInt();

            if(escolha == 1){
                user1.Sacar();
                System.out.println("\nDinheiro no banco: " + user1.getDinheiroNoBanco());
                System.out.println("Dinheiro na mão: " + user1.getDinheiro() + "\n");           
            }
            else if(escolha == 2){
                user1.Trabalhar();
                System.out.println("\nDinheiro no banco: " + user1.getDinheiroNoBanco());
                System.out.println("Dinheiro na mão: " + user1.getDinheiro() + "\n");
            }
            else if(escolha == 3){
                System.out.println("Saindo do sistema...");
                System.out.println("====================================");
                break;
            }
            else{
                System.out.println("Opção inválida, tente novamente.");              
            }
        }
    }   
}