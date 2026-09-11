
class Book {
    private String title;
    private String author;
    private Double price;
    /*
    Constructor chaining in Java is the practice of calling one constructor from another constructor
    either within the same class or from a parent class. Its primary purpose is to eliminate code
    duplication and pass parameters down a chain so that object initialization is handled in one
    central place.
     */
    public Book(){
        this("unknown", "unknown", 0.0);
    }

    Book(String title){
        this(title, "unknown", 0.0);
        System.out.println("i am inside this method");
    }

    Book(String title, String author) {

        this(title, author, 0.0);
    }

    Book(String title, String author, Double price) {
         this.title = title;
         this.author = author;
         this.price = price;

        System.out.println("i am in constructor ");
    }

    void displayDetails(){
        System.out.println(
                "title : " +this.title+ "  Author : "+ this.author+" Price : "+ this.price
        );
    }
}
class ConOverloading {
    public static void main(String[] args) {
        Book b2 = new Book("Revenge and Trantum");
        Book b1 = new Book("Under the Pink sky", "Meenakshi", 850.0);
        Book b3 = new Book("Anamika", "Meenakshi");
       // Book b2 = b1;
       // b2.setPrice(900.0);
        b2.displayDetails();
        b1.displayDetails();
       // System.out.println(b.getPrice());
        b3.displayDetails();
    }
}

/*
---------------------------OUTPUT --------------

i am in constructor
i am inside this method
i am in constructor
i am in constructor
title : Revenge and Trantum  Author : unknown Price : 0.0
title : Under the Pink sky  Author : Meenakshi Price : 850.0
title : Anamika  Author : Meenakshi Price : 0.0
PS E:\E\CORE-JAVA\oops\class>



-----------------Explaination---------------------

Java sees:

this(title, "Unknown", 0.0);

Don't read it as three separate things.

Read the whole thing as:

"Call another constructor of THIS SAME object, using these three values."

So Java effectively moves to:

Book(String title, String author, double price)

with:

title  = "Java"
author = "Unknown"
price  = 0.0

So visually:

new Book("Java")
       |
       v
┌─────────────────────────┐
│ Book(String title)      │
│                         │
│ title = "Java"          │
│                         │
│ this(...)  ─────────────┼──────────┐
└─────────────────────────┘          │
                                     ↓
                         ┌─────────────────────────────┐
                         │ Book(String title,          │
                         │      String author,         │
                         │      double price)          │
                         │                             │
                         │ title  = "Java"             │
                         │ author = "Unknown"          │
                         │ price  = 0.0                │
                         │                             │
                         │ this.title = title;         │
                         │ this.author = author;       │
                         │ this.price = price;         │
                         └─────────────────────────────┘

THAT is the chaining.

The first constructor doesn't initialize the fields itself.

It says:

"Hey, use the 3-argument constructor and give it these values."




The reason is that this(...) isn't an ordinary method call. It means:

"Before this constructor does anything else, initialize this object by entering another constructor."

Imagine:

Book(String title) {
    System.out.println("Starting...");
    this(title, "Unknown", 0.0);
}

Java would have to do:

1. Enter Book(String)
2. Execute println()
3. THEN jump to another constructor

But constructor initialization is supposed to establish the object before the constructor body does other work.

So Java enforces:

Book(String title) {
    this(title, "Unknown", 0.0);  // FIRST
    System.out.println("Starting...");
}

Now the flow is:

new Book("Java")
       ↓
Book(String title)
       ↓
this(...)          ← initialize through 3-arg constructor FIRST
       ↓
Book(String, String, double)
       ↓
fields initialized
       ↓
return to Book(String)
       ↓
System.out.println("Starting...")

That's the mental model I want you to keep.

One more important reason

Suppose Java allowed this:

Book(String title) {
    this.title = title;
    this(title, "Unknown", 0.0);
}

Now you're saying:

First initialize part of the object yourself
        ↓
Then ask another constructor to initialize the object

That creates ambiguity about which constructor is responsible for initialization and in what order.

Java avoids that entire mess by saying:

If you're delegating to another constructor, delegate first.

So:

this(...);       // constructor delegation
// THEN
other statements

And notice something beautiful here:

Book(String title) {
    this(title, "Unknown", 0.0);

    System.out.println("Now I'm back");
}

After this(...) finishes, execution comes back to the original constructor.

So constructor chaining isn't some magical disappearance.

It's literally:

A constructor
     ↓
calls another constructor
     ↓
that constructor finishes
     ↓
control comes back
     ↓
original constructor continues

That's the piece I wanted you to see.
 */