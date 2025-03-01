library(tidyverse)
library(countrycode)
library(glmnet)
library(corrplot) # Correlated w/each other
library(MASS)
library(tidymodels)
load('WVS_Cross-National_Wave_7_rData_v5_0.rdata')
wvs <- `WVS_Cross-National_Wave_7_v5_0` |> as.tibble()

# Look to use tidymodels here.
# Assume measure is continuous and use glmnet

wvs <- wvs |> mutate(well_being = Q46 + Q48 + Q49)

# Directly calling dplyr::select since MASS masks it.
wvs_test <- dplyr::select(wvs, matches("^Q([0-9]+)"))
# Remove 46-56 inclusive
wvs_test <- dplyr::select(wvs_test, !matches("^Q(4[6-9]|5[0-6])$"))
colnames(wvs_test)

isnum <- sapply(wvs_test, is.numeric) # Cols where we're working with #'s
# For those cols, remove all vals less than 0.
wvs_test[isnum] <- lapply(wvs_test[isnum], \(x) ifelse(x < 0, NA, x))

# Select all numeric vars with <2% missing
isnum_lowna <- apply(wvs_test, 2, function(col){
  (sum(is.na(col)) / nrow(wvs_test)) < 0.02
  })

wvs_working <- wvs_test[isnum_lowna]
# Number of Observations thrown out
ncol_thrown <- ncol(wvs_test) - ncol(wvs_working)
ncol_thrown

# Add Country/Age/Gender to list of vars. Remove all missing rows!!
wvs_working$country <- countrycode(sourcevar = wvs$B_COUNTRY_ALPHA, 
                           origin = "iso3c", 
                           destination = "country.name")
wvs_working$age = ifelse(wvs_working$Q262>15, wvs_working$Q262, NA)
wvs_working$gender = wvs_test$Q260-1 # NAs already there. 0 is male, 1 is female.

wvs_working$well_being <- wvs$well_being

na_rows <- apply(wvs_working, 1, function(row){sum(is.na(row))})
sum(na_rows != 0) # We have to remove ALL NAs here. SO:
missing_rows <- na_rows != 0
wvs_lasso <- wvs_working[!missing_rows,]
# Since instructions specifically mention not to have country as a feature

# Lasso
y <- wvs_lasso$well_being
country <- wvs_lasso$country
wvs_lasso <- dplyr::select(wvs_lasso, !c(well_being, country))
grid <- 10^seq(10, -2, length = 100)
lasso_mod <- glmnet(data.matrix(wvs_lasso), y, alpha = 1, lambda = grid)
plot(lasso_mod)

set.seed(1)
cv_out <- cv.glmnet(data.matrix(wvs_lasso), y, alpha = 1) # tibbles don't work here!
plot(cv_out)
lam_1se <- cv_out$lambda.1se

out <- glmnet(wvs_lasso, y, alpha = 1, lambda = grid)
lasso.coef <- predict(out, type = "coefficients",
                      s = lam_1se)
sum(lasso.coef == 0) # Kicked out 37 vars

top_features <- coef(cv_out, s = "lambda.1se")
top_features <- top_features[-1,]
top_features <- top_features[top_features != 0]
test <- sort(abs(top_features), decreasing = TRUE) |> head(10)

wvs_q3 <- wvs_lasso |> dplyr::select(Q131, Q254, Q9, Q58, Q250, Q57, Q27, Q3, Q14, Q60, age, gender)
wvs_q3$country <- country
grid <- 10^seq(10, -2, length = 100)
lasso_q3 <- glmnet(data.matrix(wvs_q3), y, alpha = 1, lambda = grid) # y is still well-being
par(mar = c(5, 4, 4, 8), xpd = TRUE) # Need space for the legend
plot(lasso_q3)

# Making the legend
lasso_coefs <- coef(lasso_q3)
var_names <- rownames(lasso_coefs)[-1]
colors <- rainbow(length(var_names))
legend("right", legend = var_names, col = colors, lty = 1, cex = 0.8, inset = c(-0.25, 0))



