/*
------------- problem statement--------------------

Problem Statement

Design and implement a thread-safe bounded buffer using Java's multithreading mechanisms.
The system should support multiple producer threads that add items to a shared buffer
and multiple consumer threads that remove items from it.
The buffer must have a fixed capacity.
Producers should wait when the buffer is full, while consumers should wait when the buffer is empty.
Appropriate synchronization and inter-thread communication mechanisms must be used to ensure that the shared buffer is accessed safely and efficiently.

Requirements
Create a shared buffer with a fixed capacity of 5.
Create two producer threads that add items to the buffer.
Create two consumer threads that remove items from the buffer.
Producers must wait when the buffer becomes full.
Consumers must wait when the buffer becomes empty.
Use synchronized to protect the shared buffer.
Use wait() for threads that cannot currently proceed.
Use notifyAll() to inform waiting threads when the buffer state changes.
Ensure that no items are lost or consumed incorrectly because of concurrent access.


                 BUFFER
        ┌─────────────────────┐
        │ [ ] [ ] [ ] [ ] [ ] │
        └─────────────────────┘
             capacity = 5

 Producer-1 ──────►
 Producer-2 ──────►

 Consumer-1 ◄──────
 Consumer-2 ◄──────

Rules:

Producer
                Buffer FULL?
                     ↓
                   YES → wait()
                     ↓
                   NO
                     ↓
                put item
                     ↓
                notifyAll()

Consumer
                Buffer EMPTY?
                     ↓
                   YES → wait()
                     ↓
                   NO
                     ↓
                take item
                     ↓
                notifyAll()

 */

import java.util.*;

class Buffer {
    final Queue<Integer> queue = new LinkedList<>();
    final int capacity = 5;

    // consumer works only when thequeue is full

    synchronized int consume(){
        while (queue.isEmpty()) {
            try{
                wait();
            }
            catch(InterruptedException e){
                System.out.println("Interruption occured");
            }
        }
        int k = queue.poll();
        System.out.println(
                Thread.currentThread().getName()
                        + " consumed " + k
                        + " | Buffer size = " + queue.size()
        );
        notifyAll();

        return k;
    }

    public synchronized void produce(int value)
            throws InterruptedException {

        while (queue.size() == capacity) {
            wait();
        }

        queue.add(value);

        System.out.println(
                Thread.currentThread().getName()
                        + " produced " + value
                        + " | Buffer size = " + queue.size()
        );

        notifyAll();
    }

}

class Producer implements Runnable {
    private final Buffer buffer;

    Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {

            for (int i = 1; i <= 10; i++) {

                buffer.produce(i);

                Thread.sleep(500);
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }
    }
}

class Consumer implements Runnable{
    Buffer buffer = new Buffer();

    Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {

            for (int i = 1; i <= 10; i++) {

                buffer.consume();

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }
    }
}

class InterThreadCommunication {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();

        Thread p1 =
                new Thread(new Producer(buffer), "Producer-1");

        Thread p2 =
                new Thread(new Producer(buffer), "Producer-2");

        Thread c1 =
                new Thread(new Consumer(buffer), "Consumer-1");

        Thread c2 =
                new Thread(new Consumer(buffer), "Consumer-2");

        p1.start();
        p2.start();

        c1.start();
        c2.start();

        p1.join();
        p2.join();

        c1.join();
        c2.join();

        System.out.println("All threads finished.");
    }
}


/*
----------------------output---------------------------
java InterThreadCommunication
Producer-1 produced 1 | Buffer size = 1
Producer-2 produced 1 | Buffer size = 2
Consumer-1 consumed 1 | Buffer size = 1
Consumer-2 consumed 1 | Buffer size = 0
Producer-1 produced 2 | Buffer size = 1
Producer-2 produced 2 | Buffer size = 2
Consumer-1 consumed 2 | Buffer size = 1
Consumer-2 consumed 2 | Buffer size = 0
Producer-1 produced 3 | Buffer size = 1
Producer-2 produced 3 | Buffer size = 2
Producer-2 produced 4 | Buffer size = 3
Producer-1 produced 4 | Buffer size = 4
Consumer-1 consumed 3 | Buffer size = 3
Consumer-2 consumed 3 | Buffer size = 2
Producer-2 produced 5 | Buffer size = 3
Producer-1 produced 5 | Buffer size = 4
Producer-2 produced 6 | Buffer size = 5
Consumer-2 consumed 4 | Buffer size = 4
Producer-1 produced 6 | Buffer size = 5
Consumer-1 consumed 4 | Buffer size = 4
Producer-2 produced 7 | Buffer size = 5
Consumer-2 consumed 5 | Buffer size = 4
Producer-1 produced 7 | Buffer size = 5
Consumer-1 consumed 5 | Buffer size = 4
Producer-2 produced 8 | Buffer size = 5
Consumer-2 consumed 6 | Buffer size = 4
Producer-2 produced 9 | Buffer size = 5
Consumer-1 consumed 6 | Buffer size = 4
Producer-1 produced 8 | Buffer size = 5
Consumer-2 consumed 7 | Buffer size = 4
Producer-2 produced 10 | Buffer size = 5
Consumer-1 consumed 7 | Buffer size = 4
Producer-1 produced 9 | Buffer size = 5

 */