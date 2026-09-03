/*
Problem Statement

A bank has one account with ₹10,000.

Two customers simultaneously attempt to withdraw money from the same account.

Create:

BankAccount → represents the actual account
WithdrawalTask → represents the work of withdrawing
Thread → represents each customer

Create one BankAccount object.

Then create one WithdrawalTask object that references that account.

Pass the same task object to two threads.

                BankAccount #1
                     ↑
                     |
                WithdrawalTask #1
                     ↑       ↑
                     |       |
                Thread A   Thread B

Both customers attempt to withdraw ₹7,000.
 */

//just  me exploring with thread and its effect by doing same problem in different ways...

class InsufficientBalance extends Exception {
    InsufficientBalance() {
        super("Insufficient balance");
    }
}
class Bank {
    int total = 10000; // initial money in money be 10000

    synchronized void withdraw (int amount) throws InsufficientBalance {
        if(amount > total) {
            throw new InsufficientBalance();
        }

        total = total - amount;
        System.out.println(
                Thread.currentThread().getName() + " took" + amount + "total left " + total
        );
    }
}

class NewThread implements Runnable{
    Bank bank;
    int amount;

    NewThread(Bank bank, int amount) {
        this.bank = bank;
        this.amount = amount;
    }

    @Override
    public void run() {
        try{
            bank.withdraw(this.amount);
        } catch (InsufficientBalance e) {
            System.out.println(e.getMessage());
        }
    }
}

class BankExp1{
    public static void main(String[] args) throws InterruptedException{
        Bank bank = new Bank(); // here only one bank is used
        //from that 1 pool account we are taking and implementing..
        //simultaneously we are taking the money from that account by 2 people.
        //inorder to stimulate that we need 2 threads

        NewThread task1 = new NewThread(bank, 2000);
        NewThread task2 = new NewThread(bank, 3000);
        //--------- my doubt -----------
        /*
        why do we need to create 2 different object of the item task ..
            Thread t1 = new Thread(task1, "W1");
            Thread t2 = new Thread(task1, "W2");

                     Bank #1
                       ↑
                    Task #1
                    amount = 2000
                     ↑        ↑
                    W1       W2

            - becoz both takes different amount from same bank ... if it was the same task object it would have
            detected the same  amount that is 2000 ..

            We create two task objects because amount is state stored inside the task object,
            and the two executions require different amounts.
            The task objects can still reference the same shared Bank object.
         */
        Thread t1 = new Thread(task1, "W1");
        Thread t2 = new Thread(task2, "W2");
        /*
                         Bank #1
                      total = 10000
                        ↑       ↑
                        │       │
                  Task #1     Task #2
                  amount=2000 amount=3000
                     ↑           ↑
                    W1           W2
         */

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}