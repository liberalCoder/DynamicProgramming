package naizam.o1knapSack01;

/**
 * We can take a look at similar questions as Leetcode 416.
 * I am not answering any of these as you should be able to build these solutions based on answer to
 * 416.
 * 
 * Qn1:-
 * Given a set of positive numbers, determine if there exists a subset whose sum is equal to a given number ‘S’.

    Example 1:
    Input: {1, 2, 3, 7}, S=6
    Output: True
    The given set has a subset whose sum is '6': {1, 2, 3}

    This is a straight forward 416. Here instead of having target = sum/2, our target is S

 * Qn2:-
 * Given a set of positive numbers, partition the set into two subsets with a minimum difference between their subset sums.

    Example 1:
    Input: {1, 2, 3, 9}
    Output: 3
    Explanation: We can partition the given set into two subsets where minimum absolute difference 
    between the sum of numbers is '3'. Following are the two subsets: {1, 2, 3} & {9}.

    This is a variance of subset problem. We need to find out two subsets whose difference is minimum.
    That means, if we get two subsets with sum = s/2, our difference is 0.
    So we build dp array as we did for 416. If there is no true in the last column, we see if there is
    one true in the left column and so on. The difference can be easily calculated by multiplying
    the number of columns we backtracked to find the first true.

 * Qn3:-
 * Given a set of positive numbers, find the total number of subsets whose sum is equal to a given number ‘S’.

    Example 1:
    Input: {1, 1, 2, 3}, S=4
    Output: 3
    The given set has '3' subsets whose sum is '4': {1, 1, 2}, {1, 3}, {1, 3}
    Note that we have two similar sets {1, 3}, because we have two '1' in our input.
    This implementation is similar to 494's solution. Please refer there.

 */