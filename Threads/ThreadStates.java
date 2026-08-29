
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

class ThreadStates {
    public static void main(String[] args) throws InterruptedException {

        NewThread task = new NewThread();

        Thread t1 = new Thread(task);
        System.out.println(
                "Before start: " + t1.getState()
        );

        t1.start();

        // 2. Immediately after start
        System.out.println(
                "After start: " + t1.getState()
        );

        // Give worker time to enter sleep()
        Thread.sleep(500);

        // 3. While worker is sleeping
        System.out.println(
                "While worker sleeps: " + t1.getState()
        );

        // Wait for worker to finish
        t1.join();

        // 4. After worker finishes
        System.out.println(
                "After join: " + t1.getState()
        );
    }
}