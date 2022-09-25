### The Algorithm
My algorithm generally asks the data whether or not a similar person has seen the same movie.
Instead of working to understand the movies (which, given only numbers, is impossible), it decides to look at similar users.
If no one else has seen the movie, it returns 4 as a general prediction.
The advantages of this algorithm is that it's relatively simple to code and can check movies without context, but when seeing a movie it has never seen before, it does not know what to do.
If we had more information (genre, etc.), we might be able to find more specific information.

### The Analysis
I had four files. Below are the results.

# File 1
Mean: 1.1602858286875906
Standard Deviation: 0.8772809400678043
Off by 0: 8918
Off by 1: 13390
Off by 2: 6857
Off by 3: 2641
Off by 4: 661

# File 2
Mean: 1.1784653465346535
Standard Deviation: 0.8816961789591311
Off by 0: 14043
Off by 1: 21657
Off by 2: 11325
Off by 3: 4394
Off by 4: 1101

# File 3
Mean: 1.1644288341607092
Standard Deviation: 0.8747398914829762
Off by 0: 19516
Off by 1: 30058
Off by 2: 15555
Off by 3: 5712
Off by 4: 1476

# File 4
Mean: 1.145311615621659
Standard Deviation: 0.8665972828246731
Off by 0: 21850
Off by 1: 33230
Off by 2: 16801
Off by 3: 6270
Off by 4: 1354

### Benchmarking
I ran time for set 4, which took 83.0009527 seconds. This should be about 1.044 milliseconds/prediction.
Since my prediction code generally runs in O(n) runtime (given it looks through the user list, which is set here), it'll increase by a factor of 10 or 100 if the entry size increased that much.

### Reflection
Both this and the previous assignment were actually a lot of fun, and I really enjoyed them.
Building structures and poking around with results is a lot of fun to me, and these assignments got me to work on that quite a bit.
As for getting a handle on Ruby, I feel like I have a lot I need to learn still, but I'm curious and looking at ways to play with the language. The formatting is weird, but I'm interested.
If I could choose something to change this assignment with, I would likely work to ramp up *from* a smaller dataset in order to make code run faster and to ensure error messages aren't insane.
However, most of the rest of the assignment was very nice.