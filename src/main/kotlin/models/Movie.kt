package models

data class Movie(
    var name: String,
    var showTime: String,
    var availableSeats: Int,
    var isMovieArchived :Boolean
)

data class Booking(
    val customerName: String,
    val movie: Movie
)

