import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(12345);
        Controlador controlador = new Controlador();
        while (true){
            Socket client = socket.accept();
            Thread thread = new Thread(() -> {
                try {
                    BufferedReader buffer_leitura = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    BufferedWriter buffer_escrita = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    String tarefa;
                    int temp;
                    boolean on_off;
                    while ((tarefa = buffer_leitura.readLine()) != null){
                        switch (tarefa){
                            case ("temperatura"):
                                 temp = Integer.parseInt(buffer_leitura.readLine());
                                controlador.temperatura(temp);
                            case ("limiar"):
                                temp = Integer.parseInt(buffer_leitura.readLine());
                                controlador.limiar(temp);
                            case ("on-off"):
                                on_off=Boolean.parseBoolean(buffer_leitura.readLine());
                                controlador.on_off(on_off);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Ocorreu um erro: " + e.getMessage());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            client.shutdownInput();
            client.shutdownOutput();
            client.close();
        }
    }
}

