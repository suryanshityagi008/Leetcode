class Solution {
    public long minOperations(int[] nums, int x, int k) {
        

        int n = nums.length;

        long[] cost = new long[n - x + 1];

        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        long sumL = 0, sumR = 0;
        int sizeL = 0, sizeR = 0;

        // Sliding window to calculate median costs
        for (int i = 0; i < n; i++) {

            int num = nums[i];

            if (left.isEmpty() || num <= left.lastKey()) {
                left.put(num, left.getOrDefault(num, 0) + 1);
                sumL += num;
                sizeL++;
            } else {
                right.put(num, right.getOrDefault(num, 0) + 1);
                sumR += num;
                sizeR++;
            }

            // Balance both halves
            while (sizeL > sizeR + 1) {
                int v = left.lastKey();
                remove(left, v);
                sumL -= v;
                sizeL--;

                right.put(v, right.getOrDefault(v, 0) + 1);
                sumR += v;
                sizeR++;
            }

            while (sizeL < sizeR) {
                int v = right.firstKey();
                remove(right, v);
                sumR -= v;
                sizeR--;

                left.put(v, left.getOrDefault(v, 0) + 1);
                sumL += v;
                sizeL++;
            }

            if (i >= x) {
                int old = nums[i - x];

                if (left.containsKey(old)) {
                    remove(left, old);
                    sumL -= old;
                    sizeL--;
                } else {
                    remove(right, old);
                    sumR -= old;
                    sizeR--;
                }

                while (sizeL > sizeR + 1) {
                    int v = left.lastKey();
                    remove(left, v);
                    sumL -= v;
                    sizeL--;

                    right.put(v, right.getOrDefault(v, 0) + 1);
                    sumR += v;
                    sizeR++;
                }

                while (sizeL < sizeR) {
                    int v = right.firstKey();
                    remove(right, v);
                    sumR -= v;
                    sizeR--;

                    left.put(v, left.getOrDefault(v, 0) + 1);
                    sumL += v;
                    sizeL++;
                }
            }

            if (i >= x - 1) {
                int median = left.lastKey();

                cost[i - x + 1] =
                    (long) median * sizeL - sumL
                    + sumR - (long) median * sizeR;
            }
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[n + 1][k + 1];

        for (long[] row : dp) {
            Arrays.fill(row, INF);
        }

        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {

                dp[i][j] = dp[i - 1][j];

                if (i >= x && dp[i - x][j - 1] != INF) {
                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - x][j - 1] + cost[i - x]
                    );
                }
            }
        }

        return dp[n][k];
    }

    private void remove(TreeMap<Integer, Integer> map, int num) {
        int count = map.get(num);

        if (count == 1) {
            map.remove(num);
        } else {
            map.put(num, count - 1);
        }
    }
}
    