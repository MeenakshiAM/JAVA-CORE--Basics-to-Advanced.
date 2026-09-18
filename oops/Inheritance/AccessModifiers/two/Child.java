package two;

import one.Parent;

public class Child extends Parent {

    void childTest() {

        System.out.println("public : " + publicValue);

        System.out.println("protected : " + protectedValue); // this works but

        System.out.println("default : " + defaultValue);

        System.out.println("private : " + privateValue);
        Parent p = new Parent();

        System.out.println(p.protectedValue); // this wont work

        /*&
        Because across packages, protected access is tied to the subclass relationship AND the object/reference being accessed.

Inside Child, you're allowed to access the inherited protected member through the Child side:
         */
    }
}