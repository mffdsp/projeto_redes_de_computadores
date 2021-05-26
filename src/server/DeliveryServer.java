package server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DeliveryServer {

    public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
            System.err.println("Usage: java DeliveryServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        System.out.println("Servidor Iniciado...\nesperando conexao na porta " + portNumber + "...");
       
        try ( 
                
                ServerSocket serverSocket = new ServerSocket(portNumber);   
                
        ) {
        
            while(true){
                
                Socket clientSocket = serverSocket.accept();
                Thread t = new Thread(new DeliveryThread(clientSocket));
                System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " conectado!");
                t.start();
            }
           
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}