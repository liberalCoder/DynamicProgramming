package naizam.o3fibnocciStyle;

/**
* 
Given a number ‘n’, implement a method to count how many possible ways there are to express ‘n’ as the sum of 1, 3, or 4.

Example 1:

n : 4
Number of ways = 4
Explanation: Following are the four ways we can exoress 'n' : {1,1,1,1}, {1,3}, {3,1}, {4} 
Example 2:

n : 5
Number of ways = 6
Explanation: Following are the six ways we can express 'n' : {1,1,1,1,1}, {1,1,3}, {1,3,1}, {3,1,1}, 
{1,4}, {4}
n : 5
Number of ways = 6
Explanation: Following are the six ways we can express 'n' : {1,1,1,1,1}, {1,1,3}, {1,3,1}, {3,1,1}, 
{1,4}, {4}
*/

public class NumberFactor {

    private int numberOfFactors(int n) {
        // as usual, i love the bottoms up approach.
        /*
         * Base cases:-
         * ways(1) -> 1
         * ways(2) -> 1
         * ways(3) -> 2
         * ways(4) -> 4
         * 
         * ways(n) = ways(n-1)+ ways(n-3)+ways(n-4)
         * 
         */
        if (n == 0 || n == 1 || n==2) {
            return 1;
        }
        if (n == 3) {
            return 2;
        }
        if (n == 4) {
            return 4;
        }
        int[] dp = new int[4];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;
        for (int i = 5; i <= n; i++) {
            if (i % 4 == 0) { // 8,12 etc
                dp[3] = dp[2] + dp[0] + dp[3];
            } else if (i % 4 == 1) { // 5,9 etc
                dp[0] = dp[3] + dp[1] + dp[0];
            } 
            else if (i % 4 == 2) { // 6,10 etc
                dp[1] = dp[0] + dp[2] + dp[1];
            } 
            else { // 7,11 etc
                dp[2] = dp[1] + dp[3] + dp[2];
            }
        }
        return Math.max(dp[0], Math.max(dp[1], Math.max(dp[2], dp[3])));
    }

    public static void main(String[] args) {
        NumberFactor instance = new NumberFactor();
        System.out.println(instance.numberOfFactors(4));
        System.out.println(instance.numberOfFactors(5));
        System.out.println(instance.numberOfFactors(6));
    }
}