package controllers

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
    private var api: MoviesApi? = null
    private var emptyApi: MoviesApi? = null

    private var avenger: Movie? = null
    private var avatar: Movie? = null
    private var car: Movie? = null

    @BeforeEach
    fun setup() {
        api = MoviesApi(XMLSerializer(File("movies.xml")))
        emptyApi = MoviesApi(XMLSerializer(File("empty-movies.xml")))

        avenger = Movie("Avenger", "2024-12-01", false)
        avatar = Movie("Avatar", "2024-12-05", false)
        car = Movie("car", "2024-12-10", false)

        api!!.addMovie(avenger!!)
        api!!.addMovie(avatar!!)
        api!!.addMovie(car!!)
    }

    @AfterEach
    fun tearDown() {
        api = null
        emptyApi = null
        avenger = null
        avatar = null
        car = null
    }

    @Nested
    inner class AddMovies {
        @Test
        fun `adding a movie to a populated list increases its size`() {
            val titanic = Movie("Titanic", "2024-12-15", false)
            assertEquals(3, api!!.numberOfMovie())
            api!!.addMovie(titanic)
            assertEquals(4, api!!.numberOfMovie())
            assertEquals(titanic, api!!.findMovie(3))
        }

        @Test
        fun `adding a movie to an empty list works correctly`() {
            val titanic = Movie("Titanic", "2024-12-15", false)
            assertEquals(0, emptyApi!!.numberOfMovie())
            emptyApi!!.addMovie(titanic)
            assertEquals(1, emptyApi!!.numberOfMovie())
            assertEquals(titanic, emptyApi!!.findMovie(0))
        }
    }

    @Nested
    inner class ListMovies {
        @Test
        fun `listMovies returns message when no movies exist`() {
            assertEquals("No orders stored", emptyApi!!.listMovies())
        }

        @Test
        fun `listMovies returns all movies when movies are stored`() {
            val movieList = api!!.listMovies()
            assertTrue(movieList.contains("Inception"))
            assertTrue(movieList.contains("Avatar"))
            assertTrue(movieList.contains("The Matrix"))
        }
    }

    @Nested
    inner class UpdateMovies {
        @Test
        fun `updating a movie that doesn't exist returns false`() {
            val newMovie = Movie("Updated Movie", "2024-12-20", false)
            assertFalse(api!!.updateMovie(5, newMovie))
            assertFalse(api!!.updateMovie(-1, newMovie))
            assertFalse(emptyApi!!.updateMovie(0, newMovie))
        }

        @Test
        fun `updating a movie updates its details`() {
            val newMovie = Movie("Updated Inception", "2024-12-01", false)
            assertTrue(api!!.updateMovie(0, newMovie))
            val updatedMovie = api!!.findMovie(0)
            assertEquals("Updated Inception", updatedMovie!!.name)
            assertEquals("2024-12-01", updatedMovie.showTime)
        }
    }

    @Nested
    inner class DeleteMovies {
        @Test
        fun `deleting a movie reduces the list size`() {
            assertEquals(3, api!!.numberOfMovie())
            api!!.deleteMovie(1)
            assertEquals(2, api!!.numberOfMovie())
        }

        @Test
        fun `deleting a non-existing movie returns null`() {
            assertNull(api!!.deleteMovie(5))
            assertNull(api!!.deleteMovie(-1))
        }
    }

    @Nested
    inner class PersistenceTests {
        @Test
        fun `saving and loading data retains movie collection`() {
            api!!.store()
            val loadedApi = MoviesApi(XMLSerializer(File("movies.xml")))
            loadedApi.load()

            assertEquals(api!!.numberOfMovie(), loadedApi.numberOfMovie())
            assertEquals(api!!.listMovies(), loadedApi.listMovies())
        }
    }
}
