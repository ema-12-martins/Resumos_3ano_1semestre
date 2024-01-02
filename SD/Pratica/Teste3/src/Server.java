import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    int[][] submarinos;
    Map<String, Integer> jogadores;
    int nrAcabados;
    ReentrantLock lock_matriz;
    ReentrantLock lock_mapa_utilizadores;
    ReentrantLock lock;
    Condition cond;

    public Server(int m, int n, Map<String, Integer> jogadores) {
        this.submarinos = iniciar(m, n);
        this.jogadores = jogadores;
        this.nrAcabados = 0;
        this.lock_matriz = new ReentrantLock();
        this.lock_mapa_utilizadores = new ReentrantLock();
        this.lock = new ReentrantLock();
        this.cond = lock.newCondition();
    }

    public int[][] iniciar(int m, int n) {
        int[][] matriz = new int[m][n];
        Random random = new Random();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = random.nextInt(2); // Generates 0 or 1 randomly
            }
        }

        return matriz;
    }

    public void atualiza_pontos(String nome, int m, int n) {
        int j = n - 4;
        int i = n + 4;
        int pontuacao = 0;

        lock_matriz.lock();
        try {
            for (; j <= i; j++) {
                if (submarinos[m][j] == 1) {
                    pontuacao++;
                    submarinos[m][j] = 0;
                }
            }
        } finally {
            lock_matriz.unlock();
        }

        lock_mapa_utilizadores.lock();
        try {
            int pontuacao_atual = jogadores.get(nome);
            pontuacao_atual += pontuacao;
            jogadores.put(nome, pontuacao_atual);
        } finally {
            lock_mapa_utilizadores.unlock();
        }
    }
    public void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(12345);

            while (true) {
                Socket client = socket.accept();
                Thread thread = new Thread(() -> {
                    try {
                        int conta_disparos = 0;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                        String jogada;
                        while (conta_disparos < 3 && (jogada = bufferedReader.readLine()) != null) {
                            String[] jogada_separada = jogada.split(" ");
                            atualiza_pontos(jogada_separada[0], Integer.parseInt(jogada_separada[1]), Integer.parseInt(jogada_separada[2]));
                            conta_disparos++;
                        }

                        lock.lock();
                        nrAcabados++;
                        lock.unlock();

                        cond.signalAll();
                        while (nrAcabados != jogadores.size()) {
                            cond.await();
                        }

                        int maior=0;
                        String vencedor=null;
                        for(String jogador : jogadores.keySet()){
                            if (jogadores.get(jogador)>maior){
                                maior=jogadores.get(jogador);
                                vencedor=jogador;
                            }
                        }

                        bufferedWriter.write("O vencedor é"+vencedor);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
