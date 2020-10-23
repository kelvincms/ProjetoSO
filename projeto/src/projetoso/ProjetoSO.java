package projetoso;


public class ProjetoSO {
    public static void main(String[] args) {
    long tempoIni = System.currentTimeMillis();
    Matriz a = new Matriz(10000,5);
    Matriz b = new Matriz(a);
    b.ordenaMatriz();
    long tempoFinal = System.currentTimeMillis();
    System.out.println(b.toString());
    System.out.println("Tempo final: " + String.valueOf(tempoFinal - tempoIni) + "Ms");
    }
    
}
