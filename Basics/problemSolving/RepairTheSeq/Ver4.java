/*Level 4 — Removing a specified contiguous interval
Problem 4: Is this removal valid?

You are given:
        - An integer array.
- A starting index l.
        - An ending index r.
Determine whether removing the contiguous subarray from l through r makes the remaining array non-decreasing.

Assume at least one element must remain.

        Examples:
arr = [1, 3, 2, 5, 6]
l = 1, r = 2

Remaining: [1, 5, 6]
Output: true


arr = [1, 4, 2, 3, 5]
l = 2, r = 3

Remaining: [1, 4, 5]
Output: true


arr = [1, 4, 2, 3, 5]
l = 1, r = 2

Remaining: [1, 3, 5]
Output: true


arr = [1, 5, 2, 4, 3]
l = 2, r = 2

Remaining: [1, 5, 4, 3]
Output: false
*/
class Ver4{
    static Boolean repair(int[] input, int l, int r){
        int n = input.length;
        int i = 0;


        int start = 0;
        boolean flag = false;
        while(i<n){
           // System.out.println(start + " "+ next + " "+i);
            if((i>= l) && (i <= r)){

                i++;
              //  System.out.println("hi");
                continue;
            }
            if(!flag) {
                start = input[i];
                flag = true;
                continue;
            }
            if(start > input[i]) return false;
            start = input[i];
            i++;
        }
        return true;
    }
    public static void main(String[] args) {
        int[] input = {1, 3, 2, 4, 5};
        System.out.println(repair(input, 3,4));

    }
}