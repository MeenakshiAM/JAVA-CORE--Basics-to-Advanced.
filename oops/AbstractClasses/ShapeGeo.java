abstract class Shape{
 // it is absolutly fine to do it
    // u  can absoulutly keep this empty
}
abstract class TwoD extends Shape {
    abstract double perimeter();
    abstract double area();
}

abstract class ThreeD extends Shape {
    abstract double volume();
    abstract double surfaceArea();
}

class Rectangle extends TwoD {
    int length;
    int breadth;

    Rectangle(int length, int breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    @Override
    double area() {
        return this.length * this.breadth;
    }

    @Override
    double perimeter() {
        return 2 * (this.length + this.breadth);
    }
}

class Square extends TwoD {
    int s;
    Square(int s) {
        this.s = s;
    }

    @Override
    double area() {
        return this.s * this.s;
    }

    @Override
    double perimeter() {
        return 4 * this.s;
    }
}

class Circle extends TwoD {
    int r;
    Circle(int r) {
        this.r = r;
    }

    @Override
    double area() {
        return Math.PI * this.r * this.r;
    }

    @Override
    double perimeter() {
        return 2 * Math.PI * this.r;
    }
}

class Cylinder extends ThreeD {
    int r;
    int h;

    Cylinder(int h, int r) {
        this.h = h;
        this.r = r;
    }

    @Override
    double surfaceArea() {
        return 2 * Math.PI * r * (r + h);
    }

    @Override
    double volume() {
        return Math.PI * r * r * h;
    }
}

class ShapeGeo {
    public static void main(String[] args) {
        TwoD s1 = new Circle(2);
        TwoD s2 = new Square(2);
        TwoD s3 = new Rectangle(3, 2);
        ThreeD s4 = new Cylinder(2, 4);

        System.out.println("Circle area: " + s1.area());
        System.out.println("Square perimeter: " + s2.perimeter());
        System.out.println("Rectangle area: " + s3.area());
        System.out.println("Cylinder volume: " + s4.volume());
    }
}