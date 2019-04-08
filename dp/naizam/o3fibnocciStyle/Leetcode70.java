package naizam.o3fibnocciStyle;

/**
* You are climbing a stair case. It takes n steps to reach to the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Note: Given n will be a positive integer.

Example 1:

Input: 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
Example 2:

Input: 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
*/

public class Leetcode70{

    private int waysToClimb(int n){
        //as usual, i love the bottoms up approach.
        /*
        If we compare this to Fib, this is our intution.
        Number of ways to reach top = jump 1 step from n-1 or jump 2 steps from n-2
        Putting it to Fib like equation,
        ways(n) = ways(n-1)+ ways(n-2)

        I love bottoms up approach all the time.
        Lets do bottoms up.

        Base case building. 
        If n = 0, ways = 1 -> dont take any step
        If n=1, ways = 1 -> take only one step from 0
        if n=2, ways = 2 -> take 2 one steps or take 1 2 step.

        */
        if(n==0 || n==1){
            return 1;
        }
        if(n==2){
            return 2;
        }
        int[] dp = new int[2];
        dp[0]=1;
        dp[1]=2;
        for(int i=3; i<=n; i++){
            if(i%2 == 0){ //even number
                dp[1] = dp[0]+ dp[1];
            } 
            else{ //odd number
                dp[0] = dp[0]+ dp[1];
            }
        }
        return n%2 ==0 ? dp[1]: dp[0];
    }
    public static void main(String[] args){
        Leetcode70 instance = new Leetcode70();
        System.out.println(instance.waysToClimb(2));
        System.out.println(instance.waysToClimb(3));
        System.out.println(instance.waysToClimb(10));
    }
}