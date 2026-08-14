// creating thread using the runnable interface

class NewThread implements Runnable{

    public void run(){
        try{
            for(int i = 0; i< 8; i++) {
                System.out.println(
                                Thread.currentThread().getName()+"-> "+i
                );
                Thread.sleep(2000);
            }
        }
        catch (InterruptedException e){
            System.out.println("thread interrupted");
        }
    }
}

/*
* Unlike the thread class this new thread class is not a thread itself
* it is an executive component made by implementing the runnable interface
* MyTask
   │
   └── IS-A Runnable/task
* Thread
   │
   └── executes the task
* -------------------Here's the OOP reason this approach is useful----------
* Java only allows single class inheritance.
* Suppose you already have:    class MyServer extends SomeServerFramework
* You can't do:       class MyServer extends SomeServerFramework, Thread {}-----> XXX
* Java doesn't allow multiple class inheritance.
* But you can do:        class MyServer extends SomeServerFramework implements Runnable{}
* */


class InterThreadCreate {
    public static void main(String[] args) {
        NewThread task = new NewThread();

        Thread t = new Thread(task, "demo");
        Thread t1 = new Thread(task, "Worker-1");
        Thread t2 = new Thread(task, "Worker-2");
        Thread t3 = new Thread(task, "Worker-3");

        t1.start();
        t2.start();
        t3.start();
        t.start();


    }
    /*
    * -----------------------output----------------------
            Worker-1-> 0
            demo-> 0
            Worker-3-> 0
            Worker-2-> 0
            Worker-1-> 1
            demo-> 1
            Worker-3-> 1
            Worker-2-> 1
            Worker-3-> 2
            Worker-1-> 2
            Worker-2-> 2
            demo-> 2
            demo-> 3
            Worker-1-> 3
            Worker-3-> 3
            Worker-2-> 3
            Worker-1-> 4
            demo-> 4
            Worker-3-> 4
            Worker-2-> 4
            demo-> 5
            Worker-1-> 5
            Worker-2-> 5
            Worker-3-> 5

    * */
}
/*
* here we have created a runnable task and then pushed that to a thread to execute it ..
* this can be done while initializing the thread itself
*
* class newThread{
*   Thread t
*   newThread(){
*       t = new Thread (this, "name of thread"};
*       t.start()
*   }
* }
*
* this is how we invoke through the constructor
*
* */