class NewThread implements Runnable{
    int counter = 0; // this is a variable that is manipulated by all the working threads
    public void run(){
        try{
            for(int i = 0; i< 8; i++) {
                counter++;
                System.out.println(
                        Thread.currentThread().getName()+"-> "+counter
                );
                Thread.sleep(2000);
            }
        }
        catch (InterruptedException e){
            System.out.println("thread interrupted");
        }
    }
}

class Intrupted {
    public static void main(String[] args)
            throws InterruptedException {

        NewThread task = new NewThread();
        Thread t1 = new Thread(task, "Worker-1");
        t1.start();

        Thread.sleep(3000);

        t1.interrupt();
    }
}