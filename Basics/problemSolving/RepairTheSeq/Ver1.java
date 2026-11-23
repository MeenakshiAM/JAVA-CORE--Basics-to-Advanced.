class Ver1 { // check if the given array is decending or not
    static Boolean repair(int[] input){
        int n = input.length;

        for(int i = 1; i<n; i++) {
            if(input[i]< input[i-1]) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        int[] input = {2, 2, 7, 3, 4};
        System.out.println(repair(input));

    }
}