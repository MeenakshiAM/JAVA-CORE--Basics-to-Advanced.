class Worker implements Runnable {

    @Override
    public void run() {

        try {
            doWork();

        } catch (InterruptedException e) {
            System.out.println(
                    "Worker: interruption received"
            );
        }
    }

    void doWork() throws InterruptedException {

        System.out.println("Worker: working...");
        Thread.sleep(5000);
    }
}

public class InterruptProp {

    public static void main(String[] args)
            throws InterruptedException {

        Thread t1 = new Thread(new Worker(), "Worker-1");

        t1.start();

        Thread.sleep(2000);

        System.out.println(
                "Main: requesting interruption..."
        );

        t1.interrupt();
    }
}