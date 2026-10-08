interface Printable {
    void print();
}

interface Savable {
    void save();
}

class Report implements Printable, Savable {

    @Override
    public void print() {
        System.out.println("printing");
    }

    @Override
    public void save() {
        System.out.println("saving");
    }
}
class Basic {
    public static void main(String[] args) {
        Printable p = new Report();
        p.print();   // ✅
        p.save();    // ❌

        Savable s = new Report();
        s.print();
        s.save();

        Report r = new Report();

        r.print();   // ✅
        r.save();    // ✅
    }
}