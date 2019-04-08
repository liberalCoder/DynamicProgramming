package naizam.o2knapSackUnbounded;

/**
 You are given coins of different denominations and a total amount of money amount. Write a function to compute the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

    Example 1:

    Input: coins = [1, 2, 5], amount = 11
    Output: 3 
    Explanation: 11 = 5 + 5 + 1
    Example 2:

    Input: coins = [2], amount = 3
    Output: -1
    Note:
    You may assume that you have an infinite number of each kind of coin.
 */
public class Leetcode322{

    private int getMinCount(int[] coins, int amount){

        /*
        We build a dp array of length = coins.length and width = amount +1
        The first column is all filled with 0 because we need 0 coins to get amount 0
        //Filling first row.
        if the amount < coins[i], we cant make a combo. So put Integer.MAX. 
        If amount = coins[i], we fill 1. go left applying the logic below.

        Filling second row.
            If not including the current one. Min number required is, value in the above cell.
            If including current, Min number required is 1+dp[i][j-wight[i]]
            Min of these two is our dp[i][j].

        Handle Integer.MAX_VALUE as a special case. If we find it in the look up col, we know that
        it was impossible to create a combo there.
        */

        // You can optimize this space to use only two rows and achieve O(N) instead of O(NC)

        int [][] dp = new int[coins.length][amount+1];

        //fill first col.
        for(int i=0; i<coins.length ; i++){
            dp[i][0] = 0;  //we dont need any coins to get to amount 0
        }

        for(int i =0; i< coins.length; i++){
            for (int j=1; j<= amount ; j++){
                int without = Integer.MAX_VALUE;
                if(i>0){ //handle for first row
                    without = dp[i-1][j];
                }

                int with = 0;
                if(coins[i]>j){ //coin is more than current amount, we cant make a combo
                    with = Integer.MAX_VALUE;
                }
                else{
                    int prev = dp[i][j-coins[i]];
                    if(prev == Integer.MAX_VALUE){ //we could not make a valid one with previous
                        with = Integer.MAX_VALUE;
                    }
                    else{
                        with = 1+ prev;
                    }
                }
                dp[i][j] = Math.min(with, without);
            }
        }

        return dp[coins.length-1][amount] == Integer.MAX_VALUE ? -1 : dp[coins.length-1][amount];
    }

    public static void main(String[] args){
        Leetcode322 solution = new Leetcode322();
        int [] input1 = {1, 2, 5};
        int amount1 = 11;
        System.out.println(solution.getMinCount(input1, amount1));
        int [] input2 = {2};
        int amount2 = 3;
        System.out.println(solution.getMinCount(input2, amount2));
    }
}