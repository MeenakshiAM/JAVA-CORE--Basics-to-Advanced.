class Book {
    private String title;
    private String author;
    private Double price;

    // creating a constructor
    Book(String title,String author, Double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
    void setPrice(double price) {

        this.price = price;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

     public Double getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    void displayDetails(){
        System.out.println(
                "title : " +this.title+ "  Author : "+ this.author+" Price : "+ this.price
        );
    }
}
class SimpleClass{
    public static void main(String[] args) {
        Book b = new Book("Revenge and Trantum", "Meenakshi", 750.0);
        Book b1 = new Book("Under the Pink sky", "Meenakshi", 850.0);
        Book b2 = b1;
        b2.setPrice(900.0);
        b.displayDetails();
        b1.displayDetails();
        System.out.println(b.getPrice());
        b2.displayDetails();
    }
}

/*
-----------------output -------------------
PS E:\E\CORE-JAVA\oops\class> java SimpleClass
title : Revenge and Trantum  Author : Meenakshi Price : 750.0
title : Under the Pink sky  Author : Meenakshi Price : 900.0
title : Under the Pink sky  Author : Meenakshi Price : 900.0


// even though the price set was for b2 it was reflected in b1 as well

                 ┌──────────────────────┐
b1 ─────────────►│      Book object     │
                 │ title = "Book A"      │
b2 ─────────────►│ author = "Meenakshi" │
                 │ price = 500.0        │
                 └──────────────────────┘

                 Both b1 and b2 refer to the same object.



 */