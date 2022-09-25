# This class validates the results of the Ratings class. It calls Ratings and
# uses what the Ratings predict in order to validate against known results.

# frozen_string_literal: true

require_relative('ratings')

class Validator
  attr_accessor :basedata, :testdata, :totalcount, :offby

  # two Ratings objects
  def initialize(base, test)
    @basedata = base
    @testdata = test
  end

  def validate
    @totalcount = 0
    @offby = [0, 0, 0, 0, 0]
    @testdata.userprofiles.each_key do |user|
      iterate_user(user)
    end
    mean, stddev = calculate_stddev_and_mean
    [mean, stddev, @offby]
  end

  def iterate_user(user)
    totalcount_usr, offby_usr = validate_single(user)
    @totalcount += totalcount_usr
    5.times do |ct|
      @offby[ct] += offby_usr[ct]
    end
  end

  def calculate_stddev_and_mean
    mean = ((@offby[1] + (2 * @offby[2]) + (3 * @offby[3])+ (4 * @offby[4])).to_f / @totalcount.to_f)
    stddev = 0.0
    5.times do |ratingdiff|
      stddev += @offby[ratingdiff] * (ratingdiff.to_f - mean).abs
    end
    stddev = Math.sqrt(stddev / @totalcount)
    [mean, stddev]
  end

  # to keep it...relatively short.
  def validate_single(user)
    test_ratings = @testdata.predict_all(user, @basedata.maxmovie)
    totalcount = 0
    offby = [0, 0, 0, 0, 0] # 0, 1, 2, 3, 4
    @basedata.userprofiles[user].each do |prediction|
      totalcount += 1
      offby[(test_ratings[prediction[0]] - @basedata.userprofiles[user][prediction[0]]).to_i.abs] += 1
    end
    [totalcount, offby]
  end
end
