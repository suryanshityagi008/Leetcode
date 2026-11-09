class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        

        int n = arrival.length;

        int[] count = new int[k];

        // Available servers
        TreeSet<Integer> available = new TreeSet<>();

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        // {finish time, server id}
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        for (int i = 0; i < n; i++) {

            // Free servers whose requests are completed
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                int[] server = busy.poll();
                available.add(server[1]);
            }

            // If all servers are busy, drop request
            if (available.isEmpty()) {
                continue;
            }

            // Find server starting from i % k
            int serverId = i % k;

            Integer server = available.ceiling(serverId);

            // Wrap around if no server is found
            if (server == null) {
                server = available.first();
            }

            // Assign request
            available.remove(server);

            count[server]++;

            // Add server to busy queue
            busy.offer(new int[]{
                arrival[i] + load[i], server
            });
        }

        // Find maximum requests handled
        int max = 0;

        for (int c : count) {
            max = Math.max(max, c);
        }

        // Collect busiest servers
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                result.add(i);
            }
        }

        return result;
    }
}

        
    