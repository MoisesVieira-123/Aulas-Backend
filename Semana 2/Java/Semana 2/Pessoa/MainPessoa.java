public class MainPessoa {
    public static void main(String[] args) {

        Person pessoa1 = new Person(21, "Moises", 1.9);
        Person pessoa2 = new Person(19, "Emanuela", 1.6);
        Person pessoa3 = new Person(26, "Alexandre", 1.8);
        
        if(pessoa1.Altura > pessoa2.Altura && pessoa1.Altura > pessoa3.Altura){
            System.out.println(pessoa1.Nome + " é mais alto(a)");
        }
        else if(pessoa2.Altura > pessoa1.Altura && pessoa2.Altura > pessoa3.Altura){
            System.out.println(pessoa2.Nome + " é mais alto(a)");
        }
        else{
            System.out.println(pessoa3.Nome + " é mais alto(a)");
        }
    }
}
