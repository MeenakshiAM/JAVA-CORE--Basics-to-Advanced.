/*
Experiment 3 — ATM Withdrawal Race

Create one shared BankAccount:

balance = ₹10,000
Two threads:

Alice → withdraw ₹7,000
Bob   → withdraw ₹7,000
The withdrawal operation should conceptually do:

check balance
↓
wait a little
↓
subtract amount
 */
class InsufficientBalance extends Exception {
    InsufficientBalance(){
        super("Insufficient balance .....");
    }

}
class Bank{

    int amount  = 10000;

    synchronized  void decrement (int no) throws InsufficientBalance{
        if(amount < no){
            throw new  InsufficientBalance();
        }
        amount = amount - no;
        System.out.println(" the amount of" + no + "taken by" + Thread.currentThread().getName());
    }
}
class NewThread implements Runnable {
    Bank bank;
    int no;

    NewThread(Bank bank, int no){
        this.bank = bank;
        this.no = no;
    }

    @Override
    public void run() {
        try{
            bank.decrement(this.no);
        } catch (InsufficientBalance e) {
            throw new RuntimeException(e);
        }

    }
}

class Atm {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();

        Thread t1 = new Thread(new NewThread(bank, 7000), "user 1");
        Thread t2 = new Thread(new NewThread(bank, 7000), "user 2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

/*
----------------- output --------------------
 the amount of 7000 taken byuser 1
Exception in thread "user 2" java.lang.RuntimeException: InsufficientBalance: Insufficient balance .....
        at NewThread.run(atm.java:51)
        at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: InsufficientBalance: Insufficient balance .....
        at Bank.decrement(atm.java:31)
        at NewThread.run(atm.java:49)
        ... 1 more

 */