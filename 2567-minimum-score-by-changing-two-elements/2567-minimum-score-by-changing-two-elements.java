
import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {

        Arrays.sort(nums);

        int n = nums.length;

        int ans = Integer.MAX_VALUE;

        // Case 1: Change the two smallest elements
        ans = Math.min(ans, nums[n - 1] - nums[2]);

        // Case 2: Change the smallest and largest elements
        ans = Math.min(ans, nums[n - 2] - nums[1]);

        // Case 3: Change the two largest elements
        ans = Math.min(ans, nums[n - 3] - nums[0]);

        return ans;
    }
}
