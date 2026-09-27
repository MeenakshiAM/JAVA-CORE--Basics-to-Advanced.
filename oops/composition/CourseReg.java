/*
--------------Questions---------------
You need two classes:

Student

A student should have:

name
a collection of courses they are registered for

It should be able to:

register for a course
display all registered courses
Course

A course should have:

courseName
courseCode

It should be able to:

display its details
Main

Create:

Student: Meenakshi
Courses:
  Java
  Computer Networks

Register both courses for the student and display the student's details and registered courses.
 */
import java.util.List;
import java.util.ArrayList;

class Course {
  String code;
     String name;

    Course (String code, String name) {
        this.code = code;
        this.name = name;
    }
}

class Student {
   private String name;
   private List<Course> courseList;

    Student(String name) {
        this.name = name;
        this.courseList = new ArrayList<>() ;
    }
    void regCourse (Course c){
        courseList.add(c);
    }

    void display() {
        System.out.println("Name = " + this.name);
        System.out.println("Courses taken:");
        for (Course c : courseList) {
            System.out.println("  " + c.name + " " +c.code);
        }
    }

}
class CourseReg {
    public static void main(String[] args) {

        Student s = new Student("Meenakshi");

        Course java = new Course("CS101", "Java");
        Course networks = new Course("CS202", "Computer Networks");

        s.regCourse(java);
        s.regCourse(networks);

        s.display();
    }
}

/*
--------------------- output-----------------------
java CourseReg


Name = Meenakshi
Courses taken:
  Java CS101
  Computer Networks CS202

 *//*
--------------Questions---------------
You need two classes:

Student

A student should have:

name
a collection of courses they are registered for

It should be able to:

register for a course
display all registered courses
Course

A course should have:

courseName
courseCode

It should be able to:

display its details
Main

Create:

Student: Meenakshi
Courses:
  Java
  Computer Networks

Register both courses for the student and display the student's details and registered courses.
 */
import java.util.List;
import java.util.ArrayList;

class Course {
  String code;
     String name;

    Course (String code, String name) {
        this.code = code;
        this.name = name;
    }
}

class Student {
   private String name;
   private List<Course> courseList;

    Student(String name) {
        this.name = name;
        this.courseList = new ArrayList<>() ;
    }
    void regCourse (Course c){
        courseList.add(c);
    }

    void display() {
        System.out.println("Name = " + this.name);
        System.out.println("Courses taken:");
        for (Course c : courseList) {
            System.out.println("  " + c.name + " " +c.code);
        }
    }

}
class CourseReg {
    public static void main(String[] args) {

        Student s = new Student("Meenakshi");

        Course java = new Course("CS101", "Java");
        Course networks = new Course("CS202", "Computer Networks");

        s.regCourse(java);
        s.regCourse(networks);

        s.display();
    }
}

/*
--------------------- output-----------------------
java CourseReg


Name = Meenakshi
Courses taken:
  Java CS101
  Computer Networks CS202

 */