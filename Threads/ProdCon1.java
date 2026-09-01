
class Q{
    int n;
    synchronized int get(){
        System.out.println("got: "+n);
        return n;
    }

    synchronized  void put(int n){
        this.n = n;
        System.out.println("put: "+n);
    }
}

class Producer implements Runnable{
    Q q ;
    Producer(Q q){
        this.q = q;
        new Thread(this, "producer").start();
    }

    public void run() {
        int i = 0;
        while (true){
            q.put(i++);
        }
    }
}

class Consumer  implements Runnable{
    Q q ;
    Consumer(Q q){
        this.q = q;
        new Thread(this, "consumer").start();
    }

    public void run(){
        while(true){
            q.get();
        }
    }
}

public class ProdCon1 {
    public static void main(String[] args) {
        Q q = new Q();
        new Producer(q);
        new Consumer(q);
    }
}

/*
output

put: 26765
put: 26766
put: 26767
put: 26768
put: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769
got: 26769


 */