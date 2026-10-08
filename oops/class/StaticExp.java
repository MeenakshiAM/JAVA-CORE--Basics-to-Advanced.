class Student {

    String name;                  // non-static
    static int studentCount = 0; // static
    /*
   A static field belongs to the class rather than to each individual object, so all objects of that class share the same field.
                  Student class
                   |
             studentCount = 3
                /      \
              s1        s2
               \        /
                \      /
                 s3

     */

    Student(String name) {
        this.name = name;
        studentCount++;
    }


    void display() {
        System.out.println("Name: " + name);
        System.out.println("Student count: " + studentCount);
    }
}

public class StaticExp {
    public static void main(String[] args) {

        Student s1 = new Student("Meenakshi");

        System.out.println("After creating s1:");
        s1.display();

        Student s2 = new Student("Anu");

        System.out.println("\nAfter creating s2:");

        s1.display();
        s2.display();

        Student s3 = new Student("Rahul");

        System.out.println(Student.studentCount);
        System.out.println(s1.studentCount);

        s1.display();
        s2.display();
        s3.display();
    }
}
/*
PS E:\E\CORE-JAVA\oops\class> java StaticExp
After creating s1:
Name: Meenakshi
Student count: 1

After creating s2:
Name: Meenakshi
Student count: 2
Name: Anu
Student count: 2

After creating s3:
Name: Meenakshi
Student count: 3
Name: Anu
Student count: 3
Name: Rahul
Student count: 3

---------------- i tried this by removing the static keyword -------------

PS E:\E\CORE-JAVA\oops\class> javac StaticExp.java
PS E:\E\CORE-JAVA\oops\class> java StaticExp
After creating s1:
Name: Meenakshi
Student count: 1

After creating s2:
Name: Meenakshi
Student count: 1
Name: Anu
Student count: 1

After creating s3:
Name: Meenakshi
Student count: 1
Name: Anu
Student count: 1
Name: Rahul
Student count: 1

 */