package controllers

import models.Booking
import models.Movie
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File

class MoviesApiTest {

    private lateinit var moviesApi: MoviesApi
    private lateinit var movie1: Movie
    private lateinit var movie2: Movie
    private lateinit var movie3: Movie

    @BeforeEach
    fun setup() {
        moviesApi = MoviesApi(XMLSerializer(File("movies.xml")))
        movie1 = Movie("Inception", "2023-01-01T20:00:00", false)
        movie2 = Movie("The Matrix", "2023-01-02T20:00:00", false)
        movie3 = Movie("Interstellar", "2023-01-03T20:00:00", true)


        moviesApi.addMovie(movie1)
        moviesApi.addMovie(movie2)
        moviesApi.addMovie(movie3)
    }

    @AfterEach
    fun tearDown() {

    }

    @Nested
    inner class AddMovies {
        @Test
        fun `adding a movie increases the movie count`() {
            val newMovie = Movie("Dunkirk", "2023-01-04T20:00:00", false)
            assertEquals(3, moviesApi.numberOfOrders())
            moviesApi.addMovie(newMovie)
            assertEquals(4, moviesApi.numberOfOrders())
        }
    }

    @Nested
    inner class ListMovies {
        @Test
        fun `listing movies returns correct string representation`() {
            val expectedOutput = "0: Movie(name=Inception, showTime=2023-01-01T20:00:00, isMovieArchived=false)\n" +
                    "1: Movie(name=The Matrix, showTime=2023-01-02T20:00:00, isMovieArchived=false)\n" +
                    "2: Movie(name=Interstellar, showTime=2023-01-03T20:00:00, isMovieArchived=true)"
            assertEquals(expectedOutput, moviesApi.listMovies())
        }

        @Test
        fun `listing movies when none exist returns no orders stored message`() {
            val emptyMoviesApi = MoviesApi(XMLSerializer(File("empty-movies.xml")))
            assertEquals("No orders stored", emptyMoviesApi.listMovies())
        }
    }

    @Nested
    inner class UpdateMovies {
        @Test
        fun `updating a movie that exists returns true and updates the movie`() {
            val updatedMovie = Movie("Inception Updated", "2023-01-01T21:00:00", false)
            assertTrue(moviesApi.updateMovie(0, updatedMovie))
            assertEquals("Inception Updated", moviesApi.findMovie(0)?.name)
        }

        @Test
        fun `updating a movie that does not exist returns false`() {
            assertFalse(moviesApi.updateMovie(5, Movie("Nonexistent Movie", "2023-01-01T21:00:00", false)))
        }
    }

    @Nested
    inner class DeleteMovies {
        @Test
        fun `deleting a movie that exists returns the movie and decreases the count`() {
            assertEquals(3, moviesApi.numberOfOrders())
            assertEquals(movie3, moviesApi.deleteMovie(2))
            assertEquals(2, moviesApi.numberOfOrders())
        }

        @Test
        fun `deleting a movie that does not exist returns null`() {
            assertNull(moviesApi.deleteMovie(5))
        }
    }

    @Nested
    inner class ArchiveMovies {
        @Test
        fun `archiving a movie that does not exist returns false`() {
            assertFalse(moviesApi.archiveMovie(5))
            assertFalse(moviesApi.archiveMovie(-1))
        }

        @Test
        fun `archiving an already archived movie returns false`() {
            assertTrue(moviesApi.archiveMovie(1))
        }}}