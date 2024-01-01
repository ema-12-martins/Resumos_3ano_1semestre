import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedWriter buffer_escrita = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader buffer_leitura = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            String numero_string;
            System.out.println("Digite um número: ");

            while (scanner.hasNextInt()) {
                numero_string = scanner.nextLine();

                buffer_escrita.write(numero_string+"\n"); // Temos de passar para String
                buffer_escrita.flush();

                String media_dos_numeros = buffer_leitura.readLine();
                System.out.println("Média dos números recebidos do servidor: " + media_dos_numeros);
                System.out.println("Digite um número: ");
            }

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
            scanner.close();
        } catch (IOException e) {
            System.out.println("Não foi possível criar o socket: " + e.getMessage());
        }
    }
}


