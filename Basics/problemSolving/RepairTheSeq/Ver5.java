class Ver4{
    static Boolean repair(int[] input, int l, int r){
        int n = input.length;
        int i = 0;
        int start = 0;
        while(i<n-1){
            if(i >= l && i <= r){
                i++;
                continue;
            }
            if(input[i] > input[i+1]) return false;
            i++;
        }
        return true;
    }
    static boolean find(int[] input) {
       int n = input.length;
       int i = 0;

       int s = -1, e =-1;

       while(i < n-1) {
           if(input[i] > input[i+1]  && s == -1) {
               s = i;
           }
           else  if(input[i] > input[i+1]  && s != -1) {
               e = i;
           }
            i++;
       }
       return repair(input, s,e);
   }
    public static void main(String[] args) {
        int[] input = {3,2,1};
        System.out.println(find(input));

    }
}