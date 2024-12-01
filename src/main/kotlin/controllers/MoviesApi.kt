    package controllers

    import models.Booking
    import persistence.Serializer
    import models.Movie
    import utils.isValidListIndex

    class MoviesApi (serializerType: Serializer){
        private  var serializer: Serializer = serializerType
        private  var movies = ArrayList<Movie>()
        private val bookings = mutableListOf<Booking>()

        fun addMovie(movie : Movie): List<Movie>{
            movies.add(movie)
            return movies;
        }

        fun listMovies():String=
            if(movies.isEmpty()) "No orders stored"
            else formatListString(movies)

        fun deleteMovie(indexToDelete: Int): Movie? {
            return if (isValidIndex(indexToDelete)) {
                movies.removeAt(indexToDelete)
            } else null
        }

        fun updateMovie(indexToUpdate: Int, movies: Movie?): Boolean {
            val foundMovie = findMovie(indexToUpdate)

            if ((foundMovie != null) && ( movies != null)) {
                foundMovie.name = movies.name
                foundMovie.showTime = movies.showTime
                return true
            }
            return false
        }

        fun searchByMovie(searchString: String): String =
            formatListString(
                movies.filter { movies -> movies.name.contains(searchString, ignoreCase = true)
                })
        fun numberOfOrders() = movies.size

        fun findMovie(index: Int): Movie? {
            return if (isValidIndex(index)) {
                movies[index]
            } else null
        }

        fun archiveMovie(indexToArchive: Int): Boolean {
            if (isValidIndex(indexToArchive)) {
                val movieToArchive = movies[indexToArchive]
                if (!movieToArchive.isMovieArchived) {
                    movieToArchive.isMovieArchived = true
                    return true
                }
            }
            return false
        }


        fun isValidIndex(index: Int): Boolean {
            return isValidListIndex(index = index, movies);
        }
        fun bookMovie(customerName: String, movieIndex: Int): Boolean {
            val movie = findMovie(movieIndex)
            if (movie != null) {
                val booking = Booking(customerName, movie)
                bookings.add(booking)
                return true
            }
            return false
        }

        fun listBookings(): String =
            if (bookings.isEmpty()) "No bookings found."
            else bookings.joinToString("\n") { "${it.customerName} booked for ${it.movie.name}" }

        fun deleteBooking(customerName: String): Boolean {
            val booking = bookings.find { it.customerName == customerName }
            return if (booking != null) {
                bookings.remove(booking)
                true
            } else false
        }

        @Throws(Exception::class)
        fun load() {
            movies = serializer.read() as ArrayList<Movie>
        }

        @Throws(Exception::class)
        fun store() {
            serializer.write(movies)
        }
        private fun formatListString(ordersToFormat: List<Movie>): String =
            ordersToFormat.joinToString(separator = "\n") { order ->
                movies.indexOf(order).toString() + ": " + order.toString()
            }
    }
