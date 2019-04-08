package naizam.o3fibnocciStyle;

/**
* Let's tweak the previous question to allow user to take
  1 or 2 or 3 steps at a time. How does your logic and answer change?
*/

public class Leetcode70Sample2 {

    private int waysToClimb(int n) {
        // as usual, i love the bottoms up approach.
        /*
         * Applying the same thought process as previous, we get
         * ways(n) = ways(n-1)+ ways(n-2)+ways(n-3)
         * 
         * Base cases
         * ways(0) = 1
         * ways(1) = 1
         * ways(2) = 2
         * ways(3) = 4
         * 
         */
        if (n == 0 || n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (n == 3) {
            return 4;
        }
        int[] dp = new int[3];
        dp[0] = 1;
        dp[1] = 2;
        dp[2] = 4;
        for (int i = 4; i <= n; i++) {
            if (i % 3 == 0) { // 6,9,12 etc
                dp[2] = dp[0] + dp[1]+ dp[2];
            } 
            else if(i%3 == 2){ // 5,8,11 etc
                dp[1] = dp[0] + dp[1]+ dp[2];
            }
            else{ //4,7,10 etc
                dp[0] = dp[0] + dp[1]+ dp[2];
            }
        }
        return n % 3 == 0 ? dp[2] : n% 3 == 2? dp[1] : dp[0];
    }

    public static void main(String[] args) {
        Leetcode70Sample2 instance = new Leetcode70Sample2();
        System.out.println(instance.waysToClimb(3));
        System.out.println(instance.waysToClimb(4));
        System.out.println(instance.waysToClimb(5));
    }
}