package Controller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Diego Arndt & Ewerthon Ricardo Just
 */
public class Processamento {
    protected String calcularGraus(int[][] matriz) {
        boolean eDigrafo = eDigrafo(matriz);
        StringBuilder res = new StringBuilder();
        if (eDigrafo) {
            int[] grausOut = new int[matriz.length];
            int[] grausIn = new int[matriz.length];
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz.length; j++) {
                    grausOut[i] += matriz[i][j];
                    grausIn[j] += matriz[i][j];
                }
            }
        } else {
            int[] seqGraus = new int[matriz.length];
            for (int i = 0; i < matriz.length; i++) {
                int grauV = 0;
                for (int j = 0; j < matriz.length; j++) {
                    if (matriz[i][j] > 0) {
                        if (i == j)
                            grauV += matriz[i][j] * 2;
                        else
                            grauV += matriz[i][j];
                    }
                }
                seqGraus[i] = grauV;
            }
            seqGraus = ordenarModoBolha(seqGraus, false);
            for (int i = 0; i < seqGraus.length; i++) {
                if (i > 0)
                    res.append(", ");
                res.append(seqGraus[i]);
            }
        }
        return res.toString();
    }
    
    protected String buscarModoProfundidade(Integer origem, LinkedList<Integer>[] listAdj, Integer qtdVert) {
        Stack pilha = new Stack();
        int[] pais = new int[qtdVert];
        pilha.push(origem);
        Cor[] cores = new Cor[qtdVert];
        while (!pilha.isEmpty()) {
            int v = Integer.parseInt(pilha.pop().toString());
            visitarNos(v, cores, listAdj, pais, pilha);
        }
        String l1 = "   | ", l2 = "PI | ", esp = "";
        for (int i = 0; i <= qtdVert.toString().length(); i++)
            esp += " ";
        for (int i = 0; i < qtdVert; i++) {
            l1 += (i + 1) + esp + "|" + esp;
            l2 += pais[i] + esp + "|" + esp;
        }
        return l1 + "\r\n" + (l2.replace("0", "-"));
    }
    
    protected String buscarModoLargura(LinkedList<Integer>[] listAdj, Integer origem, Integer qtdVert) {
        int[] pais = new int[qtdVert];
        int[] dist = new int[qtdVert];
        Cor[] cores = new Cor[qtdVert];
        Queue<Integer> fila = new LinkedList<>();
        fila.add(origem);
        dist[origem - 1] = 0;
        cores[origem - 1] = Cor.CINZA;
        while (!fila.isEmpty()) {
            origem = fila.remove();
            LinkedList<Integer> adjs = listAdj[origem - 1];
            if (adjs != null) {
                for (int i = 0; i < adjs.size(); i++) {
                    Integer adj = adjs.get(i);
                    if (cores[adj - 1] == null) {
                        fila.add(adj);
                        pais[adj - 1] = origem;
                        dist[adj - 1] = dist[origem - 1] + 1;
                        cores[adj - 1] = Cor.CINZA;
                    }
                }
            }
            cores[origem - 1] = Cor.PRETO;
        }
        String esp = "", l1 = "   | ", l2 = "D  | ", l3 = "PI | ";
        for (int i = 0; i <= qtdVert.toString().length(); i++)
            esp += " ";
        for (int i = 0; i < qtdVert; i++) {
            l1 += (i + 1) + esp + "|" + esp;
            l2 += dist[i] + esp + "|" + esp;
            l3 += pais[i] + esp + "|" + esp;
        }
        return l1 + "\r\n" + l2 + "\r\n" + (l3.replace("0", "-"));
    }
    
    protected void processarMatriz(int[][] grafo) {
        System.out.println("Matriz de adjacência");
        for (int[] grafo1 : grafo) {
            for (int j = 0; j < grafo1.length; j++) {
                System.out.print(grafo1[j] + " | ");
            }
            System.out.println("");
        }
    }

    protected void processarLista(LinkedList<Integer>[] listAdj) {
        System.out.println("Lista de adjacência");
        for (int i = 0; i < listAdj.length; i++) {
            if (listAdj[i] != null) {
                System.out.print((i + 1) + "->"
                        + listAdj[i].toString().
                                replaceAll(",", "->").
                                replace("[", "").
                                replace("]", "").
                                replaceAll(" ", ""));
            } else
                System.out.println((i + 1) + "->");
            System.out.println("");
        }
    }
    
    private boolean eDigrafo(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (m[i][j] != m[j][i])
                    return true;
            }
        }
        return false;
    }

    private void visitarNos(Integer v, Cor[] cores, LinkedList<Integer>[] listAdj, int[] pais, Stack pilha) {
        if (cores[v - 1] == null || cores[v - 1] == Cor.BRANCO) {
            cores[v - 1] = Cor.CINZA;
            int[] adjs = new int[listAdj[v - 1].size()];
            for (int i = 0; i < listAdj[v - 1].size(); i++)
                adjs[i] = listAdj[v - 1].get(i);
            adjs = ordenarModoBolha(adjs, false);
            if (adjs != null && adjs.length > 0) {
                for (int i = 0; i < adjs.length; i++) {
                    if (cores[adjs[i] - 1] == null) {
                        pais[adjs[i] - 1] = v;
                        pilha.push(adjs[i]);
                        visitarNos(adjs[i], cores, listAdj, pais, pilha);
                        cores[adjs[i] - 1] = Cor.BRANCO;
                    }
                }
            }
        }
        if (cores[v - 1] == Cor.CINZA)
            cores[v - 1] = Cor.PRETO;
    }

    private int[] ordenarModoBolha(int[] arr, Boolean c) {
        int aux;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (!c) {
                    if (arr[j] > arr[j + 1]) {
                        aux = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = aux;
                    }
                } else {
                    if (arr[j] < arr[j + 1]) {
                        aux = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = aux;
                    }
                }
            }
        }
        return arr;
    }
    
    private enum Cor {
        CINZA, PRETO, BRANCO;
    }
}