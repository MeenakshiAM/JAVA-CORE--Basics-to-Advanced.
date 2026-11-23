Music Teacher

A music teacher has developed a new technique to teach the students in which each unique node is identified by a lower character of English alphabet. In total there are 26 nodes in this technique from 'a' to 'z'. To teach a new song the music teacher first converts the song to string consisting of a to z then he breaks it into melodies. A melody is a collection of nodes in which the frequency of each node present in that melody is the same. He strictly teaches one melody in one day. You're given the song string and your task is to find and return the minimum number of days in which he can teach the whole song.

Input Specification:

input1: A string representing the song string.

Output Specification:

Return the minimum number of days in which he can teach the whole song.

Example 1:

input1: "abbaabcd"

Output: 2

Explanation:

Here the given song string is "abbaabcd" which can be broken down into two melodies:

1. The first melody is "abbaba" where the frequency of characters a and b is equal

to 3.

2. The second melody is "cd" where the count of frequency c and d is equal to 1.

Therefore, there are 2 melodies "abbaba" and "cd" which are required to teach the song "abbaabcd". Hence minimum 2 days is required to teach the song

Example 2:

input1: "abcd"

Output: 1

Explanation:

Here the given song string "abcd" which itself is a melody as frequency of each node is 1. Hence, 1 is returned as the output.