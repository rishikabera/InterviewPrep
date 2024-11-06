/*

1. Approach 1: Maintain Sorted List
2. Approach 2: Heap

**** Intuition *****
        In Approach 1, sorting the entire stream of numbers seems unnecessary because we only need the k-th largest element.
        Maintaining a sorted list becomes costly as its size increases. To optimize, we can focus on only the necessary elements for
        retrieving and updating the k-th largest element.

        Consider a stream of numbers [0, 4, 6, 9] where k = 3 and incoming val = 2. Before adding 2, the k-th largest element is 4.
        Adding 2 does not affect 4's position since 2 is smaller. Now, if the incoming value is 7, which is greater than both 4 and 6,
        7 would become the 2nd largest number, pushing 6 to be the new k-th largest element, and 4 is no longer in the top k.
        From this example, we see that keeping track of just the k largest elements allows us to efficiently maintain the k-th largest element:

        1. If an incoming element val is smaller than or equal to the existing k-th largest element:
        The k largest elements remain unchanged, and we can return the current k-th largest element.
        2. If val is larger than the current k-th largest element: It replaces the current k-th largest element.
        After adding val, the new k-th largest element is the next largest element.

        To efficiently maintain the k largest elements, we use a min-heap.
        In a min-heap, elements are organized such that the smallest element is always at the top (root node), providing O(1) access time.
        Adding elements and removing the top element from the min-heap can be done in O(logn) time.

        We use a min-heap data structure. The idea is to maintain a heap of size k such that the
        smallest element in the heap is always the kth largest element in the stream.

        For our problem, the min-heap will contain the k largest elements, with the k-th largest element at the top. If a new val
        is greater than the k-th largest element, we add val to the heap and remove the top element, keeping the heap size at k and updating
        the k-th largest element.

        In our optimized approach, we initialize the min-heap with the initial stream nums in the constructor and ensure it
        contains only the k largest elements. In the add(int val) function, if val is smaller than the current k-th largest element and
        the heap already contains k elements, we return the top element. Otherwise, we add val,
        remove the top element if the heap size exceeds k, and return the updated top element.

        This approach is more efficient in both time and space complexity compared to maintaining a fully sorted list,
        as the relaxed ordering of a heap allows quick access and updates to the k largest elements without the overhead of sorting
        the entire stream.
*/

        class KthLargest{

        PriorityQueue<int> pq;
        int k;

        public KthLargest(int k, int[] nums)
        {
            pq = new PriorityQueue<>();
            this.k = k;
            for(int a : nums)
                add(a);
        }

        public int add(int val)
        {
            //add to the pq if we haven't processed k elements yet or if val is greater than the top element (kth largest)
            if(pq.size() < k || pq.peek() < val){
                pq.add(val);
                if(pq.size() > k){
                    pq.remove();
                }
            }
            return pq.peek();
        }
        }



/*
        *** Complexity Analysis ***

        Let M be the size of the initial stream nums given in the constructor, and let N be the number of calls to add.

        ----------Time Complexity: O((M+N)*logk)

        The add function involves adding and removing an element from a heap of size k, which is an O(logk) operation. Since the add function
        is called N times, the total time complexity for all add calls is O(N*logk).
        The constructor also calls add M times to initialize the heap, leading to a time complexity of O(M*logk).
        Therefore, the overall time complexity is O((M+N)*logk).

        ------------Space Complexity: O(k)

        The minHeap maintains at most k elements, so the space complexity is O(k).


 */