// ---------------- THREAD CREATION - USING CLASS -------------------------
class ClassThread extends Thread {
    // Constructor
    ClassThread() {
        // You can use super("ThreadName") here if you want to set a name
        super("My Custom Thread");
    }

    @Override
    public void run() {
        System.out.println("hello from new thread created using the Thread class");
    }
}

public class CreateThread {
    public static void main(String[] args) {
        ClassThread t1 = new ClassThread();

        t1.start();  // starts a new thread
         t1.run(); // would just call run() in the same thread
    }
}
