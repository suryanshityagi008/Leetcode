class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Integer.compare(a[0], b[0]);
                return Integer.compare(a[1], b[1]);
            }
        );

        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Integer.compare(a[0], b[0]);
                return Integer.compare(a[1], b[1]);
            }
        );

        int n = servers.length;
        int m = tasks.length;

        int[] ans = new int[m];

        // Add all servers to free queue
        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        long time = 0;

        for (int i = 0; i < m; i++) {

            // Update current time
            time = Math.max(time, i);

            // Release completed servers
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                int[] s = busy.poll();
                free.offer(new int[]{s[1], s[2]});
            }

            // If no server is free, wait
            if (free.isEmpty()) {

                time = busy.peek()[0];

                // Release all servers free at this time
                while (!busy.isEmpty() && busy.peek()[0] <= time) {
                    int[] s = busy.poll();
                    free.offer(new int[]{s[1], s[2]});
                }
            }

            // Select the best free server
            int[] server = free.poll();

            ans[i] = server[1];

            // Assign task and calculate finishing time
            busy.offer(new int[]{
                (int)(time + tasks[i]),
                server[0],
                server[1]
            });
        }

        return ans;
    }
}
        
    