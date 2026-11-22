class basic{
    public static void main(String[] args) {
//        int x = 5;
//        int y = x++ + ++x;
//
//        //System.out.println(x++);
//        System.out.println(x);
//        System.out.println(y);
//        for(int i = 0; i<5; i++) {
//            System.out.println(i++);
//        }

        Long v1 = new Long("42");
        Long v2 = new Long(42);
        Short v3  = new Short("42");
        System.out.println(v1.equals(v3));
        System.out.println(v1 == v2);
        System.out.println(v1.equals(v2));
        //System.out.println(v1 == v3);
    }
}