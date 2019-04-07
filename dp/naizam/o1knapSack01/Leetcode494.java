package naizam.o1knapSack01;

import java.util.Arrays;

/*
You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.

Find out how many ways to assign symbols to make sum of integers equal to target S.

Example 1:
Input: nums is [1, 1, 1, 1, 1], S is 3. 
Output: 5
Explanation: 

-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

There are 5 ways to assign symbols to make the sum of nums be target 3.
*/

public class Leetcode494{

    private int countWays(int[] nums, int target){
        /**
         * This is little tricky. But this can be mapped to knapsack01 subset problem.
         * Lets say, one of our solution is +1+1+1+1-1 =3
         * Which is (1+1+1+1) - (1) =3
         * That is now, count the number of subsets S1 and S2 whose difference is target.
         * sum(S1) - sum(S2) = target
         * sum(S1) + sum(S2) = sum(nums)
         * 
         * or,
         * 2*sum(S1) = target+sum(nums)
         * 
         * Or, now we simply say,
         * sum(S1) = (target+sum(nums))/2
         * Or,
         * Find the number of subsets whose sum is (target+sum(nums))/2
         * This can be solved with Knapsack0/1 approach.
         * In solution to 416 we kept a boolean dp array. But here we will keep a int dp array to track 
         * the total number of subsets instead of just checking if a subset is possible or not.
         */

         /*
         This solution assumes you have only posisitve numbers in the array. What happens if there is
         0 in the array? For you to fix.
         */

         if(nums == null || nums.length ==0){
             return 0;
         }

         int sum = 0;
         for(int a: nums){
             sum+= a;
         }

         if(target>sum){ //we are never going to reach there.
            return 0;
         }

         int subarraySum = sum+target;
         if(subarraySum %2 ==1){
            return 0; //it is impossible to create a subarray
         }
         subarraySum = subarraySum/2;

         int[][] dp = new int [nums.length][subarraySum+1];

         //for target 0, we can always create a empty subarray. Fill first col with 1s.
         for(int i=0; i<nums.length; i++){
             dp[i][0] = 1;
         }
         //for the first element in nums, if it is lessthan or equal to sum we are looking for, set
         //count =1
         if(nums[0]<= subarraySum){
             dp[0][nums[0]] = 1;
         }

         for(int i=1; i< nums.length; i++){
             for (int j=1; j<= subarraySum; j++){
                //dp[i][j] is,
                   // if nums[i]> currentTarget -> The possible ways is the possible ways excluding
                   //         current element. That is, dp[i-1][j]
                   if(nums[i]> j){
                       dp[i][j] = dp[i-1][j];
                   }
                   else{
                       //answer is, num of ways without including + num of ways with incluing current element
                       // num of ways with incluing current element = nums of ways of  target-nums[i] 
                       dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i]];
                   }
             }
         }
         //print2dArray(dp);
        return dp[nums.length-1][subarraySum];
    }

    private void print2dArray(int [][] array){
        for(int[] row: array){
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main (String[] args){
        Leetcode494 instance = new Leetcode494();
        int [] input = {1, 1, 1, 1, 1};
        int target = 3;
        System.out.println(instance.countWays(input, target));
    }
}