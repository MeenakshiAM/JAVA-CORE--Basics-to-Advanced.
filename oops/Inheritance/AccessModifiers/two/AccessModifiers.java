package two;

import one.Parent;

public class AccessModifiers {

    public static void main(String[] args) {

        Parent p = new Parent();
        Child c = new Child();

        System.out.println("p public : " + p.publicValue);
        System.out.println("p protected : " + p.protectedValue);
        System.out.println("p default : " + p.defaultValue);
        System.out.println("p private : " + p.privateValue);

        System.out.println("c public : " + c.publicValue);
        System.out.println("c protected : " + c.protectedValue);
        System.out.println("c default : " + c.defaultValue);
        System.out.println("c private : " + c.privateValue);

        c.childTest();
    }
}

/*
------------------output --------------------------
two\Child.java:13: error: defaultValue is not public in Parent; cannot be accessed from outside package
        System.out.println("default : " + defaultValue);
                                          ^
two\Child.java:15: error: privateValue has private access in Parent
        System.out.println("private : " + privateValue);
                                          ^
two\AccessModifiers.java:13: error: protectedValue has protected access in Parent
        System.out.println("p protected : " + p.protectedValue);
                                               ^
two\AccessModifiers.java:14: error: defaultValue is not public in Parent; cannot be accessed from outside package
        System.out.println("p default : " + p.defaultValue);
                                             ^
two\AccessModifiers.java:15: error: privateValue has private access in Parent
        System.out.println("p private : " + p.privateValue);
                                             ^
two\AccessModifiers.java:18: error: protectedValue has protected access in Parent
        System.out.println("c protected : " + c.protectedValue);
                                               ^
two\AccessModifiers.java:19: error: defaultValue is not public in Parent; cannot be accessed from outside package
        System.out.println("c default : " + c.defaultValue);
                                             ^
two\AccessModifiers.java:20: error: privateValue has private access in Parent
        System.out.println("c private : " + c.privateValue);
                                             ^
8 errors
 */