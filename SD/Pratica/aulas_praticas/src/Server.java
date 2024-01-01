
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(12345);

            while(true){
                Socket cliente = socket.accept();
                BufferedWriter buffer_escrita = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
                BufferedReader buffer_leitura = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                int soma_numeros = 0;
                int contador = 0;
                String numero_string;

                while((numero_string = buffer_leitura.readLine()) != null){
                    int numero = Integer.parseInt(numero_string);
                    soma_numeros+=numero;
                    contador+=1;

                    buffer_escrita.write((soma_numeros / contador) + "\n");
                    buffer_escrita.flush();
                }
                cliente.shutdownInput();
                cliente.shutdownOutput();
                cliente.close();
            }
        }catch (IOException e){
            System.out.print("Nao foi possivel criar o socket");
        }

    }
}
