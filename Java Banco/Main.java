public class Main {
    public static void main(String[] args) {

        Banco banco = new Banco();
        banco.setValorDebito(100);
        banco.setValorCredito(200);
        banco.setValorPix(300);

        banco.UsuarioDinheiro();
        banco.Pagar();
  
    }
}
