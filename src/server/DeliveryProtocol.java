package server;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;

import data.Delivery;
import data.FinalReport;
import data.Singleton;

public class DeliveryProtocol {
    private static final int INICIO = 0;
    private static final int MENU = 1;
    private static final int ITEM_ESCOLHIDO = 2;
    private static final int SABORPIZZA = 3;
    private static final int SQUANTIDADE = 4;
    private static final int RESUMO = 5;

    private int state = INICIO;

    private String[] pizzas =  {"calabresa", "frango catupiri", "portuguesa", "carne do sol", "camarao"};
    private String[] tamanhos = {"pequeno", "medio", "grande"};
    Delivery pedido = new Delivery();
    public FinalReport FR;


    public String processInput(String theInput, Socket clientSocket) throws FileNotFoundException {

        if(clientSocket != null){
            FR = Singleton.getInstance(clientSocket.getPort());
        }
        String theOutput = null;

        if (state == INICIO) {
            theOutput = "Bem vindo ao IC Delivery! Digite <Menu> para mostrar os produtos disponiveis.";

            state = MENU;
        } else if (state == MENU) {
            if (theInput.equalsIgnoreCase("Menu")) {
                theOutput = "Digite um sabor: " + Arrays.toString(pizzas);
                state = ITEM_ESCOLHIDO;
            } else {
                theOutput = "Entrada invalida, retornando ao inicio. Digite \"Menu\"!";
            }
            if (theInput.equalsIgnoreCase("finalizar")){
                theOutput = "Pedido Finalizado. Obrigado pela preferencia!";

                int id = clientSocket.getPort();
                String file_name = id + "nota_fiscal_server.txt";

                FR.addTotal("\nTotal: R$" + pedido.getTotal(), id);
                try (PrintStream out = new PrintStream(new FileOutputStream(file_name))) {
                    out.print(FR.returnReport(id));
                }
                state = INICIO;
            }
           

        } else if (state == ITEM_ESCOLHIDO) {
  
        	boolean verify = Arrays.asList(pizzas).contains(theInput.toLowerCase());
            if (verify) {
                pedido.setSabor(theInput);
                theOutput = "Escolha um tamanho: " + Arrays.toString(tamanhos);
                state = SABORPIZZA;
            } else {
                theOutput = "Entrada invalida, retornando ao inicio. Digite \"Menu\"!";
                state = MENU;
            }
        } else if (state == SABORPIZZA) {
        	boolean verify = Arrays.asList(tamanhos).contains(theInput.toLowerCase());
            if (verify) {
                pedido.resetPrecoParcial(pedido.getSabor());
                pedido.setTipo(theInput.toLowerCase());
                theOutput = "pizza de " + pedido.getSabor() + ", tamanho "+  pedido.getTipo() + " selecionada! Qual a quantidade? (Digite um numero)";
                state = SQUANTIDADE;
            } else {
                theOutput = "Entrada invalida, retornando ao inicio. Digite \"Menu\"!";
                state = INICIO;
            }
        }
        else if (state == SQUANTIDADE){
            if (Integer.parseInt(theInput) >= 1){
                    int id = clientSocket.getPort();

                    theOutput = theInput + " pizza(s) de "+ pedido.getSabor() + " de tamanho " + pedido.getTipo() + " selecionada(s)!. Digite  \"resumo\" para ver o que voce pediu e finalizar a comprar ou continuar comprando";

                    pedido.setQuantidade(Integer.parseInt(theInput), pedido.getSabor());
                    FR.add(pedido.toString(pedido.getSabor()), id); //adiciona pedido ao report final ou nota fiscal
                    String file_name = id + "nota_fiscal_server.txt";

                    try (PrintStream out = new PrintStream(new FileOutputStream(file_name))) {
                        System.out.println("Manipulando arquivo de nota fiscal no servidor....");
                        out.print(FR.returnReport(id));
                    }

                    state =  RESUMO;
            }


        }
        else if (state == RESUMO){
            if (theInput.equalsIgnoreCase("resumo")) {
                theOutput = pedido.toString(pedido.getSabor()) + " Deseja finalizar a compra? (\"finalizar\" para finalizar ou  \"menu\" caso contrario)";
                state = MENU;
            }else{
                theOutput = "Entrada invalida, retornando ao inicio. Digite \"Menu\"!";
                state = MENU;
            }
        }

        return theOutput;
    }

    public String readFile(String str){
        String data = "Aqui esta um resumo do seu pedido:\n";
        try {
            File myObj = new File(str);
            Scanner myReader = new Scanner(myObj); 
            while (myReader.hasNextLine()) {
              data = data + myReader.nextLine();
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          } 
          return data;

    }
}
/*Proximos passos
    Dar um preço para cada item e mostrar num menu após selecionar o item
    Guardar itens selecionados num array ou qualquer outra estrutura. Cada tipo de item terá um array diferente.
    Criar uma classe nova para itens
    Imprimir uma "Nota fiscal" - não estou conseguindo mandar a string com quebra de linha, é algo na implementação que eu peguei aqui (eu acho)
    + ideias que nao pensei na hora
    Falta tambem dividir o output pra cada caso do item escolhido*/ 