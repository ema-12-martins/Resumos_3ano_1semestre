import java.io.*;
import java.net.*;
import java.util.concurrent.locks.*;

public class Servidor {
    private static int peso = 0;
    private static int transportadora = 1;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition cond = lock.newCondition();

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(12345);
        while (true) {
            Socket client = socket.accept();

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedWriter buffer_out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                        BufferedReader buffer_in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        int valor = Integer.parseInt(buffer_in.readLine());

                        try {
                            lock.lock();
                            int minhaTransportadora=transportadora;
                            peso += valor;

                            if (peso > 200) {
                                cond.signalAll();
                                transportadora++;
                                peso = 0;
                            } else {
                                cond.await();

                            }
                            buffer_out.write(minhaTransportadora + "\n");
                            buffer_out.flush();
                        } finally {
                            lock.unlock();
                        }
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Erro: " + e);
                    } finally {
                        try {
                            client.shutdownOutput();
                            client.shutdownInput();
                            client.close();
                        } catch (IOException ex) {
                            System.out.println("Erro ao fechar o cliente: " + ex);
                        }
                    }
                }
            });

            thread.start();
        }
    }
}
