// ---------------- THREAD CREATION - USING CLASS -------------------------
class ClassThread extends Thread {
    // Constructor

    // while defining the new thread class u can create as many methods as required for the operation
    ClassThread() {
        // You can use super("ThreadName") here if you want to set a name
        super("My Custom Thread");
    }

    @Override
    public void run() {

        System.out.println (" inside run for loop : "+ Thread.currentThread().getName());
        forLoop(8);
        System.out.println("hello from new thread created using the Thread class");

    }

    public void forLoop (int n) {
        try{

            for(int i = 0; i<= n; i++) {
                System.out.println(
                        "forLoop(): " +
                                Thread.currentThread().getName()+"-> "+i
                );
               // System.out.println("");
               // System.out.println();
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println(" child thread got interrupted");
        }
    }
}

public class CreateThread {
    public static void main(String[] args) {
        ClassThread t1 = new ClassThread();

        t1.start();  // starts a new thread\
        t1.forLoop(6);
       //  t1.run(); // would just call run() in the same thread

        System.out.println("the threads name is : " + t1.getName());

    }


    //------------------ output------------------

    /* inside run for loop : My Custom Thread
forLoop(): My Custom Thread-> 0
forLoop(): main-> 0
forLoop(): main-> 1
forLoop(): My Custom Thread-> 1
forLoop(): My Custom Thread-> 2
forLoop(): main-> 2
forLoop(): main-> 3
forLoop(): My Custom Thread-> 3
forLoop(): main-> 4
forLoop(): My Custom Thread-> 4
forLoop(): main-> 5
forLoop(): My Custom Thread-> 5
forLoop(): main-> 6
forLoop(): My Custom Thread-> 6
forLoop(): My Custom Thread-> 7
the threads name is : My Custom Thread
forLoop(): My Custom Thread-> 8
hello from new thread created using the Thread class

*/
}
