interface Printable {
    void print();
}

class Report implements Printable {

    @Override
   protected void print() {  // so here it should be ---- > public void print()
        System.out.println("Printing report...");
    }
}

/*
Exp2.java:8: error: print() in Report cannot implement print() in Printable
    void print() {
         ^
  attempting to assign weaker access privileges; was public
1 error

 */