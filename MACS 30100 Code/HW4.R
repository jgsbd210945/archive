library(tidyverse)
library(countrycode)
library(MASS)
load('WVS_Cross-National_Wave_7_rData_v5_0.rdata')
wvs <- `WVS_Cross-National_Wave_7_v5_0` |> as.tibble()

# Outcome of interest
wvs$happiness <- case_when(wvs$Q46>0 ~ 4-wvs$Q46,
                          TRUE ~ NA_integer_) # ALL ELSE IS NA
table(wvs$happiness)

dim(wvs)
head(wvs)
# glimpse(wvs)

na_cols <- apply(wvs, 2, function(col){sum(is.na(col))})
na_cols |> sort(decreasing = TRUE) |> head(35) # Lots of 90360 values...that's most of the data. I'll filter.
missing_cols <- na_cols > (nrow(wvs) * 0.9)
wvs0 <- wvs
wvs <- wvs[!missing_cols]

na_rows <- apply(wvs, 1, function(row){sum(is.na(row))})
na_rows |> sort(decreasing = TRUE) |> head(100) # All at 115...where does it drop off?
na_rows |> hist()
# I'm not too worried about this. I think it'll be fine.

wvs$country <- countrycode(sourcevar = wvs$B_COUNTRY_ALPHA, 
                          origin = "iso3c", 
                          destination = "country.name")
table(wvs$country , useNA = "always")
wvs <- wvs |> filter(!is.na(country))

range(wvs$Q262 , na.rm = TRUE) 
wvs$age = ifelse(wvs$Q262>15, wvs$Q262, NA) 
hist(wvs$age)
wvs |> filter(!is.na(age)) |>
  ggplot(aes(country, age))+
  geom_boxplot() + 
  coord_flip()

wvs |> 
  group_by(country) |>
  summarize(mean_happiness = mean(happiness, na.rm = TRUE)) |>
  ggplot() + 
  aes(country , mean_happiness) +
  geom_col()+
  coord_flip()

wvs |> 
  group_by(country) |>
  summarize(mean_happiness = mean(happiness, na.rm = TRUE)) |>
  arrange(mean_happiness) |>
  # the following command tells R what the order of the countries
  # as a categorical variable is
  mutate(country = factor(country, levels = country)) |>
  ggplot() + 
  aes(country , mean_happiness) +
  geom_col(fill = 'steelblue3')+
  coord_flip()


wvs$belief_in_god = ifelse(wvs$Q165>0, 2-wvs$Q165, NA)
wvs_country <- wvs |> 
  group_by(country) |>
  summarize(cor_god_happ = cor(belief_in_god, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_god_happ) |>
  mutate(country = factor(country, levels = country))

wvs_country |>
  filter(!is.na(cor_god_happ)) |>
  ggplot(aes(country , cor_god_happ)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where belief in God is positively correlated with happiness') +
  ylab('correlation between belief in God and happiness') 

## Gender
wvs$gender = ifelse(wvs$Q165>0, wvs$Q165-1, NA) # 0 is male, 1 is female
sum(is.na(wvs$gender)) # 4631
wvs |> 
  group_by(country) |>
  summarize(cor_gender = cor(gender, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_gender) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_gender)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_gender)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where Gender is most positively correlated with happiness') +
  ylab('correlation between gender and happiness') 

## Age - Already cleaned
wvs |> 
  group_by(country) |>
  summarize(cor_age = cor(age, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_age) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_age)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_age)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where age is most positively correlated with happiness') +
  ylab('correlation between age and happiness') 

## Size of household
wvs$household = ifelse(wvs$Q270>=0, wvs$Q270, NA)
sum(is.na(wvs$household)) #934
wvs |> 
  group_by(country) |>
  summarize(cor_household = cor(household, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_household) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_household)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_household)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where size of household is most positively correlated with happiness') +
  ylab('correlation between household size and happiness') 

