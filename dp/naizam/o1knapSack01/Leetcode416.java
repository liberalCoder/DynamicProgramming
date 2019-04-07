package naizam.o1knapSack01;

/**
 * Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
Note:
Each of the array element will not exceed 100.
The array size will not exceed 200.
 
Example 1:

Input: [1, 5, 11, 5]

Output: true

Explanation: The array can be partitioned as [1, 5, 5] and [11].

Example 2:

Input: [1, 2, 3, 5]

Output: false

Explanation: The array cannot be partitioned into equal sum subsets.
 */

public class Leetcode416{

    private boolean canPartition(int[] input){
        if(input == null || input.length ==0){
            return false;
        }
        /*
        If you think about this,
        what we are trying to find out is if we can make a subarray from the given array whose
        sum is totalSum/2
        If we can do tht the we can say that we can split the array in two equal sums.
        So this becomes a knapsack problem whose sum=S/2.
        We can use the same logic as we did in the Introduction.
        Lets go straight in to bottoms up approach.
        We need an array with rows = number of elements in the array.
        Cols = 0-S/2.
        What do we store in the array? We just need to return a boolean to see if it is feasible to 
        create a subarray. So lets build a boolean array.
        Base building -> Since we can build a subarray of sum 0 with an empty array, col1 is all T
        input[0] can be made by using just that element. So in the first row set T form sums=input[0]

                    sums    0   1   2   3   4   5
            inputs  1       T   T   F   F   F   F
                    1,2     T   
                    1,2,3   T
                    1,2,3,5 T
        
        Building dp[1,1]. We need to see if we can make a subarray of sum 1 with numbers 1 and 2.
        If we can make a subarray of sum 1 with just number 1 (previous row), we can do it with 1 and 2 as well.
        Building dp[1,2]. Look up the row as in previous case if it is true we are good.
                          Here it is F and we need to check if adding the new number 2, we can get sum 2.
                          That will be true, if we use 2 and 2-0 column in previous row is T.
        
                    sums    0   1   2   3   4   5
            inputs  1       T   T   F   F   F   F
                    1,2     T   T   T and so on
                    1,2,3   T
                    1,2,3,5 T
        
        We can stop building as soon as we get a T for the last col. If we dont see a T in the last col,
        we return false;

        */
        int sum = 0;
        for(int i=0; i<input.length; i++){
            sum = sum + input[i];
        }
        if(sum%2 ==1){
            //we can not divide an odd number equally
            return false;
        }

        sum = sum/2;
        //lets try the space otimized solution here. I explained on why we just need two rows, in the introduction
        //problem.
        //at any point we are only looking at previous row to populate current row.

        boolean [][] dp = new boolean[2][sum +1];
        dp[0][0] = true;
        dp[1][0] = true;
        if(input[0] == sum){  //lottery, first element is what we are looking for
            return true;
        }
        else if(input[0]<sum){ // first element is smaqller than our targetted sum. So make tht col true
            dp[0][input[0]] = true;
        }

        //start building the dp array.
        //if we are building odd rows, we populate second row in dp array.
        //if we are building even rows, we populate first row in dp array.
        for(int i=1; i<input.length; i++){
            for (int j=1; j <= sum; j++){
                if(i%2 == 0){
                    //we are in even row. populate first row. refer second row
                    if(j-input[i] >=0){
                        dp[0][j] = dp[1][j] || dp[1][j-input[i]];
                    }
                    else{
                        dp[0][j] = dp[1][j];
                    }
                }
                else{
                    //we are in odd row. populate second row. refer first row
                    if(j-input[i] >=0){
                        dp[1][j] = dp[0][j] || dp[0][j-input[i]];
                    }
                    else{
                        dp[1][j] = dp[0][j];
                    }
                }
                if(j== sum && (dp[0][j] || dp[1][j])){
                    //we already found a subarray which sums to S/2.
                    return true;
                }
            }
        }
        return false;
    }

    public static void main (String [] args){
        Leetcode416 solution = new Leetcode416();
        int [] input1 = {1, 2, 3, 4};
        System.out.println(solution.canPartition(input1));
        int [] input2 = {1, 5, 11, 5};
        System.out.println(solution.canPartition(input2));
        int [] input3 = {90, 17, 3, 80, 78};
        System.out.println(solution.canPartition(input3));
    }
}