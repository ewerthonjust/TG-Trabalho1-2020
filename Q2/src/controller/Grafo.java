package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

import controller.Processamento;

//@authors Diego Arndt & Ewerthon Ricardo Just
public class Grafo {
	public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/teste4.txt"))) {
            Processamento p = new Processamento();
            String l = br.readLine();
            while (l != null && !l.equals("0")) {
                Integer qtdVert = Integer.parseInt(l);
                Integer qtdArest = 0;
                l = br.readLine();
                int[][] grafo = new int[qtdVert][qtdVert];
                @SuppressWarnings("unchecked")
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
                String result = "Vértices: " + qtdVert + " | Arestas: " + qtdArest + "\nSequência de Graus: " + p.calcularGraus(grafo);
                System.out.println("============================= Questão A. ===============================\n\n" + result + "\n________________________________________________________________________\n\n");
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
                    String resultBuscas = "\r\nVértice de origem: "+ origemBusca +"\nBusca em largura:\r\n" + p.buscarModoLargura(listaAdj, origemBusca, qtdVert)
                            + "\r\nBusca em profundidade:\r\n" + p.buscarModoProfundidade(origemBusca, listaAdj, qtdVert);
                    System.out.println(resultBuscas);               
                    String dir = "src/grafos_infos.txt"; 
                    File f = new File(dir);
                    PrintWriter out = null;
                    if (f.exists() && !f.isDirectory() )
                        out = new PrintWriter(new FileOutputStream(new File(dir), true));
                    else
                        out = new PrintWriter(dir);
                    out.append(result+"\n"+resultBuscas+"\r\n_________________________________\n\n");
                    out.close();
                    System.out.println("\n________________________________________________________________________\n"
                    		+ "\nMemória utilizada: " + p.calcMem() + "Mb\nTempo necessário: " + p.getTime());
                    System.out.println("\n________________________________________________________________________\n"
                            + "\nAs informações deste processamento foram salvas no\nseguinte arquivo: src/grafos_infos.txt\n");
                } else 
                    System.out.println("Opção inválida");
            }
        } catch (Exception ex) {
            if (ex.getClass().equals(NumberFormatException.class))
                System.out.println("O valor digitado deve ser númerico");
        }
    }
}