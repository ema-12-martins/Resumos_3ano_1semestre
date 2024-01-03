import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Controlador {
    float temperatura_atual=0;
    float temperatura_alvo=5;
    ReentrantLock lock = new ReentrantLock();
    Condition cond = lock.newCondition();

    public void temperatura(int centigrados){// medidor indica temperatura actual
        lock.lock();
        temperatura_atual=centigrados;
        cond.signalAll();
        lock.unlock();
    }

    public void limiar(int centigrados){// utilizador indica limiar de activao
        lock.lock();
        temperatura_alvo=centigrados;
        lock.unlock();
    }

    public boolean on_off(boolean estadoatual) throws InterruptedException {// caldeira pergunta se estado mudou
        boolean res = false;
        try{
            this.lock.lock();
            if(estadoatual == true){
                while(temperatura_atual < temperatura_alvo)
                    cond.await();
                res = false;
            }else{
                while(temperatura_atual >= temperatura_alvo)
                    cond.await();
                res = true;
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        finally{
            this.lock.unlock();
        }
        return res;
    }
}