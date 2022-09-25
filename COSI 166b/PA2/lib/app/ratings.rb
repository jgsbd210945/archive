# This class compiles the data file and predicts movie ratings.

# frozen_string_literal: true

class Ratings
  attr_accessor :stats, :userprofiles, :maxmovie, :similarity_list

  def initialize(datafile)
    @stats = []
    move_file_to_list(datafile)
    @userprofiles = {}
    generate_user_profiles
    @similarity_list = {}
  end

  def predict(user, movie)
    @similarity_list = most_similar(user) if @similarity_list.include?(user) || @similarity_list.length.zero?
    @similarity_list.each do |usr|
      return @userprofiles[usr][movie] if @userprofiles[usr].include?(movie)
    end
    4.0 # If there is no equated user found, I'll return four as a general guess.
  end

  # returns a list of every single movie the user has not watched.
  def predict_all(user, highestnummovie)
    predictions = {}
    @similarity_list = most_similar(user)
    (0..highestnummovie).each do |movie|
      predictions[movie] = predict(user, movie) unless @userprofiles[user].include?(movie)
    end
    predictions
  end

  # The following four methods are taken from PA1, doing according tasks to help out this predictor.
  def move_file_to_list(datafile)
    @maxmovie = 0
    datfile = open(datafile)
    datfile.each_line do |line|
      add_line_to_stats(line)
    end
    datfile.close
  end

  def add_line_to_stats(line)
    review = line.split("\t")
    review = [review[0].to_i, review[1].to_i, review[2].to_f, review[3].to_i]
    @maxmovie = review[1] if maxmovie < review[1]
    @stats.append(review) # [x][0] is user ID, [x][1] is movie ID, [x][2] is rating, [x][3] is timestamp
  end

  # Generates user profiles baed on the movies they watch.
  def generate_user_profiles
    @stats.each do |entry|
      @userprofiles[entry[0]] = {} unless @userprofiles.include?(entry[0])
      @userprofiles[entry[0]][entry[1]] = entry[2] # adding to hash
    end
  end

  # Compares the similarity between two users.
  def similarity(user1, user2)
    bothwatched = 0
    totalmovies = @userprofiles[user1].length + @userprofiles[user2].length
    # The above will have overlap, will shrink when overlap occurs.
    avgdiff = 0.0 # to ensure it's a float
    @userprofiles[user2].each_key do |movie|
      next unless @userprofiles[user1].keys.include?(movie)

      bothwatched += 1
      totalmovies -= 1 # overlap
      avgdiff += (@userprofiles[user2][movie] - @userprofiles[user1][movie]).abs
    end
    ((bothwatched / totalmovies) + (avgdiff / totalmovies)) / 2 # This is between 0 and 1.
  end

  # returns the list of users from most similar to least similar.
  # I've modified this from PA1 to return the entire thing rather than the 10 most similar.
  def most_similar(user)
    comparisons = {}
    @userprofiles.each_key do |user2|
      comparisons[user2] = similarity(user, user2) if user2 != user
    end
    comparisons.sort_by { |_k, v| -v }.to_h.keys
  end
end
