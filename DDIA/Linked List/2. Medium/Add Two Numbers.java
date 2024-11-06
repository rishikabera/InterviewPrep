
**** Intuition ****
Keep track of the carry using a variable and simulate digits-by-digits sum starting from the head of list,which contains
the least-significant digit. we begin by summing the least-significant digits, which is the head of l1 and l2.
        Since each digit is in the range of 0...9, summing two digits may "overflow".
        For example 5+7=12. In this case, we set the current digit to 2 and bring over the carry=1 to the next iteration.
        carry must be either 0 or 1 because the largest possible sum of two digits (including the carry) is 9+9+1=19.

        The pseudocode is as following:

        1. Initialize current node to dummy head of the returning list.
        2. Initialize carry to 0.
        3. Loop through lists l1 and l2 until you reach both ends and carry is 0.
        4. Set x to node l1's value. If l1 has reached the end of l1, set to 0.
        Set y to node l2's value. If l2 has reached the end of l2, set to 0.
        5. Set sum=x+y+carry.
        6. Update carry=sum/10.
        7. Create a new node with the digit value of (sum mod 10) and set it to current node's next, then advance current node to next.
        8.Advance both l1 and l2.
        9.Return dummy head's next node.
        Note that we use a dummy head to simplify the code. Without a dummy head, you would have to write extra conditional statements to
        initialize the head's value.

        Take extra caution of the following cases:
        1. When one list is longer than the other.
        2. When one list is null, which means an empty list.
        3. The sum could have an extra carry of one at the end, which is easy to forget.

        *** Complexity Analysis ***

        Time complexity : O(max(m,n)). Assume that m and n represents the length of l1 and l2 respectively.
        Space complexity : O(1). The length of the new list is at most max(m,n)+1 However, we don't count the answer as part of the space complexity.


class Solution{

    public ListNode addTwoNumbers(ListNode l1, ListNode l2)
    {
        ListNode dummyHead = new ListNode(0);
        ListNode curr = dummyHead;
        int carry =0;
        while(l1!=null || l2!=null || carry!=0)
        {
            int x = (l1!=null)?l1.val : 0;
            int y = (l2!=null)?l2.val : 0;
            int sum = carry + x + y;
            int carry = sum/10;
            curr.next =  new ListNode(sum % 10);
            curr = curr.next;
            if(l1!=null) l1 = l1.next;
            if(l2!= null) l2 = l2.next;
        }
        return dummyHead.next;
    }
}