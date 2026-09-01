class PriorityTask implements Runnable {

    @Override
    public void run() {

        for (int i = 1; i <= 20; i++) {

            System.out.println(
                    Thread.currentThread().getName()
                            + " -> "
                            + i
                            + " | priority = "
                            + Thread.currentThread().getPriority()
            );
        }
    }
}


public class ThreadPriority {

    public static void main(String[] args)
            throws InterruptedException {

        PriorityTask task = new PriorityTask();

        Thread low = new Thread(task, "LOW");
        Thread normal = new Thread(task, "NORMAL");
        Thread high = new Thread(task, "HIGH");

        low.setPriority(Thread.MIN_PRIORITY);// setting the priority
        normal.setPriority(Thread.NORM_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);

        low.start();
        normal.start();
        high.start();

        low.join();
        normal.join();
        high.join();
    }
}

/*
 java ThreadPriority
HIGH -> 1 | priority = 10
HIGH -> 2 | priority = 10
NORMAL -> 1 | priority = 5
LOW -> 1 | priority = 1
HIGH -> 3 | priority = 10
HIGH -> 4 | priority = 10
NORMAL -> 2 | priority = 5
LOW -> 2 | priority = 1
HIGH -> 5 | priority = 10
HIGH -> 6 | priority = 10
NORMAL -> 3 | priority = 5
LOW -> 3 | priority = 1
HIGH -> 7 | priority = 10
NORMAL -> 4 | priority = 5
LOW -> 4 | priority = 1
HIGH -> 8 | priority = 10
NORMAL -> 5 | priority = 5
NORMAL -> 6 | priority = 5
NORMAL -> 7 | priority = 5
LOW -> 5 | priority = 1
HIGH -> 9 | priority = 10
NORMAL -> 8 | priority = 5
NORMAL -> 9 | priority = 5
LOW -> 6 | priority = 1
HIGH -> 10 | priority = 10
HIGH -> 11 | priority = 10
NORMAL -> 10 | priority = 5
LOW -> 7 | priority = 1
LOW -> 8 | priority = 1
LOW -> 9 | priority = 1
LOW -> 10 | priority = 1
HIGH -> 12 | priority = 10
HIGH -> 13 | priority = 10
NORMAL -> 11 | priority = 5
LOW -> 11 | priority = 1
HIGH -> 14 | priority = 10
NORMAL -> 12 | priority = 5
LOW -> 12 | priority = 1
HIGH -> 15 | priority = 10
NORMAL -> 13 | priority = 5
LOW -> 13 | priority = 1
LOW -> 14 | priority = 1
HIGH -> 16 | priority = 10
NORMAL -> 14 | priority = 5
HIGH -> 17 | priority = 10
HIGH -> 18 | priority = 10
HIGH -> 19 | priority = 10
HIGH -> 20 | priority = 10
LOW -> 15 | priority = 1
NORMAL -> 15 | priority = 5
NORMAL -> 16 | priority = 5
NORMAL -> 17 | priority = 5
LOW -> 16 | priority = 1
LOW -> 17 | priority = 1
LOW -> 18 | priority = 1
LOW -> 19 | priority = 1
LOW -> 20 | priority = 1
NORMAL -> 18 | priority = 5
NORMAL -> 19 | priority = 5
NORMAL -> 20 | priority = 5

 */