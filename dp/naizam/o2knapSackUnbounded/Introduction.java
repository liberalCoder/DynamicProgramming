package naizam.o2knapSackUnbounded;

import java.util.*;

/**
 * What is the problem? We are given an array of items with their profits and
 * another array of its associated weights. What do we need to do? We need to
 * maximize the profit within a given capacity. 
 * This sounds exactly like what we did for knapsack0/1. But the difference is,
 * We can use each item as many times as we want.
 * Hence this problem is called, Knapsack unbounded.
 */
/*
    Sample qn:- 
    Inputs:-
        Items: { Apple, Orange, Banana, Melon }
        Weights: { 2, 3, 1, 4 }
        Profits: { 4, 5, 3, 7 }
        Knapsack capacity: 5
    We are asked to find out the maximum profit within a capacity of 5
*/

public class Introduction{

    private int bruteForce(int[] values, int [] weights, int capacity, int start){
        /*
            Intuition:- 
            For each item, we have one choice.
            Pick the item or Drop the item.

            If we decide to drop the item, we need to find the maximum profit possible with the remaining
             item with the whole capacity.
            If we pick the item, we need to find the maximum profit possible with the all the items
             including current item with the new capacity, totalCapacity - pickedItem's weight. The difference
             with Knapsack 0/1 is here while calculating the remaining profit, we consider current item as well.
            
            Max of the above 2 is our answer.

            This is a typical recursion problem. Lets code
        */

        /*
          Time complexity -> 2^n since we find out all possible combinations for each element.
                          -> Draw a recusrion tree and count. We will see 2^n + 2^n -1 calls
          Space complexity -> n, maximum possible recursion stack size. DFS
        */

        //Recursion boundaries
        if(start >= weights.length){  //we reached end of array
            return 0;
        }
        if(capacity <= 0){    //we reached max capacity
            return 0; 
        }

        int profitWithout = 0;
        int profitWith =0;

        profitWithout = bruteForce(values, weights, capacity, start+1);

        int currentElementsWeight = weights[start];
        //if current element is heavier than our avialable capacity we would never be able to
        //accomodate it in our solution. So avoiding such recursions.

        //Note the only difference from knapsack 0/1 is the start index in the below recursion call.
        if(currentElementsWeight <= capacity){
            profitWith = values[start] + bruteForce(values, weights, capacity-currentElementsWeight, start);
        }

        return Math.max(profitWithout, profitWith);
    }

    private int topDown(int[] values, int [] weights, int capacity, int start, Map<String, Integer> memo){
        /*
            Why is this a candidate for dynamic program?
            If we draw the recusrion tree, we can see that there are possibilities that we will call
            the bruteforce method with same capacity and start. So memoization can save us some cost.
            In a top down dp, we start with a memoization table and store values which we already calcualted
            and make use of it.
            So what are the variables to our recursive function?
            capacity and start varies. We will build a map with this key and use it for memoization.
            We could use a 2-d array as well.
        */

        /*
          Time complexity -> O(N*C) We made sure that we do not recusrse for the same combination of
                        start and capacity, we can safely say that our worst case time is N*C
          Space complexity -> O(N*C), memoization cost.
        */
        String key = capacity + "#" + start;
        if(memo.containsKey(key)){
            return memo.get(key);
        }

        //Recursion boundaries
        if(start >= weights.length){  //we reached end of array
            return 0;
        }
        if(capacity <= 0){    //we reached max capacity
            return 0; 
        }

        int profitWithout = 0;
        int profitWith =0;

        profitWithout = bruteForce(values, weights, capacity, start+1);

        int currentElementsWeight = weights[start];
        //if current element is heavier than our avialable capacity we would never be able to
        //accomodate it in our solution. So avoiding such recursions.

        //The start index is the only difference between this problem and knapsack0/1
        if(currentElementsWeight <= capacity){
            profitWith = values[start] + bruteForce(values, weights, capacity-currentElementsWeight, start);
        }

        int result = Math.max(profitWithout, profitWith);
        memo.put(key, result);
        return result;
    }