# Number of Children
wvs$nchild = ifelse(wvs$Q274>=0, wvs$Q274, NA)
sum(is.na(wvs$nchild)) # 3813
wvs |> 
  group_by(country) |>
  summarize(cor_nchild = cor(nchild, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_nchild) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_nchild)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_nchild)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where number of children is most positively correlated with happiness') +
  ylab('correlation between # of Children and happiness') 

# Education Level
wvs$edulevel = ifelse(wvs$Q275>=0, wvs$Q275, NA)
sum(is.na(wvs$edulevel)) #991
wvs |> 
  group_by(country) |>
  summarize(cor_edulevel = cor(edulevel, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_edulevel) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_edulevel)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_edulevel)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where education level is most positively correlated with happiness') +
  ylab('correlation between education level and happiness') 


## Marital Status
wvs$marital_status <- wvs$Q273 |> case_match(
  1 ~ 1,
  2 ~ 1,
  3 ~ 0,
  4 ~ 0,
  5 ~ 0,
  6 ~ 0
)
sum(is.na(wvs$marital_status)) # 571
wvs |> 
  group_by(country) |>
  summarize(cor_mstatus = cor(marital_status, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_mstatus) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_mstatus)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_mstatus)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where marital status is most positively correlated with happiness') +
  ylab('correlation between marriage and happiness') 

## Immigration
wvs$gen1_immigrant <- ifelse(wvs$Q263>0, wvs$Q263 - 1, NA) # to make True/False.
sum(is.na(wvs$gen1_immigrant)) # 379
wvs |> 
  group_by(country) |>
  summarize(cor_g1immigrant = cor(gen1_immigrant, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_g1immigrant) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_g1immigrant)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_g1immigrant)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where immigration is most positively correlated with happiness') +
  ylab('correlation between being a first-generation immigrant and happiness') 

wvs$gen2_immigrant <- ifelse((wvs$Q264 < 0), NA,
                             (ifelse(wvs$Q265 < 0, NA, wvs$Q264 + wvs$Q265 - 2)))
sum(is.na(wvs$gen2_immigrant)) # 4934
wvs |> 
  group_by(country) |>
  summarize(cor_g2immigrant = cor(gen2_immigrant, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_g2immigrant) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_g2immigrant)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_g2immigrant)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where parents\' immigration is most positively correlated with happiness') +
  ylab('correlation between being a second-generation immigrant and happiness') 

## Country Pride
wvs$country_closeness <- case_when(
  wvs$Q257 == 1 ~ 3,
  wvs$Q257 == 2 ~ 2,
  wvs$Q257 == 3 ~ 1,
  wvs$Q257 == 4 ~ 0,
  TRUE ~ NA
)
sum(is.na(wvs$country_closeness)) # 1134
wvs |> 
  group_by(country) |>
  summarize(cor_countryclose = cor(country_closeness, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_countryclose) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_countryclose)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_countryclose)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where closeness to country is most positively correlated with happiness') +
  ylab('correlation between country closeness and happiness')

## National Pride
wvs$nationalism <- case_when(
  wvs$Q254 == 1 ~ 3,
  wvs$Q254 == 2 ~ 2,
  wvs$Q254 == 3 ~ 1,
  wvs$Q254 == 4 ~ 0,
  TRUE ~ NA # 5 is "I am not [nationality]," so making NA.
)
sum(is.na(wvs$nationalism)) # 2861
wvs |> 
  group_by(country) |>
  summarize(cor_nationalism = cor(nationalism, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_nationalism) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_nationalism)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_nationalism)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where pride in country is most positively correlated with happiness') +
  ylab('correlation between nationalism and happiness')

## Political System Performance
wvs$polsystem_performance <- ifelse(wvs$Q252>0, wvs$Q252 - 1, NA) # to start at 0.
sum(is.na(wvs$polsystem_performance)) # 3481
wvs |> 
  group_by(country) |>
  summarize(cor_polsystem = cor(polsystem_performance, happiness, use = "pairwise.complete.obs")) |>
  arrange(cor_polsystem) |>
  mutate(country = factor(country, levels = country)) |>
  filter(!is.na(cor_polsystem)) |>
  # Splitting between filtering and ggplot
  ggplot(aes(country, cor_polsystem)) +
  geom_point() +
  geom_hline(yintercept = 0, linetype = "dashed", col='darkred') +
  coord_flip() +
  ggtitle('Where satisfaction with political system is most positively correlated with happiness') +
  ylab('correlation between satisfaction in political system and happiness')

