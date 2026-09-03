/*
Different Accounts, Different Tasks

Now create:

            BankAccount #1 ← Task #1 ← Thread A

            BankAccount #2 ← Task #2 ← Thread B

Both accounts start with ₹10,000.

Both customers withdraw ₹7,000.

Questions

Now ask:

"Are these two threads actually competing for the same resource?"

Then:

Can they execute concurrently?
What happens if BankAccount.withdraw() is synchronized?
Are they using the same monitor?
What happens if both accounts are represented by separate objects?
 */

class InsufficientBalance extends Exception {
    InsufficientBalance (){
        super(" insufficient balance .... ");
    }
}

class Bank {
    int total = 10000;

    synchronized void withdraw (int amount) throws InsufficientBalance{
        if (amount > total) throw new InsufficientBalance();

        total = total - amount;
        System.out.println("total amount  = " + total + "  total taken "+amount);
    }
}

class NewThread implements Runnable{
    Bank bank ;
    int amount;

    NewThread(Bank bank, int amount) {
        this.bank = bank;
        this.amount = amount;
    }
    @Override
     public  void run () {
        try {
            bank.withdraw(amount);
        } catch (InsufficientBalance e) {
            throw new RuntimeException(e);
        }
    }
}

class BankExp2 {
    public static void main(String[] args) throws InterruptedException{
        /*
        Bank b1 = new Bank();

        Thread t1 = new Thread(new NewThread(b1, 7000), "W1");
        Thread t2 = new Thread(new NewThread(b1, 7000), "W2");


        Different task objects, both carrying a reference to the same Bank object,
         and therefore both eventually try to acquire the same Bank monitor.

                    Task1 ──→ Bank #1
                    Task2 ──→ Bank #1

                    W1 ──→ lock(Bank #1)
                    W2 ──→ lock(Bank #1)

                            ↓

                    Same monitor
                            ↓
                    One enters at a time
         */

        Bank b1 = new Bank();
        Bank b2 = new Bank();

        Thread t1 = new Thread(new NewThread(b1, 7000), "W1");
        Thread t2 = new Thread(new NewThread(b2, 7000), "W2");

        /*
        Different task objects, both carrying a reference to the different Bank object,
        and therefore both eventually try to acquire the different  Bank monitor.

        remember the last one the monitors are diiferent they belong to 2 different bank objects

                        Task1 ──→ Bank #1
                        Task2 ──→ Bank #2

                        W1 ──→ lock(Bank #1)
                        W2 ──→ lock(Bank #2)

                                ↓

                        Different monitors
                                ↓
                        Can run concurrently
         */


        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
