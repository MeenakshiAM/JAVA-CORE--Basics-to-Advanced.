/*
🧪  Create a Printer object shared by 3 worker threads.

Each worker has a different document:

            Worker 1 → "JAVA"
            Worker 2 → "THREADS"
            Worker 3 → "LOCK"

The printer prints one character at a time.

For example:

JAVA

is printed as:

J
A
V
A

with a small sleep() between characters.

Questions
What happens if all three threads print simultaneously?
Can the output become mixed?
Why does sleep() make the problem easier to observe?
Make the printer thread-safe.
Should the worker be synchronized or should the printer be synchronized?
What exactly is the shared resource?
What object should the lock belong to?
 */

class Printer{
//    private String word;
//
//    Printer(String word) {
//        this.word = word;
//    }


    // when one word is getting processed other will wait
   synchronized void print(String word) {
       int n = word.length();

       for(int i = 0; i< n; i++) {
           System.out.println(word.charAt(i));
           try{
               Thread.sleep(2000);
           } catch (java.lang.Exception e) {
               throw new RuntimeException(e);
           }
       }
    }
}
class NewThread implements Runnable{
    private String word;
    private  Printer p ;
    NewThread(String word, Printer p) {
        this.word = word;
        this.p = p;
    }



    @Override
    public void run () {
        p.print(this.word);
    }
}

class SharedPrinter{
    public static void main(String[] args) throws InterruptedException{
        Printer p = new Printer();
        // you need 1 printer that is what printint the words so ya
        Thread t1 = new Thread(new NewThread("JAVA",p), "Worker 1");
        Thread t2 = new Thread(new NewThread("THREADS",p));
        Thread t3 = new Thread(new NewThread("LOCK",p));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}