Approach 1 : Heap
Approach 2 : QuickSelect
Approach 3 : Bucket Sort O(n)

*** Approach 1 ***
        Let us start from the simple heap approach with O(Nlogk) time complexity.
        To ensure that O(Nlogk) is always less than O(NlogN), the particular case k=N could be considered separately and solved in O(N) time.

*** Algorithm ***

        1. The first step is to build a hash map-- element -> its frequency.
        In Java, we use the data structure HashMap. This step takes O(N) time where N is a number of elements in the list.

        2. The second step is to build a heap of size k using N elements. To add the first k elements takes a linear time O(k) in the average case,
        and O(log1+log2+...+logk)=O(logk!)=O(klogk) in the worst case. After the first k elements we start to push and pop at each step,
        N - k steps in total. The time complexity of heap push/pop is O(logk) and we do it N - k times which means O((N-k)logk) time complexity.
        Adding both parts up, we get O(Nlogk) time complexity for the second step.

        3. The third and last step is to convert the heap into an output array. That could be done in O(klogk) time.

*** Complexity Analysis ***

        Time complexity : O(Nlogk) if k<N and O(N) in the particular case of N=k. That ensures time complexity to be better than O(NlogN).
        Space complexity : O(N+k) to store the hash map with not more N elements and a heap with k elements.

*** Implementation ***

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // O(1) time
        if (k == nums.length) {
            return nums;
        }
        // 1. Build hash map: character and how often it appears
        // O(N) time
        Map<Integer, Integer> count = new HashMap();
        for (int n: nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }
        // init heap 'the less frequent element first'
        // min - heap
        Queue<Integer> heap = new PriorityQueue<>((n1, n2) -> count.get(n1) - count.get(n2));
        // 2. Keep k top frequent elements in the heap
        for (int n: count.keySet()) {
            heap.add(n);
            if (heap.size() > k) heap.poll();
        }
        // 3. Build an output array
        // O(k log k) time
        int[] top = new int[k];
        for(int i = k - 1; i >= 0; --i) {
            top[i] = heap.poll();
        }
        return top;
    }
}
-------------
 *** Approach 2: Quickselect (Hoare's selection algorithm) ***

        Quickselect is a textbook algorithm typically used to solve the problems "find kth something":
        kth smallest, kth largest, kth most frequent, kth less frequent, etc.
        Like quicksort, quickselect was developed by Tony Hoare and is also known as Hoare's selection algorithm.

        It has O(N) average time complexity and is widely used in practice. It is worth noting that its worst-case time complexity is O(N^2),
        although the probability of this worst-case is negligible.
        The approach is the same as for quicksort.

        One chooses a pivot and defines its position in a sorted array in a linear time using the so-called partition algorithm.

        As an output, we have an array where the pivot is in its perfect position in the ascending sorted array,
        sorted by the frequency. All elements on the left of the pivot are less frequent than the pivot, and all elements on the
        right are more frequent or have the same frequency.

        Hence the array is now split into two parts. If by chance our pivot element took N - kth final position, then k elements
        on the right are these top k frequent we're looking for. If not, we can choose one more pivot and place it in its perfect position.
        If that were a quicksort algorithm, one would have to process both parts of the array. That would result in O(NlogN) time complexity.
        In this case, there is no need to deal with both parts since one knows in which part to search for N - kth less frequent element,
        and that reduces the average time complexity to O(N).

        *** Lomuto's Partition Scheme ***

        There is a zoo of partition algorithms. The most simple one is Lomutos Partition Scheme, and so is what we will use in this article.
        Here is how it works: Move the pivot at the end of the array using swap.
        Set the pointer at the beginning of the array store_index = left.
        Iterate over the array and move all less frequent elements to the left swap(store_index, i).
        Move store_index one step to the right after each swap.
        Move the pivot to its final place, and return this index.


*** Complexity Analysis ***
        1. Time Complexity : O(N) in the average case, O(N^2) in the worst case
        In the worst case of constantly badly chosen pivots, the problem is not divided by half at each step,
        it becomes just one element less, which leads to O(N^2) time complexity.
        It happens, for example, if at each step you choose the pivot not randomly, but take the rightmost element.
        For the random pivot choice, the probability of having such a worst-case is negligibly small.

        2. Space complexity: up to O(N) to store hash map and array of unique elements.

--------------------------------------------------------------------------------------------------------

Approach 3 : Bucket Sort

public class Solution {
    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> count = new HashMap<>();
        List<Integer>[] freq = new List[nums.length + 1];

        for (int i = 0; i < freq.length; i++) {
            freq[i] = new ArrayList<>();
        }
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            freq[entry.getValue()].add(entry.getKey());
        }
        int[] res = new int[k];
        int index = 0;
        for (int i = freq.length - 1; i > 0 && index < k; i--) {
            for (int n : freq[i]) {
                res[index++] = n;
                if (index == k) {
                    return res;
                }
            }
        }
        return res;
}
}