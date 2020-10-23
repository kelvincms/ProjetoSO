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

    public Matriz(int tamanho) {
        this.tam = tamanho;
        linhaColuna();
        matriz = new int[this.linha][this.col];
    }
    
    public Matriz(int[][] mat){
        this.matriz = mat;
        this.tam = mat.length;
        linhaColuna();
    }
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

    // Quicksort
    public void ordenaMatriz() {

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
        for (int i = 0; i < this.linha; i++) {
            for (int j = 0; j < this.col; j++) {
                matriz[i][j] = temp[contAux];
                contAux++;
            }
        }
        long tempoFinal = System.currentTimeMillis();
        this.tempoOrd = calcTempo(tempoInicial, tempoFinal);

        //printaMatriz();

    }

    private void linhaColuna() {
        if (sqrt(tam) - Math.floor(sqrt(tam)) == 0) {
            linha = (int) sqrt(tam);
            col = (int) sqrt(tam);
        } else {
            linha = (int) sqrt(tam) + 1;
            col = (int) sqrt(tam) + 1;

        }
        this.tam = linha*col;
    }

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

    private void preencheMatrizSeq() {
        Random gerador = new Random();
        long tempoInicial = System.currentTimeMillis();
        for (int i = 0; i < this.linha; i++) {
            for (int j = 0; j < this.col; j++) {
                matriz[i][j] = gerador.nextInt(100);
                // System.out.print("i " + String.valueOf(i) + " j " + String.valueOf(j));
                //System.out.print(" ");
            }
            //System.out.print("\n");
        }
        long tempoFinal = System.currentTimeMillis();
        this.tempoSeq = calcTempo(tempoInicial, tempoFinal);

    }

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

    private void preencheMatrizCol() {
        Random gerador = new Random();
        long tempoInicial = System.currentTimeMillis();
        for (int i = 0; i < this.col; i++) {
            for (int j = 0; j < this.linha; j++) {
                matriz[i][j] = gerador.nextInt(100);
                //System.out.print("i " + String.valueOf(i) + " j " + String.valueOf(j));
                //System.out.print(" ");
            }
            // System.out.print("\n");
        }
        long tempoFinal = System.currentTimeMillis();
        this.tempoCol = calcTempo(tempoInicial, tempoFinal);
    }

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
                    //System.out.print("i " + String.valueOf(linhaAt) + " j " + String.valueOf(j));
                    // System.out.print(" |||| ");
                } else {
                    long tempoFinal1 = System.currentTimeMillis();
                    this.tempoDiag = calcTempo(tempoInicial1, tempoFinal1);
                    return;
                }

                linhaAt--;
                j--;
            }
            //System.out.println("\n");
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

    private void preencheMatrizRand() {
        Random gerador = new Random();
        TreeMap teste = new TreeMap();
        int preenche = 1;  // Fornece a posição da chave da árvore
        for (int i = 0; i < this.col; i++) {
            for (int j = 0; j < this.linha; j++) {
                PosRand aux = new PosRand(i, j);
                teste.put(preenche, aux);
                preenche++;

            }
        }
        //System.out.println(posicoesLivres.size());
        // System.out.println("Tamanho da arvore: " + teste.size());
        int l = 0;
        long tempoInicial = System.currentTimeMillis();
        while (!teste.isEmpty()) {
            l++;
            int b = gerador.nextInt(teste.size());
            if (teste.containsKey(b) || teste.ceilingKey(b) != null) {
                PosRand aux = (PosRand) teste.get(teste.ceilingKey(b));
                int testi = (int) teste.ceilingKey(b);
                teste.remove(teste.ceilingKey(b));
                matriz[aux.col][aux.linha] = gerador.nextInt(100);
                //   System.out.println("Posição preenchida:" + String.valueOf(aux.linha) +" " + String.valueOf(aux.col));
            }
        }

        long tempoFinal = System.currentTimeMillis();
        this.tempoRand = calcTempo(tempoInicial, tempoFinal);
    }

    private long calcTempo(long tempoIni, long tempoFina) {
        return (tempoFina - tempoIni);
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

// pos inicial de J (colunas) durante a inserção
// var aux = 0; 
// j = 0+aux
// if(aux <= numcol) = aux ++ 
// posição inicial de I (linhas) durante a inserção
// if(jini == col) = iini --  
// inserção em uma diagonal
//(while j != 0) j-- if (linha !=0) linha-- | if(linha-- = <0)   

/*
    // preenche a matriz com numeros aleatórios com um intervalo definido pelo usuário
    private void preencheMatriz(int numAl ){
             for (int i = 0; i < this.linha; i++) {
            for (int j = 0; j < this.col; j++) {
                matriz[i][j] = gerador.nextInt(numAl);
            }
        }
    }
 */
 /*
    @Override
    public String toString() {
        return (Arrays.toString(matriz));
    }
 */
// 300 posições = 10x30 | 100 x 3
/*
     public Matriz(int tamanho,boolean preencherMatriz,int numAl){
        this.tam = tamanho;
        linhaColuna();
        matriz = new int[this.linha][this.col];
        if(preencherMatriz){
           preencheMatriz(numAl);
        }
        
    }
 */
// preencheMatriz usando List
/*while(!posicoesLivres.isEmpty()){
            l++;
           PosRand aux = posicoesLivres.get(gerador.nextInt((int)posicoesLivres.size()));
           posicoesLivres.remove(posicoesLivres.indexOf(aux));
           matriz[aux.col][aux.linha] = gerador.nextInt();
           //System.out.println("i " + String.valueOf(aux.col) + " j " + String.valueOf(aux.linha));
        }
 */
