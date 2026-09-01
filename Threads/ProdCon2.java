class Q{
    int n;
    boolean available = false;

    synchronized int get(){
        while(!available) {  // if nothing is avilable to consume wait else proceed
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Interruption occured");
            }
        }

            System.out.println("got: "+n);
            // after you take it mark it as false
            available = false;

            // after marking notify the other producer
            notify();
            return n;


    }

    synchronized  void put(int n){
        while(available) { // if there is an item to consume wait else proceed
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Interruption occured");
            }
        }
            this.n = n; // here we have put the values
            // after putting it mark the availability
            available = true;
            System.out.println("put: "+n);

            // then notify about the update
            notify();


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

public class ProdCon2 {
    public static void main(String[] args) {
        Q q = new Q();
        new Producer(q);
        new Consumer(q);
    }
}

/*
-------------output------------
got: 14381
put: 14382
got: 14382
put: 14383
got: 14383
put: 14384
got: 14384
put: 14385
got: 14385
put: 14386

 */