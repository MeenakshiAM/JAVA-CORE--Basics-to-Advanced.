/*
Experiment 5 — Resource Pool
Now the one you liked.

Imagine your application has only 2 printers:

Printer 1
Printer 2
But there are 5 workers who want to use a printer.

A worker must:

request printer
↓
if none available → wait
↓
get printer
↓
use printer
↓
release printer
↓
notify waiting workers

 */

import java.util.*;

class Printer {
    // its mentions we have 2 printers
    // its work is to print
    String name;

    Printer(String name) {
        this.name = name;
    }

    void print(String str){// this method is synchronized becoz when one of the person is using it others should not use it
        System.out.println(
                Thread.currentThread().getName()
                        + " -> " + name
                        + " -> " + str
        );
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
class Resources {
    Printer p1 = new Printer("printer 1");
    Printer p2 = new Printer("printer 2");

    Queue<Printer> buffer = new LinkedList<>();
    Resources() {// initializations are always inside the constructors
        buffer.add(p1);
        buffer.add(p2);
    }
    private final Object lock = new Object();

     void getPrinter(String str) {
         Printer p ; // take the available printer

         synchronized (lock) {

             while (buffer.isEmpty()) {
                 System.out.println(
                         Thread.currentThread().getName()
                                 + " -> No printer available. Waiting..."
                 );

                 try {
                     lock.wait(); // waiting for the printer to be available
                 } catch (InterruptedException e) {
                     System.out.println(
                             Thread.currentThread().getName()
                                     + " -> Interrupted while waiting."
                     );
                     return;
                 }
             }


             p = buffer.poll();

             System.out.println(
                     Thread.currentThread().getName()
                             + " -> Got a printer. Printers available: "
                             + buffer.size()
             );
         }
            p.print(str);
         synchronized (lock) {

             System.out.println(
                     Thread.currentThread().getName()
                             + " -> Finished printing. Returning "
                             + p.name
             );

             buffer.add(p);

             System.out.println(
                     Thread.currentThread().getName()
                             + " -> " + p.name
                             + " returned. Printers available: "
                             + buffer.size()
             );

             lock.notifyAll();
         }
     }

}

class Workers implements Runnable {
    String str;
    Resources rs;

    Workers(String str, Resources rs) {
        this.str = str;
        this.rs = rs;
    }

    @Override
    public void run() {
        rs.getPrinter(this.str);
    }
}
class ResPool {
    public static void main(String[] args) throws InterruptedException{
        Resources rs = new Resources();

        Thread t1 = new Thread(new Workers("this is the print mg of worker 1 ", rs), "Worker1");
        Thread t2 = new Thread(new Workers("this is the print mg of worker 2 ", rs), "Worker2");
        Thread t3 = new Thread(new Workers("this is the print mg of worker 3 ", rs), "Worker3");
        Thread t4 = new Thread(new Workers("this is the print mg of worker 4 ", rs), "Worker4");
        Thread t5 = new Thread(new Workers("this is the print mg of worker 5 ", rs), "Worker5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

    }
}
/*
E:\E\CORE-JAVA\experiments\set2> javac ResPool.java
PS E:\E\CORE-JAVA\experiments\set2> java ResPool
Worker1 -> Got a printer. Printers available: 1
Worker2 -> Got a printer. Printers available: 0
Worker4 -> No printer available. Waiting...
Worker3 -> No printer available. Waiting...
Worker5 -> No printer available. Waiting...
Worker2 -> printer 2 -> this is the print mg of worker 2
Worker1 -> printer 1 -> this is the print mg of worker 1
Worker2 -> Finished printing. Returning printer 2
Worker2 -> printer 2 returned. Printers available: 1
Worker4 -> Got a printer. Printers available: 0
Worker4 -> printer 2 -> this is the print mg of worker 4
Worker5 -> No printer available. Waiting...
Worker3 -> No printer available. Waiting...
Worker1 -> Finished printing. Returning printer 1
Worker1 -> printer 1 returned. Printers available: 1
Worker5 -> Got a printer. Printers available: 0
Worker5 -> printer 1 -> this is the print mg of worker 5
Worker3 -> No printer available. Waiting...
Worker4 -> Finished printing. Returning printer 2
Worker4 -> printer 2 returned. Printers available: 1
Worker3 -> Got a printer. Printers available: 0
Worker3 -> printer 2 -> this is the print mg of worker 3
Worker5 -> Finished printing. Returning printer 1
Worker5 -> printer 1 returned. Printers available: 1
Worker3 -> Finished printing. Returning printer 2
Worker3 -> printer 2 returned. Printers available: 2


 */