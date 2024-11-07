Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
In other words, return true if one of s1's permutations is the substring of s2.
        Approach 1: Brute Force
        Approach 2: Using sorting:
        Approach 4: Using Array [Accepted]
        Approach 5: Sliding Window [Accepted]:
        Approach 6: Optimized Sliding Window [Accepted]:

Approach 1 : Brute Force
*** Algorithm ***
        The simplest method is to generate all the permutations of the short string and to check if the generated permutation is a
        substring of the longer string.
        In order to generate all the possible pairings, we make use of a function permute(string_1, string_2, current_index).
        This function creates all the possible permutations of the short string s1.
        To do so, permute takes the index of the current element current_index as one of the arguments. Then, it swaps the current element
        with every other element in the array, lying towards its right, so as to generate a new ordering of the array elements. After the
        swapping has been done, it makes another call to permute but this time with the index of the next element in the array. While
        returning back, we reverse the swapping done in the current function call.
        Thus, when we reach the end of the array, a new ordering of the array's elements is generated. The following animation depicts the
        process of generating the permutations.



*** Complexity Analysis ***
        Let n be the length of s1

        Time complexity: O(n!). The permute method generates all possible permutations of the string s1. In a permutation problem,
        the number of ways to permute a string of length n is n!. Each recursive call swaps characters at different positions
        to explore every possible permutation at each level of recursion. At the first level, there are n choices for which character
        to place in the first position. At the second level, there are n−1 choices for which character to place in the second position,
        and so on, leading to n! total recursive calls.

        Space complexity: O(n^2). The depth of the recursion tree is n(n refers to the length of the short string s1).
        Every node of the recursion tree contains a string of max. length n.

*** Approach 2 : Sorting ***
        we can sort the two strings and compare them. We sort the short string s1 and all the substrings of s2, sort them and
        compare them with the sorted s1 string. If the two matches completely, s1's permutation is a substring of s2, otherwise not.

        *** Complexity Analysis ***

        Let l1 be the length of string s1 and l2 be the length of string s2.
        a. Time complexity: O(l2-l1) * l1log(l1)
        First, we sort s1 which takes O(l1logl1). Then, we iterate through a range of (l2-l1+1) and within the loop,
        we sort a substring of length l1. This process takes O((l2-l1+1))* l1logl1.
       me. Overall, we combine both time complexities: O((l2-l1)*l1log(l1))

        b. Space complexity:
        Some extra space is used when we sort an array of size n in place. The space complexity of the sorting algorithm (S)
        depends on the programming language. The value of S depends on the programming language and the sorting algorithm being used:
        In Java, Arrays.sort() is implemented using a variant of the Quick Sort algorithm which has a space complexity of O(logn)
        Thus, the total space complexity of the algorithm is O(l1+S).


*** Approach 4: Arrays
        We can use a simpler array data structure to store the frequencies.
        Given strings contains only lowercase alphabets ('a' to 'z'). So we need to take an array of size 26.
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;
        int[] s1arr = new int[26];
        for (int i = 0; i < s1.length(); i++)
            s1arr[s1.charAt(i) - 'a']++;
        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            int[] s2arr = new int[26];
            for (int j = 0; j < s1.length(); j++) {
                s2arr[s2.charAt(i + j) - 'a']++;
            }
            if (Arrays.equals(s1arr, s2arr))
                return true;
        }
        return false;
    }
}


*** Complexity Analysis ***
        Let l1 be the length of string s1  and l2 be the length of string s2.
    1. Time Complexity : O(l1 + (l1)*(l2-l1))
        The initialization of the array s1arr takes O(l1) since we loop through each character of s1 once and store the counts.
        The outer loop runs O(l2- l1 +1) times, as we need to consider each possible substring of length l1 within s2.
        For each iteration of the outer loop, we build s2arr which takes O(l1) time (since we process l1 characters for each substring in s2).
   2. Space complexity: O(l2-l1). Each substring from s2 of length l1 creates a array s2arr to store the character frequencies. The size of the array is O(26),
    since there are at most 26 characters in the alphabet. Over l2-l1 + 1 iterations of the outer loop, we create one such array per iteration
    resulting in O(26*(l2-l1+1)) space usage. For s1arr = O(26). Total simplified space O(l2-l1).



        *** Approach 5: Sliding Window ***

