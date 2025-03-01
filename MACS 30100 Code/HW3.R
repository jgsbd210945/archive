library(tidyverse)
library(janitor)
library(randomForest)
library(rpart)
library(rpart.plot)

listings <- read_csv("listings.csv", show_col_types = FALSE)

listings_long <- listings |>
  transmute(id,
            amenities_list = map(amenities, \(x) {
              clean_x <- gsub('\\[|\\]|"', '', x)
              out <- str_split(clean_x, ", ", simplify = FALSE)[[1]]
              out
            })
  ) |>
  unnest(cols = amenities_list) |>
  filter(!is.na(amenities_list), amenities_list != "") |>
  mutate(amenities_list = as.character(amenities_list))

top_amenities <- listings_long |> # Changed
  count(amenities_list, sort = TRUE) |>
  filter(n > nrow(listings) * 0.4, n < nrow(listings) * 0.7) |>
  pull(amenities_list)

listings_for_pivot <- listings_long |>
  filter(amenities_list %in% top_amenities) |>
  dplyr::select(id, amenities_list) |>
  mutate(value = 1) |>
  distinct()

listings_wide <- listings_for_pivot |> # Changed 
  pivot_wider(
    id_cols = "id",
    names_from  = "amenities_list",
    values_from = "value",
    values_fill = 0
  ) |>
  left_join(listings, by = "id") |>
  mutate(price = as.numeric(gsub("[\\$,]", "", price))) |>
  relocate(price, .after = id)


corrs <- map_dbl(
  top_amenities, 
  ~ cor(listings_wide[[.x]], log(listings_wide$price), use = "complete.obs")
)

top_predictive_amenities = 
  tibble(amenity = top_amenities,
         correlation_with_price = corrs
  ) |>
  arrange(desc(abs(correlation_with_price)))

na_cols <- apply(listings_wide, 2, function(col){sum(is.na(col))})
na_cols |> sort(decreasing = TRUE) |> head(25)
missing_cols <- na_cols > 1000
listings_wide0 <- listings_wide
listings_wide <- listings_wide[!missing_cols]

sum(is.na(listings_wide$price))
listings_wide <- listings_wide |>
  filter(!is.na(price))

na_cols <- apply(listings_wide, 2, function(col){sum(is.na(col))})
na_cols |> sort(decreasing = TRUE) |> head(25)

listings_wide |> filter(is.na(bedrooms)) |> select(beds, bedrooms)
listings_wide$bedrooms <- ifelse(is.na(listings_wide$bedrooms), listings_wide$beds, listings_wide$bedrooms)

listings_wide$host_is_superhost <- ifelse(is.na(listings_wide$host_is_superhost),
                                          FALSE,
                                          listings_wide$host_is_superhost)
listings_wide$has_availability <- ifelse(is.na(listings_wide$has_availability),
                                         FALSE,
                                         listings_wide$has_availability)

listings_wide$host_neighbourhood <- ifelse(is.na(listings_wide$host_neighbourhood),
                                         "",
                                         listings_wide$host_neighbourhood)


colnames(listings_wide) <- make.names(colnames(listings_wide)) # Fixes colnames, makes it easier for randomForest to run

