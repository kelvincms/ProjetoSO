package projetoso;

import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

public class Matriz {

    public final int[][] matriz;
    private int tam;
    private int linha;
    private int col;
    private long tempoSeq;
    private long tempoCol;
    private long tempoDiag;
    private long tempoRand;
    private long tempoOrd;

    // Inicializar matriz vazia com o tamanho, não vai realizar inserção nem nada do gênero
    public Matriz(int tamanho) {
        this.tam = tamanho;
        linhaColuna();
        matriz = new int[this.linha][this.col];
    }
    // Inicializar a matriz recebendo como parâmetro outra matriz
    public Matriz(int[][] mat){
        this.matriz = mat;
        this.tam = mat.length;
        linhaColuna();
    }
    //Inicializar a matriz recebendo como parâmetro um objeto da classe Matriz
    public Matriz(Matriz a){
        this.matriz = a.matriz;
        this.tam = a.getTam();
        this.linha = a.getLinha();
        this.col = a.getCol();
        this.tempoCol = a.getTempoCol();
        this.tempoSeq = a.getTempoSeq();
        this.tempoDiag = a.getTempoDiag();
        this.tempoRand = a.getTempoRand();
        this.tempoOrd = a.getTempoOrd();
        
       
    }

    // Modo 0 - Preenchimento sequencial (linha a linha)
    // Modo 1 - Preenchimento coluna a coluna(Coluna a coluna)
    // Modo 2 - Preenchimento de toda matriz através de diagonais : https://prnt.sc/v43lms
    // Modo 3 (não implementado) - Preenchimento de toda matriz através de espirais : https://prnt.sc/v43n19 
    // Modo 4 - Preenchimento aleatório
    // Modo 5 - Executa todos os acima
    public Matriz(int tamanho, int modo) {
        this.tam = tamanho;
        linhaColuna();
        matriz = new int[this.linha][this.col];
        selecPreenchimento(modo);
    }

    // Quicksort dual pivot (Arrays.Sort)
    public void ordenaMatriz() {
    // Adiciona todos os valores da matriz no vetor para ordenar com (Arrays.Sort)
        long tempoInicial = System.currentTimeMillis();
        int[] temp = new int[(this.col * this.linha)];
        int contAux = 0;
        for (int i = 0; i < this.linha; i++) {
            for (int j = 0; j < this.col; j++) {
                temp[contAux] = matriz[i][j];
                contAux++;
            }
        }
        contAux = 0;
        Arrays.sort(temp);
     //Insere os valores na matriz de forma ordenada
        for (int i = 0; i < this.linha; i++) {
            for (int j = 0; j < this.col; j++) {
                matriz[i][j] = temp[contAux];
                contAux++;
            }
        }
        long tempoFinal = System.currentTimeMillis();
        this.tempoOrd = calcTempo(tempoInicial, tempoFinal);

    }

    private void linhaColuna() {
        // Caso sqrt = raiz perfeita, caso contrário col e linha +1
        if (sqrt(tam) - Math.floor(sqrt(tam)) == 0) {
            linha = (int) sqrt(tam);
            col = (int) sqrt(tam);
        } else {
            linha = (int) sqrt(tam) + 1;
            col = (int) sqrt(tam) + 1;

        }
        this.tam = linha*col;
    }

