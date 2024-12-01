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
        val name = readNextLine("Enter movie name: ")
        val showTime = readNextLine("Enter show time: ")
        val availableSeats = readNextInt("Enter number of available seats: ")

        val movie = Movie(name, showTime, availableSeats)
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
                val availableSeats = readNextInt("Enter the new number of available seats: ")
                if (moviesApi.updateMovie(indexToUpdate, Movie(movieName, showTime, availableSeats, false))) {
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

    }

    fun saveOrder(){

    }

    fun loadOrder(){

    }

    fun exitApp() {
        println("Exiting...bye")
        exit(0)
    }




