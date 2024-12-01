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

    fun addMovie(): Int {
        val name = readNextLine("Enter movie name: ")
        val showTime = readNextLine("Enter show time: ")
        val availableSeats = readNextInt("Enter number of available seats: ")

        val movie = Movie(name, showTime, availableSeats)
        moviesApi.addMovie(movie)
        println("Movie added successfully!")
    }
    fun listMovies(){

    }

    fun updateMovie(){

    }
    fun deleteMovie(){

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




