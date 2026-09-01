
/*
this experiment is done to demonstrate the poling using the consumer-producer problem
Polling is implemented in loop to check same condition repeatedly, which leads to waste of CPU Burst time;

 */
class Box {

    boolean available = false;  // initially noting is avialable
    long checks = 0;

    void consume() {

        System.out.println("Consumer started polling...");

        while (!available) {
            checks++;
        }

        System.out.println(
                "Consumer: Data found!"
        );

        System.out.println(
                "Number of checks: " + checks
        );
    }

    void produce() {

        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        available = true;

        System.out.println(
                "Producer: Data produced!"
        );
    }
}


public class PollingDemo {

    public static void main(String[] args)
            throws InterruptedException {

        Box box = new Box();

        Thread consumer =
                new Thread(box::consume, "Consumer");

        Thread producer =
                new Thread(box::produce, "Producer");

        consumer.start();
        producer.start();

        consumer.join();
        producer.join();
    }
}

/*------------------ output--------------
Consumer started polling...
Producer: Data produced!
Consumer: Data found!
Number of checks: 1847392947


-------------------Explanation----------
well that was one hell of a dely ...

Producer
   │
   └── sleeping 😴


Consumer
   │
   ├── check
   ├── check
   ├── check
   ├── check
   ├── check
   ├── check
   ├── check
   ├── ...
   └── millions/billions of checks

The Consumer isn't actually doing useful work.

It's consuming CPU cycles just to repeatedly discover that nothing has changed.

Repeated checking happens
 */