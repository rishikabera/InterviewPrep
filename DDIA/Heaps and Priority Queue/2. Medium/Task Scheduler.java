All approaches use a greedy strategy,meaning decisions are made step by step,
focusing on what seems best in the moment to reach the overall best solution.

Using proof by contradiction, we can demonstrate that selecting the task with the lowest frequency increases idle
time for the scheduler, thereby failing to maximize efficiency. Conversely, choosing tasks with higher frequencies maximizes efficiency.

APPROACH 1 : PRIORITY QUEUE/ MAX HEAP
APPROACH 2 : FILLING THE SLOTS AND SORTING
APPROACH 3 : GREEDY APPROACH
APPROACH 4 : USING MATH FORMULA

We will use the Greedy Approach - 3.
        *** Intuition ***
        The key is to determine the required number of idle intervals. Let us start by exploring how to arrange tasks.
        It is apparent that a "greedy arrangement" works well: always arrange tasks with the highest frequency first.
        The goal is to arrange tasks with the highest frequency first, ensuring that they are separated by at least n units of time.

        @@------ Step 1: Task Arrangement ------@@

        For instance, if tasks are ["A","A","A","B","B","C"] with n = 2, the initial arrangement would be:
        A _ _ A _ _ A ("_" denotes empty slots)
        The same approach can be applied to arrange B. The final schedule would look like this:
        A B _ A B _ A
        After arranging B tasks, we have 2 empty slots, but only one task remains. We can place task C and IDLE time in those slots.
        A B C A B _ A
        The final schedule could be: A B C A B _ A

        @@------ Step 2: Calculate Idle Intervals ------@@

        Now that we have a method for arranging tasks, the next step is to calculate the total number of idle intervals required. The solution to the problem is the sum of idle intervals and the number of tasks.
        Consider the same example of tasks: ["A","A","A","B","B","C"] with n = 2. After arranging A, we get:
        A _ _ A _ _ A
        Observe that A separates the empty slots into (count(A) - 1) = 2 parts, each with a length of n. A has the highest frequency, so it requires more idle intervals than any other task.
        To calculate parts, empty slots, and available tasks:
        Find the number of parts separated by A: partCount = count(A) - 1.
        Determine the number of empty slots: emptySlots = partCount * n.
        Identify the number of tasks to be placed into those slots: availableTasks = tasks.length - count(A).
        If emptySlots > availableTasks, indicating insufficient tasks to fill all empty slots, the remaining slots are filled with idle intervals: idles = max(0, emptySlots - availableTasks).

        Special Case:

        A special case arises when there is more than one task with the highest frequency. For instance, with ["A","A","A","B","B","B","C","C","D"] and n = 3, arranging A results in:
        A _ _ _ A _ _ _ A
        When arranging B, it becomes evident that each B must follow each A. Considering "A B" as a special task "X," the arrangement becomes:
        X _ _ X _ _ X

        In this case, the calculations for parts, empty slots, and available tasks are adjusted:
        partCount = count(A) - 1
        emptySlots = partCount * (n - (count of tasks with the highest frequency - 1))
        availableTasks = tasks.length - count(A) * count of tasks with the highest frequency
        If emptySlots is negative, it means there are already enough tasks to make the "distance" between the same tasks longer than n,
        and no idle intervals are needed. In this case, idles = max(0, emptySlots - availableTasks) provides the time it takes to complete
        the tasks.

        The final result is then calculated as result = tasks.length + idles.

        ************* ALGORITHM *************

        1. Initialize a counter array of size 26 to store the frequency of each task and variables maximum and maxCount to track the
        maximum frequency and the number of tasks with that frequency.
        2. Traverse through the tasks and update the counter array.
        If the frequency of a task is equal to the current maximum frequency, increment maxCount.
        3. If the frequency is greater than the current maximum frequency, update maximum and set maxCount to 1.
        4. Calculate the number of emptySlots by multiplying partCount (maximum - 1) and partLength (n - (maxCount - 1)).
        5. Calculate the number of availableTasks by subtracting the product of maximum and maxCount from the total number of tasks.
        6. Calculate the number of idles periods needed by taking the maximum of 0 and the difference between the number of
        emptySlots and the number of availableTasks.
        7. Return the total time required by adding the number of tasks to the number of idles periods.

        *********** IMPLEMENTATION ************
        class Solution{
                public int leastInterval(char tasks[], int n){
                        //counter array to store frequency
                        int counter[] = new int[26];
                        int maximumFreq = 0;
                        int maxCount = 0;

                        // Traverse through tasks to calculate task frequencies
                        for(char task : tasks)
                        {
                                counter[task - 'A']++;
                                if(maximumFreq == counter[task - 'A'])
                                        maxCount++;
                                else if (maximumFreq < counter[task - 'A'])
                                {
                                        maximumFreq = counter[task - 'A'];
                                        maxCount = 1;
                                }
                        }
                        // Calculate empty slots, available tasks, and idles needed
                        int partCount = maximumFreq - 1;
                        int partLength = n - (maxCount - 1);
                        int emptySlots = partLength * partCount;
                        int availableTasks = tasks.length - maximumFreq*maxCount;
                        int idles = Math.max(0, emptySlots - availableTasks);

                        return tasks.length + idles;

                }
        }


        *********** COMPLEXITY ANALYSIS ********
        Let N be the number of tasks.

        -------Time complexity: O(N)

        To obtain count(A) and the count of tasks with the highest frequency,
        we iterate through the inputs, calculating counts for each distinct character.
        This process has a time complexity of O(N). All other operations have a time complexity of O(1),
        resulting in an overall time complexity of O(N)

        ---------Space complexity: O(26) = O(1)

        The array count is size 26 because the tasks are represented by the letters A to Z.
        No data structures that vary with input size are used, resulting in an overall space complexity of O(1).