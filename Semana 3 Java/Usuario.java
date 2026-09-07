public class Usuario extends Banco implements AcoesUsuario {

    @Override
    public void Sacar() {
        if(getDinheiroNoBanco() < 50){
            System.out.println("\n---Saldo insuficiente no banco para sacar 50 reais.---\n");
            System.out.println("====================================");
        }else{
            System.out.println("\n---Sacando 50 reais do banco...---\n");
            System.out.println("====================================");
            setDinheiro(getDinheiro() + 50);
            setDinheiroNoBanco(getDinheiroNoBanco() - 50);       
        }      
    }

    @Override 
    public void Trabalhar() {
        System.out.println("\n---Trabalhando...---\n");
        System.out.println("====================================");
        setDinheiroNoBanco(getDinheiroNoBanco() + 200);       
    }
}