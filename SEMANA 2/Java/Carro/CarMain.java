public class CarMain {
    public static void main(String[] args) {

        Car carro1 = new Car(2021, "WolksVagen", "Preto");
        Car carro2 = new Car(2023, "Toyota", "Vermelho");

        carro1.Ano = 1998;

        System.out.println(carro1.Ano + ", " + carro1.Marca + ", " + carro1.Cor);
        System.out.println(carro2.Ano + ", " + carro2.Marca + ", " + carro2.Cor);
    }
}
