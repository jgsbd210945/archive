class MovieData
    attr_accessor :stats, :poplist, :userprofiles #generally, @userprofiles is a hash of users that has a hash of movie/rating pairs.

    def initialize(datafile)
        @stats = []
        move_file_to_list(datafile)
        @poplist = {}
        @userprofiles = {}
        generate_user_profiles
        generate_popularity_hash
    end

    def move_file_to_list(datafile)
        datfile = open(datafile)
        datfile.each_line do |line|
            review = line.split("\t")
            review = [review[0].to_i, review[1].to_i, review[2].to_f, review[3].to_i]
            @stats.append(review) #[x][0] is user ID, [x][1] is movie ID, [x][2] is rating, [x][3] is timestamp
        end
        datfile.close
    end

    def generate_popularity_hash
        @stats.each do |id|
            if @poplist.include?(id[1])
                @poplist[id[1]][2] += 1.0 #iterating ct
                @poplist[id[1]][1] += id[2] #adding to total ratings
                @poplist[id[1]][0] = @poplist[id[1]][1]/@poplist[id[1]][2] #taking the new average now that we have new #'s
            else #ID is not in poplist
                @poplist[id[1]] = [id[2], id[2], 1.0] #Initializing the row with avg rating, total rating, # of ratings.
            end
        end
    end

    def popularity(movie_id)
        @poplist[movie_id][0]
    end

    def popularity_list
        @poplist.sort_by{|_k, v| -v[0]}.to_h.keys
    end

    def generate_user_profiles
        @stats.each do |entry|
            if !(@userprofiles.include?(entry[0]))
                @userprofiles[entry[0]] = {}
            end
            @userprofiles[entry[0]][entry[1]] = entry[2] #adding to hash
        end
    end

    def similarity(user1, user2)
        bothwatched = 0
        totalmovies = @userprofiles[user1].length + @userprofiles[user2].length #will have overlap, will shrink when overlap occurs.
        avgdiff = 0.0 #to ensure it's a float
        @userprofiles[user2].keys.each do |movie|
            if @userprofiles[user1].keys.include?(movie)
                bothwatched += 1
                totalmovies -= 1 #overlap
                if @userprofiles[user2][movie] < @userprofiles[user1][movie]
                    avgdiff += @userprofiles[user2][movie]/@userprofiles[user1][movie]
                else
                    avgdiff += @userprofiles[user1][movie]/@userprofiles[user2][movie]
                end
            end
        end
        ((bothwatched/totalmovies) + (avgdiff/totalmovies))/2 #This is between 0 and 1.
    end

    def most_similar(u)
        comparisons = {}
        @userprofiles.keys.each do |usr|
            if usr != u
                comparisons[usr] = similarity(u, usr)
            end
        end
        comparisons.sort_by{|_k, v| -v}.to_h.keys[0...10] #numbers 1-10, I could do [0..9] but this makes more sense to me somehow.
    end
end
