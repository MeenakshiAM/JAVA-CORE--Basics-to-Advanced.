class Worker implements Runnable {

    @Override
    public void run() {

        try {
            System.out.println("Worker: starting work...");
            Thread.sleep(5000);

        } catch (InterruptedException e) {

            System.out.println("Worker: interruption received!");
            System.out.println("Worker: cleaning up...");
            System.out.println("Worker: exiting...");
        }
    }
}

public class InterruptCleanUp {

    public static void main(String[] args)
            throws InterruptedException {

        Thread t1 = new Thread(new Worker(), "Worker-1");

        t1.start();

        Thread.sleep(2000);

        System.out.println("Main: requesting interruption...");
        t1.interrupt();
    }
}