*** Algorithm ***
        Instead of building a new hashmap from scratch for every window we check in s2. we can just set up a fixed-size array of length 26 once
        for the first window in s2. Then, as we slide the window over, we can simply update it. Basically, we will remove the character
        that is no longer in the window and add the new one that is now part of it. So, the array gets tweaked only at the two spots
        related to those two characters. Each time we update the array, we just compare all the elements to
        check if everything matches up for the result we want.

*** Implementation ***
public class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length > s2.length)
            return false;

        int s1arr = new int[26];
        int s2arr = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            s1arr[s1.charAt(i) - 'a']++;
            s2arr[s2.charAt(i) - 'a']++;
        }
        for(int i =0;i <s2.length() - s1.length(); i++)
        {
            if(Arrays.equals(s1arr,s2arr))
                return true;
            s2arr[s2.charAt(i+s1.length())-'a']++;
            s2arr[s2.charAt(i)-'a']--;
        }
        return Arrays.equals(s1arr, s2arr);

    }
}
        *** Complexity Analysis ***
            Let l1 be the length of string s1 and l2 be the length of string s2

    Time complexity: O(l2)
    The loop that populates two frequency arrays runs for l1 iterations, as it processes all characters in s1 and
    the first l1 characters in s2 This step takes O(l1) time.
    The outer loop runs l2−l1 times, sliding the window of size l1 across s2
    For each iteration, two operations are performed:
    a. Increment the count of the new character added to the window
    b. Decrement the count of the character leaving the window
    Both of these operations are constant-time, O(1), for each iteration since the arrays are of fixed size (26). Thus,
    the time complexity for this part is O(l2−l1).

    Space complexity : Two arrays, s1arr and s2arr, are used to store character frequencies. Each array has a fixed size of 26, regardless of the lengths of s1
    and s2. Therefore, the space used for these arrays is O(26+26)=O(52)=O(1).
---------------------------------------------------------------------------------------
                *** Approach 6: Optimized Sliding Window ***

        *** Algorithm ***
    The last approach can be optimized, if instead of comparing all the elements of the s1arr for every updated s2arr
    corresponding to every window of s2 considered, we keep a track of the number of elements which were already matching
    in the s1arr and update just the count of matching elements when we shift the window towards the right.

    To do so, we maintain a count variable, which stores the number of characters(out of the 26 alphabets),
    which have the same frequency of occurence in s1 and the current window in s2. When we slide the window, if the deduction
    of the last element and the addition of the new element leads to a new frequency match of any of the characters, we increment the count by 1.
    If not, we keep the count intact. But, if a character whose frequency was the same earlier(prior to addition and removal) is added,
    it now leads to a frequency mismatch which is taken into account by decrementing the same count variable.
    If, after the shifting of the window, the count evaluates to 26, it means all the characters match in frequency totally. So,
    we return a True in that case immediately.



*** Implementation ***
public class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;
        int[] s1arr = new int[26];
        int[] s2arr = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            s1arr[s1.charAt(i) - 'a']++;
            s2arr[s2.charAt(i) - 'a']++;
        }

        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (s1arr[i] == s2arr[i])
                count++;
        }

        for (int i = 0; i < s2.length() - s1.length(); i++) {
            int r = s2.charAt(i + s1.length()) - 'a', l = s2.charAt(i) - 'a';
            if (count == 26)
                return true;
            s2arr[r]++;
            if (s2arr[r] == s1arr[r]) {
                count++;
            } else if (s2arr[r] == s1arr[r] + 1) {
                count--;
            }
            s2arr[l]--;
            if (s2arr[l] == s1arr[l]) {
                count++;
            } else if (s2arr[l] == s1arr[l] - 1) {
                count--;
            }
        }
        return count == 26;
    }

}

    *** Complexity Analysis ***
    Let l1 be the length of string s1 and l2 be the length of string s2.
    Time complexity: O(l1+(l2-l1))~~O(l2). Populating s1arr and s2arr takes O(l1) time since we iterate over the first l1 characters
    of both strings.The outer loop runs l2-l1 times. In each iteration, we update two characters
    (one entering and one leaving the window) in constant time O(1), and we maintain a count of matches. This step takes O(l2-l1).
    Checking if count == 26 also happens in O(1), since it’s a constant comparison.


    Space complexity: O(1): Two fixed-size arrays (s1arr and s2arr) of size 26 are used for counting character frequencies.
    No additional space that grows with the input size is used.
