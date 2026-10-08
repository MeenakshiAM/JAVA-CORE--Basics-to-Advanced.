interface Printable {
    void print();
}

class Report implements Printable {

    @Override
    public void print() {
        System.out.println("Printing report.....");
    }

    void generateReport() {
        System.out.println("Generating report...");
    }
}

class Invoice implements Printable {

    @Override
    public void print() {
        System.out.println("Printing invoice.....");
    }

    void calculateTax() {
        System.out.println("Calculating invoice tax...");
    }
}

class Receipt implements Printable {

    @Override
    public void print() {
        System.out.println("Printing receipt...");
    }
}

public class InterfaceExp {

    static void printDocument(Printable document) {
        document.print();
    }

    public static void main(String[] args) {

        Report report = new Report();
        Invoice invoice = new Invoice();
        Receipt receipt = new Receipt();

        // Each object can be used directly
        report.generateReport();
        invoice.calculateTax();

        // All of them can be treated as Printable
        printDocument(report);
        printDocument(invoice);
        printDocument(receipt);

        // Polymorphism through interface reference
        Printable p = new Report();
        p.print();

        // p.generateReport();  //
        // because Printable doesn't declare generateReport()
    }
}


/*
Generating report.....
Calculating invoice tax...
Printing report.....
Printing invoice.....
Printing receipt...
Printing report...

 */