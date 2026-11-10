class Solution {
    public int climbStairs(int n, int[] costs) {

        int[] dp = new int[n + 1];

        // Starting point
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {

            dp[i] = Integer.MAX_VALUE;

            // Check previous 1, 2 and 3 steps
            for (int j = 1; j <= 3; j++) {

                int prev = i - j;

                if (prev >= 0) {

                    int jumpCost = j * j;

                    int totalCost = dp[prev]
                                  + costs[i - 1]
                                  + jumpCost;

                    dp[i] = Math.min(dp[i], totalCost);
                }
            }
        }

        return dp[n];
    }
}

        
