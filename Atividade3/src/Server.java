import java.net.*;
import java.io.*;

public class Server{

  public static void main(String[] args) throws Exception{
    int number = 80;
    ServerSocket serverSocket = new ServerSocket(4444);
    while(true){
      System.out.println("Server running and waiting connection...");
      Socket clientSocket = serverSocket.accept();

      System.out.println("Getting connection from "+clientSocket.getInetAddress()+", port="+clientSocket.getPort()+", localPort="+clientSocket.getLocalPort());

      (new Thread(new ServerClient(clientSocket, number))).start();
    }
  }

  private static class ServerClient implements Runnable{
    private Socket socket;
    private int num;
    public ServerClient(Socket clientSocket, int num) {
      this.num = num;
      this.socket = clientSocket;
    }
    public void run(){
      try{
        int min = 0;
        int max = 100;
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String line;
        int tentativas=0;

        line=in.readLine();
        while(!line.equals("tchau")){
           System.out.println(line);
           int n =  Integer.parseInt(line);
           if(n>=min && n<=max){
             tentativas++;
             if(n==num){
               out.write("Parabéns, você acertou o número escolhido em "+tentativas+" tentativas.");
             }else{
               if(num>=min && num<=n){
                 max = n-1;
               }else{
                 min = n+1;
               }
               out.write("O número está entre ["+min+","+max+"]");
             }
           }else{
             out.write("Número enviado está fora do intervalo ["+min+","+max+"]!");
           }
           out.newLine();
           out.flush();
           line = in.readLine();
        }
        System.out.print("Finishing connection with client "+
        socket.getInetAddress() + ", port=" + socket.getPort() +
        ", localPort="+socket.getLocalPort()+"...");
        out.close();
        in.close();
        socket.close();
        System.out.println("Finished!");
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }
}
