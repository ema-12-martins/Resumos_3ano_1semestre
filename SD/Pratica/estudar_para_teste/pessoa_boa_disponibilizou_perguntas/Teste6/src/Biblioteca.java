import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Biblioteca{
    Map<Integer, Questao> mapa_questoes;
    ReentrantLock lock_mapa_questoes;
    Condition cond;

    //...................................................................................................
    private class Questao {
        String pergunta;
        String resposta;
        int numero_tentativas;
        boolean foi_acertada;
        int id;
        int conta_perguntas = 1;
        ReentrantLock lock_questao;

        public Questao(String pergunta, String resposta) {
            this.pergunta = pergunta;
            this.resposta = resposta;
            this.numero_tentativas = 10;
            this.foi_acertada = false;
            this.id = conta_perguntas;
            conta_perguntas++;
            this.lock_questao = new ReentrantLock();
        }
    }

    //................................................................................
    public Biblioteca() {
        this.mapa_questoes = new HashMap<Integer, Questao>();
        this.lock_mapa_questoes = new ReentrantLock();
        this.cond = lock_mapa_questoes.newCondition();
    }

    private void adiciona(String pergunta, String resposta) {
        Questao nova_questao = new Questao(pergunta, resposta);

        try {
            this.lock_mapa_questoes.lock();
            this.mapa_questoes.put(nova_questao.id, nova_questao);
            this.cond.signalAll();
        } finally {
            this.lock_mapa_questoes.unlock();
        }
    }

    ;

    private Questao obtem(int id) {
        try {
            lock_mapa_questoes.lock();
            int i = id;
            int lenght = mapa_questoes.size();
            while (i < lenght) {
                Questao atual = mapa_questoes.get(i);
                if (atual.numero_tentativas < 10) {
                    atual.numero_tentativas++;
                    return atual;
                } else {
                    i++;
                }
            }
            cond.await();
            return obtem(id);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock_mapa_questoes.unlock();
        }
    }

    ;

    private String responde(int id_questao, String resposta) {
        String resposta_para_utilizador;
        try {
            this.lock_mapa_questoes.lock();
            Questao questao_atual = this.mapa_questoes.get(id_questao);
            questao_atual.lock_questao.lock();
            if (questao_atual.foi_acertada) {
                resposta_para_utilizador = "R";
            } else {
                if (questao_atual.resposta.equals(resposta)) {
                    resposta_para_utilizador = "C";
                    questao_atual.foi_acertada = true;
                } else {
                    resposta_para_utilizador = "E";
                }
            }
            this.lock_mapa_questoes.unlock();
            questao_atual.lock_questao.unlock();
            return resposta_para_utilizador;
        } finally {
            this.lock_mapa_questoes.unlock();
        }

    }

    ;

    public static void main(String[] args) throws IOException {
        Biblioteca biblioteca = new Biblioteca();
        ServerSocket serverSocket = new ServerSocket(12345);

        while (true) {
            Socket client = serverSocket.accept();
            Thread thread = new Thread(() -> {
                try {
                    BufferedWriter buffer_out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    BufferedReader buffer_in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    String opcao;
                    while ((opcao = buffer_in.readLine()) != null) {
                        switch (opcao) {
                            case "adicionar":
                                String pergunta = buffer_in.readLine();
                                String resposta = buffer_in.readLine();
                                biblioteca.adiciona(pergunta, resposta);
                                break;
                            case "obtem":
                                String idStr = buffer_in.readLine();
                                int id = Integer.parseInt(idStr);
                                Questao questao = biblioteca.obtem(id);
                                if (questao != null) {
                                    buffer_out.write(questao.id + "\n");
                                    buffer_out.write(questao.pergunta + "\n");
                                    buffer_out.flush();
                                }
                                break;
                            case "responde":
                                String idResp = buffer_in.readLine();
                                String resposta_utilizador = buffer_in.readLine();
                                String respostaParaCliente = biblioteca.responde(Integer.parseInt(idResp), resposta_utilizador);
                                buffer_out.write(respostaParaCliente + "\n");
                                buffer_out.flush();
                                break;
                            default:
                                buffer_out.write("A opcao nao á válida\n");
                                buffer_out.flush();
                                break;
                        }
                    }
                    client.shutdownOutput();
                    client.shutdownInput();
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }
    }
}
