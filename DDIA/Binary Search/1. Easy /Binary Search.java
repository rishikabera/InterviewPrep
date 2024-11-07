Approach 1: Find the Exact Value
Approach 2: Find Upper bound
Approach 3: Find Lower bound

APPROACH 1:First, we define the search space using two boundary indexes, left and right, all possible indexes are within the
        inclusive range [left, right]. The next step is to find the 'pivot point', the middle index that divides the search space into two halves.
        We need to compare the value at the middle index nums[mid] with target, the purpose of this step is to cut one half that is
        guaranteed not to contain target.
        If nums[mid] = target, it means we find target, and the job is done! We can break the loop by returning mid.
        If nums[mid] < target, combined with the array is sorted, we know that all values in the left half are smaller than target, so we can safely cut this half by letting left = mid + 1.
        If nums[mid] > target, it means all values in the right half are larger than target and can be cut safely!

        Does this loop ever stop? Yes, take the following picture as an example, suppose we are searching over an array of size 1, in this case, left, right,
        and mid all stand for the only index in the array. In any of the three conditions, we trigger one of the break statements and stop the loop.

        *** Algorithm ***
        Initialize the boundaries of the search space as left = 0 and right = nums.size - 1.
        If there are elements in the range [left, right], we find the middle index mid = (left + right) / 2 and compare the middle value nums[mid] with target:
        If nums[mid] = target, return mid.
        If nums[mid] < target, let left = mid + 1 and repeat step 2.
        If nums[mid] > target, let right = mid - 1 and repeat step 2.
        We finish the loop without finding target, return -1.

        *** Complexity Analysis ***
        Let n be the size of the input array nums.
        1. Time complexity: O(logn): nums is divided into half each time. In the worst-case scenario, we need to cut nums until the range has no
        element, and it takes logarithmic time to reach this break condition.
        2. Space complexity: O(1) : During the loop, we only need to record three indexes, left, right, and mid, they take constant space.


*** Implementation ***
    class Solution {

    public int search(int[] nums, int target) {
        // Set the left and right boundaries
        int left =0;
        int right = nums.length-1;
        //under this condition
        while(left < = right)
        {
        //get the middle value and the middle index
            int mid = left + (right - left)/2;
            if(nums[mid] == target)
                return mid;
            else if(nums[mid] < target)
                left = mid+1;
            else right = mid-1;
        }
        // If we finish the search without finding target, return -1.
        return -1;
    }



}

