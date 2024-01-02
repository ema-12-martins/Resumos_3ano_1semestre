import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(Integer.parseInt(args[1]));
        List<Presenca> presencas = new ArrayList<Presenca>();
        ReentrantLock lock = new ReentrantLock();

        while (true){
            Socket client = socket.accept();
            Thread thread= new Thread(()->{
                try {
                    BufferedReader buffer_in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    BufferedWriter buffer_out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                    String nova_entrada=buffer_in.readLine();
                    Presenca presenca= new Presenca(nova_entrada, args[0], )

                }catch (IOException e){
                    System.out.println("Erro"+e);
                }


            });
            thread.start();
        }
    }
}