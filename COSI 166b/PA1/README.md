### My Solution
Generally, my solution was to use hashes and lists in order to simplify and make the code work.

First, of course, I read the file into a 2-D array, separating by tab and rearranging the .data file fully into the code.I also made sure to convert everything into an integer or float as needed. From that, I can manipulate it easily.

For generating the popularity list and finding the general popularity of a movie, I iterated through this array and organized the entries into a hash of movies, with their average popularity, total popularity, and number of reviews tracked. This was so that I could more easily keep the average running.
The general popularity was determined by the average rating, and so the popularity of a single movie returned that. The total popularity list sorts the list by average rating.

For comparing users, I first made profiles of every user accessible throughout the class. I then found the similarities between users in two parts: 0.5 of the similarity rating is made up of the percent of movies the two share, and the other half is made up of the average differential in review, by percentage.
The most similar function essentially runs this through this for every user and like total popularity, sorts the list, and returns the top 10.

### Questions
# Describe an algorithm to predict the ranking that a user U would give to a movie M assuming the user hasn't already ranked the movie in the dataset.
Generally, the best way to do this using the data we have is find if the most similar person rated that movie and assign that rating to the user, moving through the list based on similarity until we find a match.

# Does your algorithms scale? You dont have to make it scale alot, I just want to see that you have an awareness of whether it does or does not, and why,
Theoretically, they should scale pretty nicely. On initialization, most of the heavy lifting in creating algorithms is made so it's only needed to be done once, and both the popularity list and the user profiles are O(n) based on the # of entries. The `initialize` function is O(3n) because there's no nesting.
Other than that, the worst runtime we have is `most_similar` at O(n^2), which definitely isn't ideal, but it's pretty decent to scale.

# What factors determine the execution time of your "most_similar" and "popularity_list" algorithms.
`most_similar` is determined by the amount of user profiles; it runs on O(n^2) (technically O(amount of users * amount of movies)), so as that scales, runtime will be worse.
`popularity_list` actually has O(nlogn) runtime due to `Hash#sort_by` being nlogn, and the popularity hash already being created upon initialization. The popularity hash's creation is O(n) based on the amount of entries we have.