package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

//@authors Diego Arndt & Ewerthon Ricardo Just
public class Grafo {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/java/teste3.txt"))) {
            Processamento p = new Processamento();
            String l = br.readLine();
            while (l != null && !l.equals("0")) {
                Integer qtdVert = Integer.parseInt(l);
                Integer qtdArest = 0;
                l = br.readLine();
                int[][] grafo = new int[qtdVert][qtdVert];
                LinkedList<Integer>[] listaAdj = new LinkedList[qtdVert];
                for (int i = 0; i < grafo.length; i++)
                    grafo[i][i] = 0;
                for (int i = 0; i < grafo.length; i++) {
                    for (int j = 0; j < grafo.length - i; j++)
                        grafo[j][i + j] = 0;
                }
                while (l != null && l.length() != 1) {
                    qtdArest++;
                    String[] vertTemp = l.split(" ");
                    if (vertTemp.length == 2) {
                        grafo[Integer.parseInt(vertTemp[0]) - 1][Integer.parseInt(vertTemp[1]) - 1] = 1;
                        grafo[Integer.parseInt(vertTemp[1]) - 1][Integer.parseInt(vertTemp[0]) - 1] = 1;
                    }
                    if (listaAdj[Integer.parseInt(vertTemp[0]) - 1] == null)
                        listaAdj[Integer.parseInt(vertTemp[0]) - 1] = new LinkedList<>();
                    if (listaAdj[Integer.parseInt(vertTemp[1]) - 1] == null)
                        listaAdj[Integer.parseInt(vertTemp[1]) - 1] = new LinkedList<>();
                    listaAdj[Integer.parseInt(vertTemp[0]) - 1].add(Integer.parseInt(vertTemp[1]));
                    listaAdj[Integer.parseInt(vertTemp[1]) - 1].add(Integer.parseInt(vertTemp[0]));
                    l = br.readLine();
                }
                System.out.println("============================= Questão A. ===============================\n\nVértices: " + qtdVert + " | Arestas: " + qtdArest 
                        + "\nSequência de Graus: " + p.calcularGraus(grafo) + "\n________________________________________________________________________\n\n");
                System.out.println("============================= Questão B. ===============================\n\nQual representação você deseja utilizar?"
                        + "\n(Digite 1 para matriz de adjacência ou digite 2 para lista de adjacência) ");
                int origemBusca;
                try (Scanner s = new Scanner(System.in)) {
                    String e = s.nextLine();
                    System.out.println("________________________________________________________________________\n");
                    switch (e) {
                        case "1":
                            p.processarMatriz(grafo);
                            break;
                        case "2":
                            p.processarLista(listaAdj);
                            break;
                        default:
                            System.out.println("Opção inválida");
                            break;
                    }
                    System.out.println("________________________________________________________________________"
                            + "\n\nBusca em largura e profundidade...\n\nQual vértice você deseja processar?");
                    String o = s.nextLine();
                    System.out.println("________________________________________________________________________\n");
                    origemBusca = Integer.parseInt(o);
                }
                if (origemBusca > 0 && origemBusca < qtdVert + 1) {
                    String resultadoBuscas = "Busca em largura:\r\n" + p.buscarModoLargura(listaAdj, origemBusca, qtdVert)
                            + "\r\nBusca em profundidade:\r\n" + p.buscarModoProfundidade(origemBusca, listaAdj, qtdVert);
                    System.out.println(resultadoBuscas);
                    try (PrintWriter out = new PrintWriter("src/test/java/grafos_infos.txt")) {
                        out.println(resultadoBuscas);
                        System.out.println("\n________________________________________________________________________\n"
                                + "As informações deste processamento foram salvas no\nseguinte arquivo: src/test/java/grafos_infos.txt\n");
                    }
                } else 
                    System.out.println("Opção inválida");
            }
        } catch (Exception ex) {
            if (ex.getClass().equals(NumberFormatException.class))
                System.out.println("O valor digitado deve ser númerico");
        }
    }
}