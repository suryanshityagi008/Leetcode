
class Solution {

    public int minimumChanges(String s, int k) {

        int n = s.length();

        int[][] cost = new int[n][n];

        // Step 1: Calculate cost of every substring
        for (int len = 2; len <= n; len++) {

            for (int i = 0; i + len <= n; i++) {

                int j = i + len - 1;

                cost[i][j] = Integer.MAX_VALUE;

                // Try all valid divisors
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    // Check every group
                    for (int r = 0; r < d; r++) {

                        int left = i + r;

                        int right = i + r
                                + ((len - 1 - r) / d) * d;

                        while (left < right) {

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    cost[i][j] = Math.min(cost[i][j], changes);
                }
            }
        }

        // Step 2: DP
        int INF = 1000000;

        int[][] dp = new int[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            java.util.Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        // Divide string into exactly k parts
        for (int parts = 1; parts <= k; parts++) {

            for (int i = 2 * parts; i <= n; i++) {

                for (int j = 2 * (parts - 1); j <= i - 2; j++) {

                    dp[i][parts] = Math.min(
                        dp[i][parts],
                        dp[j][parts - 1] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}

