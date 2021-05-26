package client;
import java.io.*;
import java.net.*;

public class DeliveryClient {

    public static void main(String[] args) throws IOException, InterruptedException {
    	
        if (args.length != 2) {
            System.err.println(
                "Usage: java DeliveryClient <host name> (ex: localhost) <port number> (ex: 4444)");
            System.exit(1);
        }

        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
        System.out.println("Conectado com sucesso ao servidor!");

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));

            
            String fromServer;
            String fromUser;
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Pedido Finalizado. Obrigado pela preferencia!")) {   
                	System.out.println("\nA nota fiscal com os detalhes de seu pedido está salvo no arquivo: " +  kkSocket.getLocalPort() + "nota_fiscal_.txt");
                    receiveFile(kkSocket.getLocalPort() + "nota_fiscal_server.txt",  kkSocket.getLocalPort()  , kkSocket);
                    
                    break;
                }
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
    public static void receiveFile(String filename, int clientPort, Socket kkSocket) throws IOException{
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;

        try{

            DataInputStream din = new DataInputStream(kkSocket.getInputStream());  
            DataOutputStream dout = new DataOutputStream(kkSocket.getOutputStream());  

            System.out.println("Recebendo o arquivo do servidor: " + filename);

            int FILE_SIZE_ = (int) (new File(filename)).length();

            if(FILE_SIZE_ == 0){
                FILE_SIZE_ = 1024 * 10;
            }
            filename = clientPort + "nota_fiscal_client.txt";

            byte [] mybytearray  = new byte [FILE_SIZE_];
            fos = new FileOutputStream(filename);
            bos = new BufferedOutputStream(fos);
            //current = bytesRead;

            System.out.println("Transferindo dados...");
            long bytesRead;
            do{
                bytesRead = din.read(mybytearray, 0, mybytearray.length);
                fos.write(mybytearray,0,mybytearray.length);
            }while(!(bytesRead<1024));

            System.out.println("Salvando arquivo como: "+ filename);
            System.out.println("Transferido com sucesso!\nDesconectado do servidor!");

        }  finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
          }

    }
}