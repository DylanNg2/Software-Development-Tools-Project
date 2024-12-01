package models

data class Movie(
    var name: String,
    var showTime: String,
    var availableSeats: Int,

)

data class Booking(
    val customerName: String,
    val movie: Movie
)

