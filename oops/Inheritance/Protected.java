class Parent{
    protected int value = 7;
}

class Protected{
    public static void main(String[] args) {
        Parent p = new Parent();
        System.out.println(p.value);
    }
}

/*
java.lang.NoClassDefFoundError: protected (wrong name: Protected)
PS E:\E\CORE-JAVA\oops\inheritance> javac Protected.java
PS E:\E\CORE-JAVA\oops\inheritance> java Protected
7

 */