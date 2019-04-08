package naizam.o3fibnocciStyle;

import java.util.*;

/**
 * What is the problem? 
 * In the previous two types, we saw that we are given two arrays, weight and profits and we are
 * told to figure out max profit.
 * But here, we are given an array and asked to figure out the best way to traverse the array given
 * multiple options to traverse from every element.
 * The other examples in the series will make this easy to understand. But before that, lets start
 * with the simple case of Fib series. Then we examine other qns and see a similarity and an equation
 * which looks similar to fib series.
 */
/*
    Sample qn:- 
    Print fib number n.
    We know that fib(n) = fib(n-1)+ fib(n-2)
*/

public class Introduction{

    private int bruteForce(int n){
        /*
            Intuition:- 
            Fib(n) = fib(n-1)+ fib(n-2)
            Boundary conditions.
            n=1 -> 1
            n=2 -> 1
            This is a typical recursion problem. Lets code
        */

        /*
          Time complexity -> 2^n since we find out all possible combinations for each element.
                          -> Draw a recusrion tree and count. We will see 2^n + 2^n -1 calls
          Space complexity -> n, maximum possible recursion stack size. DFS
        */

        //Recursion boundaries
        if(n==1){  //we reached end of array
            return 1;
        }
        if(n==2){  //we reached max capacity
            return 1; 
        }

        return bruteForce(n-1)+ bruteForce(n-2);
    }

    private int topDown(int n, int [] memo){
        /*
            we know that we are calling the same recursion multiple times. memoize it and we 
            reduce the time to O(n).
        */

        /*
          Time complexity -> O(N)
          Space complexity -> O(N), memoization cost.
        */
        //Recursion boundaries
        if(memo[n] != 0){
            return memo[n];
        }
        if(n==1 || n==2){
            return 1;
        }

        int result = topDown(n-1, memo) + topDown(n-2, memo);
        memo[n] = result;
        return result;
    }

    private int bottomsUp(int n){
        /*
            Simple striaght forward.
            dp[1]=1;
            dp[2]=1;
            dp[i]=dp[i-1]+dp[i-2]
        */

        /*
          Time complexity -> O(N) 
          Space complexity -> O(N), dp array cost. 
        */
        int []dp = new int[n+1];
        dp[1]=1;
        dp[2]=1;

        for(int i=3; i<=n; i++){
            dp[i] = dp[i-1]+dp[i-2];
        }

        return dp[n];
    }

    private int bottomsUp2(int n){
        /*
            Simple striaght forward.
            dp[1]=1;
            dp[2]=1;
            dp[i]=dp[i-1]+dp[i-2]
            But at any point we only use the previous 2 values. So why have the whole array?
            We can just optimize to use constnt space
        */

        /*
          Time complexity -> O(N) 
          Space complexity -> O(1). 
        */
        int []dp = new int[2];
        dp[0] = 1;
        dp[1] = 1;

        for(int i=3; i<=n; i++){
            if(i%2 == 1){  //odd number processing
                dp[0]= dp[0]+dp[1];
            }
            else{
                dp[1] = dp[0]+ dp[1];
            }
        }

        return dp[n%2];
    }

    public static void main (String[] args){

        Introduction solution = new Introduction();

        long startTime = System.currentTimeMillis();
        int result = solution.bruteForce(40);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime-startTime;
        System.out.println("Solution for 40 brute force "+ result + " in "+ elapsedTime+" milliseconds");

        startTime = System.currentTimeMillis();
        int[] memo = new int[4001];
        result = solution.topDown(4000, memo);
        endTime = System.currentTimeMillis();
        elapsedTime = endTime-startTime;
        System.out.println("Solution 4000 topdown "+ result + " in "+ elapsedTime+" milliseconds");

        startTime = System.currentTimeMillis();
        result = solution.bottomsUp(4000);
        endTime = System.currentTimeMillis();
        elapsedTime = endTime-startTime;
        System.out.println("Solution 4000 bottoms up "+ result + " in "+ elapsedTime+" milliseconds");

        startTime = System.currentTimeMillis();
        result = solution.bottomsUp2(4000);
        endTime = System.currentTimeMillis();
        elapsedTime = endTime-startTime;
        System.out.println("Solution 4000 bottoms up constant space "+ result + " in "+ elapsedTime+" milliseconds");
    }
}