    // Seleciona alguma das formas para preencher uma matriz
    private void selecPreenchimento(int modo) {
        switch (modo) {
            case 0:
                preencheMatrizSeq();
                break;
            case 1:
                preencheMatrizCol();
                break;
            case 2:
                preencheMatrizDiag();
                break;
            case 4:
                preencheMatrizRand();
                break;
            case 5:
                preencheMatrizDiag();
                preencheMatrizRand();
                preencheMatrizCol();
                preencheMatrizSeq();
                break;
            default:
                break;
        }

    }
    //Preenche a matriz de forma sequencial
    private void preencheMatrizSeq() {
        Random gerador = new Random();
        long tempoInicial = System.currentTimeMillis();
        for (int i = 0; i < this.linha; i++) {
            for (int j = 0; j < this.col; j++) {
                matriz[i][j] = gerador.nextInt(100);
            }
        }
        long tempoFinal = System.currentTimeMillis();
        this.tempoSeq = calcTempo(tempoInicial, tempoFinal);

    }
   //Preenche matriz a da forma: coluna a coluna
    private void preencheMatrizCol() {
        Random gerador = new Random();
        long tempoInicial = System.currentTimeMillis();
        for (int i = 0; i < this.col; i++) {
            for (int j = 0; j < this.linha; j++) {
                matriz[i][j] = gerador.nextInt(100);
            }
        }
        long tempoFinal = System.currentTimeMillis();
        this.tempoCol = calcTempo(tempoInicial, tempoFinal);
    }
   //Preenche matriz a partir das diagonais(esquerda para direita)
    private void preencheMatrizDiag() {
        long tempoInicial1 = System.currentTimeMillis();
        Random gerador = new Random();
        int aux = 0;
        int linhaIni = this.linha - 1;
        int linhaAt;
        // j = aux por que j é utilizado no while abaixo
        for (int j = aux; j != this.col - 1 || linhaIni >= 0;) {
            linhaAt = linhaIni;
            // preenchimento
            while (j >= 0 && linhaAt >= 0) {
                if (matriz[linhaAt][j] == 0) {
                    matriz[linhaAt][j] = gerador.nextInt(100);
                 } else {
                    long tempoFinal1 = System.currentTimeMillis();
                    this.tempoDiag = calcTempo(tempoInicial1, tempoFinal1);
                    return;
                }
                linhaAt--;
                j--;
            }
            if (aux != this.col - 1) {
                aux++;
            } else {
                if (linhaIni != 0) {
                    linhaIni--;
                }
            }
            j = aux;
    }
    }
     // Preenche a matriz em posições aleatórias
    private void preencheMatrizRand() {
        Random gerador = new Random();
        TreeMap Arvore = new TreeMap();
        int preenche = 1;  // preenche = Fornece a posição da chave da árvore
        // Adiciona na árvore todas as posições da matriz "Vazias", assim como adiciona uma respectiva chave
        for (int i = 0; i < this.col; i++) {
            for (int j = 0; j < this.linha; j++) {
                PosRand aux = new PosRand(i, j);
                Arvore.put(preenche, aux);
                preenche++;

            }
        }
        long tempoInicial = System.currentTimeMillis();
        // Enquanto a árvore não estiver vazia, gerar uma chave qualquer, caso resulte em um numero "inexato" de chave, utilizar a variação mais próxima
        while (!Arvore.isEmpty()) {
            int b = gerador.nextInt(Arvore.size());
            if (Arvore.containsKey(b) || Arvore.ceilingKey(b) != null) {
                PosRand aux = (PosRand) Arvore.get(Arvore.ceilingKey(b));
                Arvore.remove(Arvore.ceilingKey(b));
                matriz[aux.col][aux.linha] = gerador.nextInt(100);
            }
        }
        long tempoFinal = System.currentTimeMillis();
        this.tempoRand = calcTempo(tempoInicial, tempoFinal);
    }
    // Devolve a String do "printf" da Matriz
     private String printaMatriz() {
        String matrizString = "";
        for (int i = 0; i < this.linha; i++) {
            for (int j = 0; j < this.col; j++) {
                matrizString += ("Posição " + "[" + String.valueOf(i) + "]" + "[" + String.valueOf(j) + "]" + "=" + String.valueOf(matriz[i][j]) + "  ");
            }
            matrizString += ("\n");
        }
        return matrizString;
    }
    
     //Função auxiliar, apenas calcula o tempo, dado tempo inicial e final
    private long calcTempo(long tempoInicial, long tempoFinal) {
        return (tempoFinal - tempoInicial);
    }
    
    public long getTempoSeq() {
        return tempoSeq;
    }

    public long getTempoCol() {
        return tempoCol;
    }

    public long getTempoDiag() {
        return tempoDiag;
    }

    public long getTempoRand() {
        return tempoRand;
    }

    public long getTempoOrd() {
        return tempoOrd;
    }

    public int getTam() {
        return tam;
    }

    public int getLinha() {
        return linha;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString(){
        return "Linha(s):" + getLinha() + "\n" + "Coluna(s):" + getCol() + "\n" + "Tempo Seq: " + getTempoSeq() + "Ms" + "\n" + "Tempo Col: " + getTempoCol() + "Ms" + "\n" + "Tempo Diag: " + getTempoDiag() + "Ms" + "\n" + "Tempo Rand: " + getTempoRand() +"Ms"+ "\n" + "Tempo para organizar: " + getTempoOrd()+ "Ms" + "\n" + printaMatriz();
    }
}
