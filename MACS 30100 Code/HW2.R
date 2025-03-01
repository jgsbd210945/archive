library(tidyverse)
library(tidymodels)

airbnb <- read_csv("listings.csv")
codebook <- read_csv("codebook.csv")

airbnb |> dim() # 8269 rows, 75 cols.

# Checking for NAs
na_cols <- apply(airbnb, 2, function(col){sum(is.na(col))})
na_cols |> sort() |> head(25) # All just 0s.
na_cols |> sort(decreasing = TRUE) |> head(25) # This gets all of them!

# Hmm...neighbourhood_group_cleansed and Calendar_updates are ALL NA... let's remove them.
missing_cols <- na_cols > (nrow(airbnb) * 0.5)

airbnb0 <- airbnb
airbnb <- airbnb[!missing_cols]

### And now for rows...
na_rows <- apply(airbnb, 1, function(row){sum(is.na(row))})
na_rows |> sort(decreasing = TRUE) |> head(25)
# 19 of 73 as the most...I'm fine with that.

### 3 - Check/Clean data!
airbnb # Makes a tibble, so head() isn't needed here.

## 10 Variables I'm going to do:
# -> host_location (??, fct (State, maybe?))
# -> host_response_time (fct)
# -> host_response_rate(%)
# -> host_acceptance_rate (%)
# -> host_neighbourhood (fct)
# -> host_verifications (This is a split list of 3...can you have a list of fct?)
# -> neighbourhood_cleansed (fct)
# -> bathrooms_text (fct)
# -> price ($ -> int)
# -> reviews_per_month(filter!)

## Number stuff
response_rate <- airbnb$host_response_rate |>
  gsub("%", "", x = _) |>
  as.numeric() # coerces NAs automatically
hist(response_rate, breaks = 100) # Making sure there aren't any outside of 0 -> 100.
airbnb$host_response_rate <- response_rate

acceptance_rate <- airbnb$host_acceptance_rate |>
  gsub("%", "", x = _) |>
  as.numeric() # coerces NAs automatically
hist(acceptance_rate, breaks = 100) 
airbnb$host_acceptance_rate <- acceptance_rate

price <- airbnb$price |>
  gsub("\\$|,", "", x = _) |>
  as.numeric()
hist(log(price), breaks = 100) # Hmm...why are some over 9?
sort(price, decreasing = TRUE) |> head(25)
# I mean, ok, I guess...$10k/night isn't bad if it's 14 rooms like I think I saw.
airbnb$price <- price

boxplot(airbnb$reviews_per_month, horizontal = TRUE) #60 reviews per month seems like an outlier...
sort(airbnb$reviews_per_month, decreasing = TRUE) |> head(25)
# Let's filter out more than 30. That's strange for it being so high.
airbnb <- airbnb |> filter(reviews_per_month < 20)
boxplot(airbnb$reviews_per_month, horizontal = TRUE) # Much better.

## Text Stuff
airbnb |> group_by(host_response_time) |> summarize(count = n())
response_time <- airbnb$host_response_time |>
  gsub("N/A", NA, x = _) |>
  factor()
head(response_time) # Pretty clean.
airbnb$host_response_time <- response_time

airbnb |> group_by(host_neighbourhood) |> summarize(count = n())
neighborhood <- airbnb$host_neighbourhood |>
  factor()
head(neighborhood)
airbnb$host_neighbourhood <- neighborhood

airbnb |> group_by(neighbourhood_cleansed) |> summarize(count = n())
neighborhood_cleansed <- airbnb$neighbourhood_cleansed |>
  factor()
head(neighborhood_cleansed)
airbnb$neighbourhood_cleansed <- neighborhood_cleansed

airbnb |> group_by(bathrooms_text) |> summarize(count = n())
bath_text <- airbnb$bathrooms_text |>
  factor()
head(bath_text)
airbnb$bathrooms_text <- bath_text

# Host City/State instead of location?
airbnb |> group_by(host_location) |> summarize(count = n())
# Let's split these since they all follow [city], [state/country].
loc <- airbnb$host_location |> str_split(", ")
head(loc)
# Hmm...there seems to be just "Italy" there. Let's select the *last* element of the list.
state <- sapply(loc, tail, 1)
city <- sapply(loc, function(x){ifelse(length(x) == 1, NA, x[1])})
# City can be a string. I'll make state/country a factor.
state <- state |> factor()
airbnb$host_city <- city
airbnb$host_state_country <- state

## Let's make host_verifications a bit easier.
airbnb |> group_by(host_verifications) |> summarize(n())
verif <- airbnb$host_verifications
head(verif) # Needs cleaning first.
verif <- verif |>
  gsub("\\[|\\]|'", "", x = _) |>
  str_split(", ")
email <- sapply(verif, function(x){ifelse("email" %in% x, TRUE, FALSE)})
# Verification
sum(email)
sum(!email)
# Now for the other two.
phone <- sapply(verif, function(x){ifelse("phone" %in% x, TRUE, FALSE)})
work_email <- sapply(verif, function(x){ifelse("work_email" %in% x, TRUE, FALSE)})
airbnb$email <- email
airbnb$phone <- phone
airbnb$work_email <- work_email
head(select(airbnb, email, phone, work_email)) # Let's see if we did it right.

### Amenities
head(airbnb$amenities) # Ok, so there's a lot going on here.
# Let's strip the brackets and begin breaking it down.
amenities <- airbnb$amenities |>
  gsub("\\[\"|\"\\]", "", x = _) |> # Strips beginning and end.
  str_split("\\\", \\\"") # Should be \", \"
head(amenities)

names <- amenities[[5]] # Most Extensive + Simplest
df <- sapply(names, function(term){
  sapply(amenities, function(x){ifelse(term %in% x, TRUE, FALSE)})
}) |> data.frame()

# Seeing how many are just all False
false_cols <- apply(df, 2, function(col){sum(col)})
false_cols |> sort() |> head(25)
hist(false_cols)

# I'll cut anything with under 1/10th.
empty_cols <- false_cols > (nrow(df) * 0.9)
df <- df[!empty_cols]

airbnb <- cbind(airbnb, df)

### Transformations
head(airbnb$price)
# I think this is pretty useful as-is (with the commas and $ stripped.)
# If we're trying to predict price ranges based on the market, maybe stats as
# mentioned in #7 could be useful
stat_df <- airbnb |>
  filter(host_city == "Chicago") |>
  group_by(host_neighbourhood) |>
  summarize(count = n(),
            mean_price = mean(price, na.rm = TRUE),
            median_price = median(price, na.rm = TRUE),
            hosts = length(unique(host_id)),
            avail_30 = sum(ifelse(availability_30, 1, 0)),
            pct_avail = (avail_30 / count) * 100)
airbnb <- airbnb |> merge(stat_df, by = "host_neighbourhood")

# 7's questions
arrange(stat_df, desc(mean_price)) # Highest Price
arrange(stat_df, desc(median_price))
arrange(stat_df, mean_price) # Lowest Price
arrange(stat_df, median_price)
arrange(stat_df, desc(hosts)) # Hosts
arrange(stat_df, desc(avail_30)) # Availability
arrange(stat_df, desc(pct_avail))
