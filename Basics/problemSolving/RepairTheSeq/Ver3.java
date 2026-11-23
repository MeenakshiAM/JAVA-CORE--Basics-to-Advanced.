/*
Problem 3: Can one deletion fix the array?

Given an integer array, determine whether removing at most one element can make it non-decreasing.

You cannot rearrange the remaining elements.

Original:       3   4   2   3
Delete index:       X
Remaining:      3       2   3

Comparisons:    3 > 2  → violation
                2 < 3  → valid

                do check for this testcase...
 */

class Ver3{
        static Boolean repair(int[] input){
            int n = input.length;
            int count = 0;

            for(int i = 0; i<n-1; i++) {
                if(input[i] > input[i+1]) count++;
            }
            if(count > 1) return false;
            return true;
        }
        public static void main(String[] args) {
            int[] input = {3, 4, 2, 3};
            System.out.println(repair(input));

        }

}