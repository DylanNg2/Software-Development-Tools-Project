    package ie.setu.mainKt

    import controllers.MoviesApi
    import persistence.JSONSerializer
    import utils.*
    import java.lang.System.exit
    import io.github.oshai.kotlinlogging.KotlinLogging
    import models.Movie
    import java.io.File

    private val logger = KotlinLogging.logger {}
    private val moviesApi = MoviesApi(JSONSerializer(File("movies.json")))

    fun main(){
        runMain()
    }

    fun mainMenu():Int{
        print("""" 
       > ----------------------------------
       > |        MOVIE BOOKING APP       |
       > ----------------------------------
       > | MOVIE MENU                     |
       > |   1) Add a movie               |
       > |   2) List all movies           |
       > |   3) Update a movie            |
       > |   4) Delete a movie            |
       > |   5) Search movies             |
       > ----------------------------------
       > | BOOKING MENU                   |
       > |   6) Book a movie              |
       > |   7) List all bookings         |
       > |   8) Cancel a booking          |
       > ----------------------------------
       > |   9) Save movies               |
       > |   10) Load movies              |
       > ----------------------------------
       > |   0) Exit                      |
       > ---------------------------------- 
           >""".trimMargin(">"))
        return readNextInt(" > ==>>")
    }

    fun runMain(){
    do {
        val option = mainMenu()
        when(option){
            1-> addMovie()
            2-> listMovies()
            3-> updateMovie()
            4-> deleteMovie()
            5-> searchMovie()
            6-> bookMovie()
            7-> listBookings()
            8-> cancelBooking()
            9-> saveMovies()
            10-> loadMovies()
            0-> exitApp()
            else -> println("Invalid Option, please choose a different option: $option")
        }
    }while (true)
    }

    fun addMovie() {
        print("Enter movie name: ")
        val name = readLine()?.trim() ?: ""
        print("Enter show time (e.g., 8:00): ")
        val showTime = readLine()?.trim() ?: ""
        val movie = Movie(name, showTime, false)
        moviesApi.addMovie(movie)
        println("Movie added successfully!")
    }

    fun listMovies(){
        val moviesList = moviesApi.listMovies()
        if (moviesList == "No orders stored") {
            println("No movies available.")
        } else {
            println(moviesList)
        }
    }

    fun updateMovie() {
        listMovies()
        if (moviesApi.listMovies() > 0.toString()) {
            val indexToUpdate = readNextInt("Enter the index of the movie to update: ")
            if (moviesApi.isValidIndex(indexToUpdate)) {
                val movieName = readNextLine("Enter the new name for the movie: ")
                val showTime = readNextLine("Enter the new showtime: ")
                if (moviesApi.updateMovie(indexToUpdate, Movie(movieName, showTime,false))) {
                    println("Movie updated successfully!")
                } else {
                    println("Movie Failed!")
                }
            } else {
                println("There are no movies for this index")
            }
        }
    }

    fun deleteMovie(){
        listMovies()
        if (moviesApi.listMovies() > 0.toString()) {
            val indexToDelete = readNextInt("Enter the index of the movie to delete: ")
            val movieToDelete = moviesApi.deleteMovie(indexToDelete)
            if(movieToDelete != null){
                println("Movie deleted successfully! Deleted movie: ")
            }else{
                println("Movie Failed to delete!")
            }
        }
    }

    fun searchMovie(){
        val searchTitle = readNextLine("Enter the title to search for: ")
        val searchResults = moviesApi.searchByMovie(searchTitle)
        if (searchResults.isEmpty()) {
            println("No movies found.")
        } else {
            println(searchResults)
        }
    }
    fun bookMovie() {
        listMovies()
        if (moviesApi.numberOfOrders() > 0) {
            val indexToBook = readNextInt("Enter the index of the movie to book: ")
            val customerName = readNextLine("Enter your name: ")
            if (moviesApi.bookMovie(customerName, indexToBook)) {
                println("Booking successful!")
            } else {
                println("Failed to book the movie. Please check seat availability.")
            }
        } else {
            println("No movies available to book.")
        }
    }
    fun listBookings(){
        println(moviesApi.listBookings().toString())
    }

    fun cancelBooking() {
        val customerName = readNextLine("Enter the name for the booking to cancel: ")
        if (moviesApi.deleteBooking(customerName)) {
            println("Booking cancelled successfully.")
        } else {
            println("Failed to cancel the booking.")
        }
    }

    fun saveMovies(){
        try {
            moviesApi.store()
            println("Movies saved successfully.")
        } catch (e: Exception) {
            println("Failed to save movies: ${e.message}")
        }
    }

    fun loadMovies(){
        try {
            moviesApi.load()
            println("Movies loaded successfully.")
        } catch (e: Exception) {
            println("Failed to load movies: ${e.message}")
        }
    }

    fun exitApp() {
        println("Exiting...bye")
        exit(0)
    }




