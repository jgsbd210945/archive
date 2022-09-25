# This class is the command hub of the program.
# It initializes both classes and runs both of them, printing results as necessary.
# It's given files and allows you ot select them given what it has.

# frozen_string_literal: true

require_relative('validator')
require_relative('ratings')

class Control
  def initialize; end

  def run
    puts 'Type in the number (1-4) of the file you want to look at.'
    filenum = $stdin.gets.chomp
    puts '' # just formatting, making it look nice
    base = Ratings.new("u#{filenum}.base")
    test = Ratings.new("u#{filenum}.test")
    valid = Validator.new(base, test)
    mean, stddev, offby = valid.validate
    print_results(mean, stddev, offby)
  end

  def print_results(mean, stddev, offby)
    puts "Mean: #{mean}"
    puts "Standard Deviation: #{stddev}"
    counter = 0
    offby.each do |numoff|
      puts "Off by #{counter}: #{numoff}"
      counter += 1
    end
  end
end

cont = Control.new
cont.run
