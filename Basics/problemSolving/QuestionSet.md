BROOO 😂 Then we're making a proper offline problem-solving set! No wasting your network time.

Here are 25 carefully selected problems, arranged from intermediate to challenging. These are not basic syntax exercises. They require you to discover patterns, make decisions, track state, optimise operations and reason about constraints.

Use Java. You can solve them on paper or in your IDE. Don't look up solutions before attempting them.

# Java problem-solving: 25-question challenge

25 problems

Intermediate–Hard

Arrays · Strings · Greedy · Logic

## Level 1 — Discover the pattern

Questions 1–5 · Build the reasoning, not just the code.

1\. Minimum removal to sort

Given an integer array, remove one contiguous subarray so that the remaining elements are non-decreasing. Find the minimum number of elements to remove.

Example: `[1,2,6,4,5,7]` → `1`

2\. Balance the frequencies

Given a string, find the minimum number of characters to delete so that every remaining distinct character occurs the same number of times.

Example: `"aaabbc"` → `1` (delete `c`, leaving frequencies 3 and 2? Actually, this is not balanced yet; find the true minimum.)

3\. First unique element after updates

You receive an array and a series of updates. Each update changes one element. After each update, report the first element that currently occurs exactly once. If none exists, report `-1`.

4\. Longest alternating sequence

Given an array, find the length of the longest contiguous subarray in which consecutive elements alternate between increasing and decreasing.

Example: `[1,5,3,8,2,4]` → `6`

5\. Smallest missing positive number

Given an unsorted array containing positive, negative and duplicate values, find the smallest positive integer that does not appear.

Example: `[3,4,-1,1]` → `2`

## Level 2 — Make better decisions

Questions 6–10 · Greedy reasoning and optimisation.

6\. Minimum platforms with a twist

Given train arrival and departure times, determine the minimum number of platforms required. A platform cannot accommodate two trains at overlapping times.

7\. Distribute tasks

You have tasks with different durations and `k` workers. Each worker must receive a contiguous section of tasks. Minimise the maximum workload assigned to any worker.

Example: `[10,20,30,40]`, `k = 2` → `60`

8\. Circular fuel journey

There are `n` fuel stations arranged in a circle. Each station provides some fuel, and travelling to the next station consumes some fuel.

Find a starting station from which you can complete the entire circle, or return `-1` if impossible.

9\. Minimum jumps with obstacles

An array represents the maximum distance you can jump from each position. Some positions are blocked and cannot be landed on. Find the minimum jumps needed to reach the final position.

10\. Schedule maximum activities

Given activities with start and end times, select the maximum number of non-overlapping activities.

Additional challenge: What changes if an activity ending at time `t` and another starting at time `t` are allowed together?

## Level 3 — Tracking and state

Questions 11–15 · Maintain information as you traverse.

11\. Longest substring with at most two distinct characters

Example: `"eceba"` → `3` (`"ece"`)

12\. Subarray with equal zeros and ones

Given a binary array, find the maximum length of a contiguous subarray containing equal numbers of zeros and ones.

Example: `[0,1,0,1,1,0]` → `6`

13\. Minimum window containing all characters

Given strings `s` and `t`, find the smallest substring of `s` containing every character of `t`, including duplicate occurrences.

Example: `s = "ADOBECODEBANC"`, `t = "ABC"` → `"BANC"`

14\. Detect a repeated pattern

Given an integer array, determine whether there are two non-overlapping contiguous sections with identical contents.

Example: `[1,2,3,1,2,3]` → `true`

15\. Longest consecutive sequence

Find the length of the longest sequence of consecutive integer values in an unsorted array. The values need not be adjacent in the original array.

Example: `[100,4,200,1,3,2]` → `4`

## Level 4 — More complex logic

Questions 16–20 · Combine multiple observations.

16\. Merge overlapping intervals

Given intervals, merge all overlapping intervals and return the resulting non-overlapping intervals.

Example: `[[1,3],[2,6],[8,10],[9,12]]`

Output: `[[1,6],[8,12]]`

17\. Minimum swaps to group elements

Given an array and a value `k`, find the minimum number of swaps required to bring all elements less than or equal to `k` together.

Example: `[2,1,5,6,3]`, `k = 3` → `1`

18\. Next greater element with a distance constraint

For each element, find the first greater element to its right, but only within the next `k` positions. Return `-1` if none exists.

19\. Find the celebrity

At a party of `n` people, a celebrity is known by everyone but knows nobody. You can query whether person `A` knows person `B`.

Find the celebrity using as few queries as possible.

20\. Maximum sum with a restriction

Find the maximum sum of a subsequence where no two selected elements are adjacent in the original array.

Example: `[3,2,7,10]` → `13`

## Level 5 — Challenge yourself

Questions 21–25 · Unfamiliar situations and deeper reasoning.

21\. Minimum number of operations

You have an array of positive integers. In one operation, you can decrement any element by one, but you cannot make it negative.

Find the minimum operations required to make all elements equal.

22\. Build the largest number

Given an array of non-negative integers, arrange them to form the largest possible number.

Example: `[3,30,34,5,9]` → `"9534330"`

23\. The disappearing numbers

An array contains integers from `1` to `n`, but some numbers are missing and some appear twice. Find all missing numbers without using another array proportional to `n`.

24\. Minimum cost to connect points

You are given `n` points on a 2D plane. Connecting points `i` and `j` costs their Manhattan distance:

\\[ |x_i-x_j|+|y_i-y_j| \\]

Find the minimum total cost required to connect every point, directly or indirectly.

25\. Design a limited cache

Design a cache with capacity `k`.

- `get(key)` returns the stored value or `-1`.
- `put(key,value)` inserts or updates an entry.
- When full, remove the least recently used entry.

Both operations should ideally take \\(O(1)\\) time.

# Your offline progress tracker

You can use this checklist while solving. Tick a question only when you can independently explain the logic.

Your progress

# 0/25

1\. Minimum removal to sort

2\. Balance the frequencies

3\. First unique element after updates

4\. Longest alternating sequence

5\. Smallest missing positive

6\. Minimum platforms

7\. Distribute tasks

8\. Circular fuel journey

9\. Minimum jumps with obstacles

10\. Schedule maximum activities

11\. Substring with at most two distinct characters

12\. Equal zeros and ones

13\. Minimum window substring

14\. Repeated non-overlapping pattern

15\. Longest consecutive sequence

16\. Merge intervals

17\. Minimum swaps to group elements

18\. Next greater element with distance

19\. Find the celebrity

20\. Maximum non-adjacent sum

21\. Minimum operations to equalise

22\. Largest possible number

23\. Find missing numbers in-place

24\. Minimum cost to connect points

25\. Least recently used cache

&#x20;Copy progress

## Rules for today's practice

1. First explain the problem in your own words.
2. Write your approach in plain English.
3. Identify variables and their purpose.
4. Dry-run your logic with an example.
5. Only then write Java.
6. Test edge cases.
7. Finally, consider optimisation.

Don't mistake a completed code submission for a mastered problem. If you needed a hint, mark it as assisted. That's useful information too.

One important note: Questions 2 and 21 are deliberately open to exploring multiple strategies. For Q2, minimise deletions by choosing a common positive frequency for the remaining characters; for Q21, consider the actual allowed operation before deriving a formula.

My recommendation: start with questions 1, 4, 7, 12 and 17. They will expose several different reasoning habits.

And when you get stuck, send me your approach, not just your code. Today, we're training the person designing the algorithm, not merely the person typing Java. 🔥