Approach 1 : Two Pointers
Approach 2 : Improved Binary Search
        *** Intuition ***
        Lets start with the straightforward approach. If we put the elements of two arrays in one array A and arrange them in order.
        Assume the merged arrays has a length of n, then the median is:
        - A[n / 2], if n is odd.
        - The average of A[n / 2] and A[n / 2 + 1], if n is even.

        However, we do not really need to merge and sort these arrays. Note that both arrays are already sorted, so the smallest element is either
        the first element of nums1 or the first element of nums2. Therefore, we can set two pointers p1 and p2 at the start of each array,
        then we can get the smallest element from the nums1 and nums2 by comparing the values nums1[p1] and nums2[p2].

*** Algorithm ***
1. Get the total size of two arrays m + n
        If m + n is odd, we are looking for the (m + n) / 2-th element.
        If m + n is even, we are looking for the average of the (m + n) / 2-th and the (m + n) / 2 + 1-th elements.
2. Set two pointers p1 and p2 at the beginning of arrays nums1 and nums2.
3. If both p1 and p2 are in bounds of the arrays, compare the values at p1 and p2:
        If nums1[p1] is smaller than nums2[p2], we move p1 one place to the right.
        Otherwise, we move p2 one place to the right.
        If p1 is outside nums1, just move p2 one place to the right.
        If p2 is outside nums2, just move p1 one place to the right.
4. Get the target elements and calculate the median:

        If m + n is odd, repeat step 3 by (m + n + 1) / 2 times and return the element from the last step.
        If m + n is even, repeat step 3 by (m + n) / 2 + 1 times and return the average of the elements from the last two steps.


        *** Complexity Analysis ***
        Let m be the size of array nums1 and n be the size of array nums2.
        1. Time complexity: O(m+n) - We get the smallest element by comparing two values at p1 and p2, it takes O(1) to compare two elements and move the corresponding pointer to the right.
        We need to traverse half of the arrays before reaching the median element(s).
        To sum up, the time complexity is O(m+n).
        2. Space complexity: O(1) - We only need to maintain two pointers p1 and p2.

----------------------------------------------------------------------------------------------------------------------------------------
Approach 2 : Improved Binary Search

*** Intuition ***
        Recall the previous approach where we perform a binary search over the 'merged' array consisting of nums1 and nums2,
        resulting in a time complexity of O(log(m*n)).
        We could further improve the algorithm by performing the binary search only on the smaller array of nums1 and nums2,
        thus the time complexity is reduced to O(log(min(m,n))).

        The main idea -  where we need to find a point of partition in both arrays such that the maximum of the smaller half
        is less than or equal to the minimum of the larger half.

        However, instead of partitioning over the merged arrays, we can only focus on partitioning the smaller array (lets call this array A).
        Suppose the partition index is partitionA, we specify that the smaller half contains (m + n + 1) / 2 elements,
        and we can use this feature to our advantage by directly making partitionB equal to (m + n + 1) / 2 - partitionA,
        thus the smaller halves of both arrays always contain a total of (m + n + 1) / 2 elements.

        The next step is to compare the edge cases.
        1. A = maxLeftA < = minRightA
        2. B = maxLeftB < = maxRightB

        If both maxLeftA <= minRightB and maxLeftB <= minRightA hold, it means that we have partitioned arrays at the correct place.

        The smaller half consists of two sections A_left and B_left
        THe larger half consists of two sections A_right and B_right
        We just need to find the maximum value from the smaller half as max(A[maxLeftA], B[maxLeftB]) and the minimum value from the larger half
        as min(A[minRightA], B[minRightB]).
        The median value depends on these four boundary values and the total length of the input arrays and we can compute it by situation.

        If maxLeftA > minRightB, it implies that maxLeftA is too large to be in the smaller half and we should look for a smaller partition value of A.
        If maxLeftB > minRightA , it implies that minRightA is too small to be in the larger half and look for a larger partition of A.

        **** Algorithm ****
        1. Assuming nums1 to be the smaller array (If nums2 is smaller, we can swap them). Let m, n represent the size of nums1 and nums2, respectively.
        2. Define the search space for the partitioning index partitionA by setting boundaries as left = 0 and right = m.
        3. While left <= right holds, do the following.
        4. Compute the partition index of nums1 as partitionA = (left + right) / 2.
        Consequently, the partition index of nums2 is (m + n + 1) / 2 - partitionA.

        5. Obtain the edge elements:
        ~Determine the maximum value of the section A_left as maxLeftA = nums1[partitionA - 1]. If partitionA - 1 < 0, set it as maxLeftA = float(-inf).
        ~Determine the minimum value of the section A_right as minRightA = nums1[partitionA]. If partitionA >= m, set it as minRightA = float(inf).
        ~Determine the maximum value of the section B_left as maxLeftB = nums2[partitionB - 1]. If partitionB - 1 < 0, set it as maxLeftB = float(-inf).
        ~Determine the maximum value of the section B_right as minRightB = nums2[partitionB]. If partitionB >= n, set it as minRightB = float(inf).

        6. Compare and recalculate: Compare maxLeftA with minRightB and maxLeftB with minRightA.
        If maxLeftA > minRightB, it means the maxLeftA is too large to be in the smaller half, so we update right = partitionA - 1 to move to the left half of the search space.
        If maxLeftB > minRightA, it means that we are too far on the left side for partitionA and we need to go to the right half of the search space by updating left = partitionA + 1.
        Repeat step 4.

        7.When both maxLeftA <= minRightB and maxLeftB <= minRightA are true:
        If (m + n) % 2 = 0, the median value is the average of the maximum value of the smaller half and the minimum value of the larger half,
        given by answer = (max(maxLeftA, maxLeftB) + min(minRightA, minRightB)) / 2.
        Otherwise, the median value is the maximum value of the smaller half, given by answer = max(maxLeftA, maxLeftB).


        *** Complexity Analysis ***
        Let m be the size of array nums1 and n be the size of array nums2.

        1. Time complexity: O(log(min(m,n))) - We perform a binary search over the smaller array of size min(m,n).
        2. Space complexity: O(1) - The algorithm only requires a constant amount of additional space to store and update a
        few parameters during the binary search.

*** Implementation ***

class Solution{

    public double findMedianSortedArrays(int[] nums1, int[] nums2){
        if(nums1.length > nums2.length)
            return findMedianSortedArrays(nums2, nums1);

        int m = nums1.length;
        int n = nums2.length;
        int left = 0;
        int right = m;

    while(left < = right)
    {
        int partitionA = (left + right)/2;
        int partitionB = (m+n+1)/2 - partitionA;
        int maxLeftA = (partitionA == 0)? Integer.MIN_VALUE : nums1[partitionA-1];
        int minRightA = (partitionA == m)? Integer.MAX_VALUE : nums1[partitionA];
        int maxLeftB = (partitionB == 0)? Integer.MIN_VALUE : nums2[partitionB - 1];
        int minRightB = (partitionB == n) ? Integer.MAX_VALUE : nums2[partitionB];

        if (maxLeftA <= minRightB && maxLeftB <= minRightA){
            if ((m + n) % 2 == 0) { //sum of elements is even
                return ((Math.max(maxLeftA, maxLeftB) + Math.min(minRightA, minRightB)) / 2.0);
            }
            else { //odd
                return Math.max(maxLeftA, maxLeftB);
            }
        } //binary search - increasing/descreasing size of partition A
        else if (maxLeftA > minRightB) { //if the conditions dont match
            right = partitionA - 1; //
        }
        else {
            left = partitionA + 1;
        }
    }
    return 0.0;
}