    private int bottomsUp(int[] values, int [] weights, int capacity){
        /*
            What do we do in bottoms up approach?
            We first decide what are our variables here?
            From the above explanations we know that our variables are index of the items (to decide if
            we should pick it or not) and the capacity. Since we have two variables we would be using a 2d
            matrix to build bottoms up.
            rows -> 0 to values.length-1. 0 row means when only first item is avaialble.
            cols -> 0 to capacity. 0 col indicates, items selected when capacity is 0. We can select any item in this
                    case. So we prefill col0 with 0s.
            
                            capacity0 capacity1 capacity2 capacity3 capacity4 capacity5
                    item1   0
                    item2   0
                    item3   0
                    item4   0
            
            Filling first row is different than knapsack 0/1. In knapsack 0/1, we fill the first row
            easily because we know that only one possibility exists for values. But here since items 
            can be repeated, we need to fill first row like any other row.

            Now fill the matrice from 1,1 to end 1,C
            The logic is same as we did before.
                Can we pick item 2? (capacity< weight of item2)
                    No, we fill this cell as cell above it. Because cell above indicates max profit
                        for this weight without only item1.
                    Yes. we have two choices now. 
                        Should we pick 2? This depends on the fact if we will get max profit with or without
                        picking 2.
                        Lets say if we pick 2. Our total profit = profit[item2] + max profit (capacity-weight of 2) with items including 2.
                                                                = profit[item2] + dp[row][capacity-weightof2];
                        Now lets say we dont pick 2. Our profit is cell above us.
                        So we fill the current cell as max of above 2.

            Apply the same throughout and we get our complete matrix and matrix[i][j] is the answer we are looking at.
            We can backtrack on the matrix to find the items we picked by applyign the exact reverse logic.
        */

        /*
          Time complexity -> O(N*C) We build a n*c array
          Space complexity -> O(N*C), dp array cost. 
          If we observe clearly, we could further optimize
          the space. We only need to keep track of two rows to generate the array. So it will be enough to have two rows at any point.
          No!! Observe again. All we want is some previous value in the current row+ the current value in the previous row.
          So we ideally only need one row of space. Try optimizing your solution to use only space n.

          Tip:- For an 0/1 knapsack, you need space of 2 rows. But for unbounded knapsack, you need only
          space of one row. Think why!!
        */
        int [][] dp = new int[values.length][capacity+1];

        //fill first col for 0 weight
        for(int i=0; i<dp.length; i++){
            dp[i][0] = 0;
        }

        //fill all other cells
        for (int i=0; i<dp.length; i++){
            for(int j=1; j<dp[0].length; j++){
                int maXProfitWithout = 0;
                //fill with max profit from the row above. Max profit for the same weight
                //without including the current item
                if(i>0){ //boundary check for first row
                    maXProfitWithout = dp[i-1][j];
                }
                else{
                    maXProfitWithout = 0;
                } 
                if(weights[i]<= j){ //current item is more than capacity. We cant use it
                    int maxProfitWith = values[i] + dp[i][j-weights[i]];
                    dp[i][j] = Math.max(maxProfitWith, maXProfitWithout);
                }
                else{
                    dp[i][j] = maXProfitWithout;
                }
            }
        }

        int currentCapacity = capacity;
        //optionally, we can print selected items
        for(int i=weights.length-1; i>=0; i--){
            
            if(currentCapacity ==0){
                break;
            }

            int result = dp[i][currentCapacity];
            int prev = 0;
            if(i>0){
                prev = dp[i-1][currentCapacity];
            } 

            if(result == prev){
                //we did not select the current one
            }
            else{
                System.out.println("Selected "+ values[i]);
                currentCapacity = currentCapacity - weights[i];
                i++; //stay on the same row.
            }
        }
        
        return dp[weights.length-1][capacity];
    }

    public static void main (String[] args){
        int [] values = {120, 60, 100};
        int [] weights = {30, 10, 20};
        int maxCapacity = 50;

        Introduction solution = new Introduction();

        long startTime = System.currentTimeMillis();
        int result = solution.bruteForce(values, weights, maxCapacity, 0);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime-startTime;
        System.out.println("Solution brute force "+ result + " in "+ elapsedTime+" milliseconds");

        startTime = System.currentTimeMillis();
        Map<String, Integer> memo = new HashMap();
        result = solution.topDown(values, weights, maxCapacity, 0, memo);
        endTime = System.currentTimeMillis();
        elapsedTime = endTime-startTime;
        System.out.println("Solution topdown "+ result + " in "+ elapsedTime+" milliseconds");

        startTime = System.currentTimeMillis();
        result = solution.bottomsUp(values, weights, maxCapacity);
        endTime = System.currentTimeMillis();
        elapsedTime = endTime-startTime;
        System.out.println("Solution bottoms up "+ result + " in "+ elapsedTime+" milliseconds");
    }
}