import java.util.Scanner;

public class Banco extends Usuario implements Acoes {

    Usuario usuario = new Usuario();
    Scanner scanner = new Scanner(System.in);
        
    private int valorDebito;
    private int valorCredito;
    private int valorPix;

    public void UsuarioDinheiro() {
        usuario.setDinheiro(1000);
    }


    @Override public void Pagar() {
        System.out.println("Valor no banco: " + usuario.getDinheiro()+ "\n");
        System.out.println("Qual a forma de pagamento? (Debito, Credito ou Pix)");

        String formaPagamento = scanner.nextLine();
        switch (formaPagamento) {
            case "Debito":
                Debito();
                break;
            case "Credito":
                Credito();
                break;
            case "Pix":
                Pix();
                break;
            default:
                System.out.println("Forma de pagamento inválida.");
        }
    }

    @Override public void Debito() {

        if(usuario.getDinheiro() < valorDebito) {

            System.out.println("Saldo insuficiente para pagamento com débito.");
        } else{

            usuario.setDinheiro(usuario.getDinheiro() - valorDebito);
            System.out.println("Pagamento com débito selecionado. \n Valor pago: " + valorDebito + "\n Valor atual: " + usuario.getDinheiro());
        }       
    }

    @Override public void Credito() {

        if(usuario.getDinheiro() < valorCredito) {

            System.out.println("Saldo insuficiente para pagamento com crédito.");
        } else{

            usuario.setDinheiro(usuario.getDinheiro() - valorCredito);
            System.out.println("Pagamento com crédito selecionado. \n Valor pago: " + valorCredito + "\n Valor atual: " + usuario.getDinheiro());
        }
    }

    @Override public void Pix() {

        if(usuario.getDinheiro() < valorPix) {

            System.out.println("Saldo insuficiente para pagamento com Pix.");
        } else {

            usuario.setDinheiro(usuario.getDinheiro() - valorPix);
            System.out.println("Pagamento com Pix selecionado. \n Valor pago: " + valorPix + "\n Valor atual: " + usuario.getDinheiro());
        }
    }





    public int getValorDebito() {
        return valorDebito;
    }
    public void setValorDebito(int valorDebito) {
        this.valorDebito = valorDebito;
    }

    public int getValorCredito() {
        return valorCredito;
    }
    public void setValorCredito(int valorCredito) {
        this.valorCredito = valorCredito;
    }

    public int getValorPix() {
        return valorPix;
    }
    public void setValorPix(int valorPix) {
        this.valorPix = valorPix;
    }
}
