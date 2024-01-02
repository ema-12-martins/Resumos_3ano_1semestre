import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        BufferedWriter buffer_out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader buffer_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o peso da encomenda: ");
        int valor = scanner.nextInt();
        buffer_out.write(Integer.toString(valor) + "\n");
        buffer_out.flush();

        String transportadora = buffer_in.readLine();
        System.out.println("Transportadora: "+transportadora);

        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();
    }
}

