/*
Problem 2: Count the breaks.

Given an integer array, count how many adjacent pairs violate non-decreasing order.

 */
class Ver2 {
    static int repair(int[] input){
        int n = input.length;
        int count = 0;

        for(int i = 0; i<n-1; i++) {
            if(input[i] > input[i+1]) count++;
        }
        return count;
    }
    public static void main(String[] args) {
        int[] input = {1,5,3,2,8,6};
        System.out.println(repair(input));

    }
}
/*
---------output--------
PS E:\E\CORE-JAVA\basics\problemsolving\repairtheseq> javac Ver2.java
PS E:\E\CORE-JAVA\basics\problemsolving\repairtheseq> java Ver2.java
3

 */