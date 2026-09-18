class Parent {

    public int publicValue = 10;
    protected int protectedValue = 20;
    int defaultValue = 30;
    private int privateValue = 40;

    void parentTest() {
        System.out.println("public value  : " + publicValue);
        System.out.println("Private value : " + this.privateValue  );
        System.out.println("default value : " + this.defaultValue);
        System.out.println("protected value : " + this.protectedValue);
    }
}

class Child extends Parent {

    void childTest() {
        // Try accessing all four here
        System.out.println("public value  : " + publicValue);
       // System.out.println("Private value : " + super.privateValue  );
        System.out.println("default value : " + defaultValue);
        System.out.println("protected value : " + this.protectedValue);
    }
}
class AccessModifiers {
    public static void main(String[] args) {
        Parent p = new Parent();
        p.parentTest();
        System.out.println(p.publicValue);
        System.out.println(p.protectedValue);
        System.out.println(p.defaultValue);
       // System.out.println(p.privateValue);

        Child c = new Child();
        c.childTest();
        System.out.println(c.publicValue);
        System.out.println(c.protectedValue);
        System.out.println(c.defaultValue);
        //System.out.println(c.privateValue);
    }
}
/*
public value  : 10
Private value : 40
default value : 30
protected value : 20
10
20
30
public value  : 10
default value : 30
protected value : 20
10
20
30

 */