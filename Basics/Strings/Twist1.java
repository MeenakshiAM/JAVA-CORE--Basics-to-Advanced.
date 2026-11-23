class Twist1{
    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "abcd";
        System.out.println(s1 = s2);

    }
}
/*
---------------- output---------

PS E:\E\CORE-JAVA\basics\strings> java Twist1
abcd


********************** CONCEPT ************

The Concept: Assignment as an Expression
The output abcd happens due to two key Java features working together: the assignment operator (=) and the fact that assignment statements in Java are expressions that evaluate to a value.

Step-by-Step Breakdown
String s1 = "abc";

A reference variable s1 is created, pointing to the string literal "abc".

String s2 = "abcd";

Another reference variable s2 is created, pointing to the string literal "abcd".

s1 = s2 (Inside println)

Notice that this uses a single equals sign (=), which is the assignment operator, not the double equals sign (==) used for comparison.

This statement takes the value stored in s2 (the memory reference for "abcd") and assigns it to s1. Now, both s1 and s2 point to "abcd".

Crucial Rule: In Java, an assignment operation is an expression that evaluates to the value being assigned. Because s2 ("abcd") was assigned to s1, the entire expression s1 = s2 evaluates to "abcd".

System.out.println(...)

The println method prints whatever value the expression inside it evaluates to, which is "abcd".
 */