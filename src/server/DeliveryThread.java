package server;
import java.io.*;
import java.net.*;

public class DeliveryThread implements Runnable{
    private Socket clientSocket;

    public DeliveryThread(Socket s){
        this.clientSocket = s;
    }

    public void run(){

        String inputLine, outputLine;
        try {

            PrintWriter out =
            new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));
            DeliveryProtocol dp = new DeliveryProtocol();
           
            outputLine = dp.processInput(null, null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = dp.processInput(inputLine, clientSocket);

                System.out.println("Comando de entrada de " + clientSocket.getInetAddress() + ":" +  clientSocket.getPort() + inputLine);
                System.out.println("Resposta: " + outputLine);
                out.println(outputLine);

                if (outputLine.equals("Pedido Finalizado. Obrigado pela preferencia!")){
                    sendFile(clientSocket.getPort() + "nota_fiscal_server.txt");
                    break;
                }
                    
        }
           
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    public void sendFile(String filename) throws IOException{
        
        BufferedInputStream bis = null;
        DataOutputStream dout = null;
        try{
            System.out.println("Sending File: " + filename);
        
            File f = new File(filename);
            FileInputStream fin = new FileInputStream(f);
    
            byte b[] = new byte [(int)f.length()];
            bis = new BufferedInputStream(fin);        
            bis.read(b,0,b.length);
    
            dout = new DataOutputStream(clientSocket.getOutputStream());  
    
            dout.write(b,0,b.length);
            dout.flush();
                    
            System.out.println("Send Complete");
            System.out.println(clientSocket.getInetAddress() +  ":" +  clientSocket.getPort() + " Desconectado!");
        }
        finally {
            if (bis != null) bis.close();
            if (dout != null) dout.close();
          }
        
    }
}
