import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    static int soma_todas_threads=0;
    static int contador_todas_threads=0;
    static ReentrantLock lock=new ReentrantLock();
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(12345);

            while (true) {
                Socket cliente = socket.accept();
                BufferedWriter buffer_escrita = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
                BufferedReader buffer_leitura = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

                Thread thread = new Thread(() -> {
                    try {
                        int soma_numeros = 0;
                        int contador = 0;
                        String numero_string;

                        while ((numero_string = buffer_leitura.readLine()) != null) {
                            int numero = Integer.parseInt(numero_string);
                            soma_numeros += numero;
                            contador += 1;

                            try {
                                lock.lock();
                                soma_todas_threads += numero;
                                contador_todas_threads += 1;
                            } finally {
                                lock.unlock();
                            }

                            buffer_escrita.write((soma_numeros / contador) + "\n");
                            buffer_escrita.flush();
                            System.out.println(soma_todas_threads);
                            System.out.println(contador_todas_threads);
                        }

                        cliente.shutdownInput();
                        buffer_escrita.write((soma_todas_threads / contador_todas_threads) + "\n");
                        buffer_escrita.flush();
                        cliente.shutdownOutput();
                        cliente.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                thread.start();
            }
        } catch (IOException e) {
            System.out.print("Não foi possível criar o socket");
        }
    }
}
