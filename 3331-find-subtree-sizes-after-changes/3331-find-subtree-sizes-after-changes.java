
import java.util.*;

class Solution {
    List<Integer>[] tree;
    List<Integer>[] newTree;
    int[] answer;
    int[] last;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        tree = new ArrayList[n];
        newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
            newTree[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        answer = new int[n];
        last = new int[26];
        Arrays.fill(last, -1);

        // Build modified tree
        dfsBuild(0, s);

        // Calculate subtree sizes
        dfsSize(0);

        return answer;
    }

    private void dfsBuild(int node, String s) {
        int ch = s.charAt(node) - 'a';

        int old = last[ch];
        last[ch] = node;

        for (int child : tree[node]) {
            int c = s.charAt(child) - 'a';

            if (last[c] != -1) {
                newTree[last[c]].add(child);
            } else {
                newTree[node].add(child);
            }

            dfsBuild(child, s);
        }

        last[ch] = old;
    }

    private int dfsSize(int node) {
        int size = 1;

        for (int child : newTree[node]) {
            size += dfsSize(child);
        }

        answer[node] = size;
        return size;
    }
}