wvs$democracy <- ifelse(wvs$Q251>0, wvs$Q251 - 1, NA) # to start at 0.
wvs$democracy_importance <- ifelse(wvs$Q250>0, wvs$Q250 - 1, NA)
wvs$civil_rights <- ifelse(wvs$Q246>0, wvs$Q246 - 1, NA)
wvs$leftright <- ifelse(wvs$Q240>0, wvs$Q240 - 1, NA) # 0 is left, 9 is right
wvs$strongleader <- case_when(
  wvs$Q235 == 1 ~ 3,
  wvs$Q235 == 2 ~ 2,
  wvs$Q235 == 3 ~ 1,
  wvs$Q235 == 4 ~ 0,
  TRUE ~ NA
)

ols <- lm(happiness ~ gender + age + household + nchild + edulevel + marital_status + gen1_immigrant + gen2_immigrant + country_closeness + nationalism + polsystem_performance + democracy + democracy_importance + civil_rights + leftright + strongleader, data = wvs)
summary(ols)

ols_mstatus <- lm(happiness ~ marital_status, data = wvs)
summary(ols_mstatus)

ols_countryclose <- lm(happiness ~ country_closeness, data = wvs)
summary(ols_countryclose)

ols_nationalism <- lm(happiness ~ nationalism, data = wvs)
summary(ols_nationalism)

ols_polsystem <- lm(happiness ~ polsystem_performance, data = wvs)
summary(ols_polsystem)

wvs_filteredage <- wvs |> filter(!is.na(age))
poly1 <- lm(happiness ~ poly(age, 1), data = wvs_filteredage)
poly2 <- lm(happiness ~ poly(age, 2), data = wvs_filteredage)
logit_model <- polr(factor(happiness) ~ age, data = wvs_filteredage)
summary(logit_model)

# Generating a test model for age and country here.
test_model <- polr(factor(happiness) ~ age + country, data = wvs_filteredage)

age_seq <- seq(18, 70, by = 1)
countries <- c("Canada", "Indonesia", "China") # The ones with the largest number of responses
temp_data <- expand.grid(age = age_seq, country = countries)
temp_data <- temp_data |>
  mutate(belief_in_god = mode(wvs$belief_in_god), # Categoricals
         gender = mode(wvs$gender),
         edulevel = mode(wvs$edulevel),
         marital_status = mode(wvs$marital_status),
         gen1_immigrant = mode(wvs$gen1_immigrant),
         gen2_immigrant = mode(wvs$gen2_immigrant),
         #Continuous vars
         household = mean(wvs$household, na.rm = TRUE),
         nchild = mean(wvs$nchild, na.rm = TRUE),
         country_closeness = mean(wvs$country_closeness, na.rm = TRUE),
         nationalism = mean(wvs$nationalism, na.rm = TRUE),
         polsystem_performance = mean(wvs$polsystem_performance, na.rm = TRUE),
         democracy = mean(wvs$democracy, na.rm = TRUE),
         democracy_importance = mean(wvs$democracy_importance, na.rm = TRUE),
         civil_rights = mean(wvs$civil_rights, na.rm = TRUE),
         leftright = mean(wvs$leftright, na.rm = TRUE),
         strongleader = mean(wvs$strongleader, na.rm = TRUE))

predicted_probs <- predict(test_model, newdata = temp_data, type = "probs")
temp_data <- cbind(temp_data, predicted_probs)

temp_data |> ggplot(aes(x = age, y = `3`)) +
  geom_point(aes(color = country)) +
  scale_y_continuous(labels = scales::percent) +
  labs(x = "Age of Respondent",
       y = "Chance of responding \"Very Happy\"",
       title = "Predicted Chance of Happiness based on age for Canada, Indonesia, and China",
       color = "Country")
