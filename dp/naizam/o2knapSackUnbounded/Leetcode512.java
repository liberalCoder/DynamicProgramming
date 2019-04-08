package naizam.o2knapSackUnbounded;
/**
* You are given coins of different denominations and a total amount of money. 
  Write a function to compute the number of combinations that make up that amount. 
  You may assume that you have infinite number of each kind of coin.
    Example 1:

    Input: amount = 5, coins = [1, 2, 5]
    Output: 4
    Explanation: there are four ways to make up the amount:
    5=5
    5=2+2+1
    5=2+1+1+1
    5=1+1+1+1+1
    Example 2:

    Input: amount = 3, coins = [2]
    Output: 0
    Explanation: the amount of 3 cannot be made up just with coins of 2.
    Example 3:

    Input: amount = 10, coins = [10] 
    Output: 1
 */
public class Leetcode512{
    private int getWays(int[] coins, int amount){

        /*
        lets use the space optimized bottoms up approach here.

        How do we fill the dp Array?
        First column is all 1. We can make amount 0 in one way, without using any coins at all.
        Filling first row.
            if(coins[j]> j) -> coin is greater than sum, we can not make a valid. Fill 0.
            if(coins[j] == j) -> there is 1 way to make it. 
            for others fill, as below
        Filling second row.
           if(coins[i]> amount) -> number of ways is number of ways excluding this coin.
           else{
               number of total ways = number of ways with current + number of ways without it.
               number of ways without it = look the above cell.
               number of ways with it = number of ways of amount j-coins[i] including this coin
                                      = dp[i][j-coins[i]]
           }
                0   1   2   3   4   5
           2    1   0   1   0   2   0
           1    1   1   2
           5    1
        */
        /*
          Can you think about optimizing it further down?
          Yes you can. You just need one row. Because all you are referring is a previous position 
          in the current row and the current value. Look at the second implementation and how cool it is.
          Copied from Leetcode.
        */
        int [][] dp = new int [2][amount +1];
        //fill first column
        dp[0][0] = 1;
        dp[1][0] = 1;

        for(int i=0; i<coins.length; i++){
            for(int j=1; j<=amount; j++){
                if(i==0){ //filling first row
                    if(j%coins[i] == 0){ //only for the multiples of coin we have a way.
                        dp[0][j] = 1;
                    }
                    else{
                        dp[0][j] = 0;
                    }
                }
                else{
                    
                    int currentRow = 0; //filling even row, refer to row below
                    int prevRow = 1;
                    if(i%2 ==1){ //filling odd rows. Refer to row above
                        currentRow = 1;
                        prevRow = 0;
                    }
                    int without = dp[prevRow][j];
                    int with = 0;
                    if(coins[i]<= j){
                        with = dp[currentRow][j-coins[i]];
                    }
                    dp[currentRow][j] = with+ without;
                }
            }
        }

        return coins.length%2 == 0? dp[1][amount] : dp[0][amount];
    }

    /*
      Super optimized from Leetcode.
    */
    private int getWays2(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for(int coin: coins){
            for(int i = 0; i <= amount; i++){
                if(i >= coin){
                    dp[i] += dp[i-coin];
                }         
            }    
        }
        return dp[amount];
    }
    public static void main (String [] args){
        Leetcode512 solution = new Leetcode512();
        int [] input1 = {1, 2, 5};
        int amount1 = 5;
        System.out.println(solution.getWays(input1, amount1));
        int [] input2 = {2};
        int amount2 = 3;
        System.out.println(solution.getWays(input2, amount2));
        int [] input3 = {10};
        int amount3 = 10;
        System.out.println(solution.getWays(input3, amount3));
    }
}