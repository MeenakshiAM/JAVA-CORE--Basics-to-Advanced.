
public class Basic {

    public static void main(String[] args) {

        System.out.println("Hello from main");

        Thread current = Thread.currentThread();// this is done for accessing the current thread tho
        /*
        * current.getName();
            current.getPriority();
            current.isAlive();
            current.setName("My Thread");

            These are instance methods.

            They operate on the particular Thread object that current refers to
        * */

        System.out.println("Thread name: " + current.getName());
        System.out.println("Thread ID: " + current.getId());
        System.out.println("Thread priority: " + current.getPriority());
        System.out.println("thread is alive: " + current.isAlive());

        current.setName("My Thread");
        System.out.println("The new Name: " + current.getName());

        try {
            for (int i = 0; i < 6; i++) {
                System.out.println(i);
                Thread.sleep(1000); // the value is represented in milli second ie, 1 sec

                // this simply means it waits for 1 sec before printing the next no.. or continuing to next operation
                /*In the Java API, sleep() is declared as a static method.
               * Conceptually:  public static void sleep(long millis)
               * static means the method belongs to the class itself, rather than to a particular Thread object.
               * So:
               *Thread.sleep(1000);
               *means:
               * "Call the sleep() operation defined by the Thread class."
               * If sleep() is static, how does Java know WHICH thread should sleep?
               * Thread.sleep() puts the currently executing thread to sleep.
            */
            }
        }
            catch(InterruptedException e){
                System.out.println("Main THread interrupted");
            }

    }
}