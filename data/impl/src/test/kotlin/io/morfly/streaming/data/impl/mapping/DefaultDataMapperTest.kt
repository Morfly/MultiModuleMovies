package io.morfly.streaming.data.impl.mapping

import io.morfly.streaming.data.impl.network.model.MovieDbMovie
import io.morfly.streaming.data.impl.storage.entity.StoredMovie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test


class DefaultDataMapperTest {
    private val mapper = DefaultDataMapper()

    @Test
    fun `map network to storage when NO nulls present`() {
        val movie = MovieDbMovie(
            id = 1,
            title = "title",
            overview = "overview",
            backdropPath = "/path.jpg",
            posterPath = "/path.jpg",
            voteAverage = 5.3234234f,
            voteCount = 30
        )

        val storedMovie = mapper.networkToStorage(movie, ordinal = 1, query = "query")

        with(storedMovie) {
            assertEquals(id, 1)
            assertEquals(title, "title")
            assertEquals(overview, "overview")
            assertEquals(backdropPath, "/path.jpg")
            assertEquals(posterPath, "/path.jpg")
            assertEquals(voteAverage, 5.4f)
            assertEquals(voteCount, 30)
            assertEquals(ordinal, 1)
            assertEquals(query, "query")
        }
    }

    @Test
    fun `map network to storage when nulls present`() {
        val movie = MovieDbMovie(
            id = 1,
            title = "title",
            overview = "overview",
            backdropPath = null,
            posterPath = null,
            voteAverage = 5.3f,
            voteCount = 30
        )

        val storedMovie = mapper.networkToStorage(movie, ordinal = 1, query = "query")

        with(storedMovie) {
            assertEquals(id, 1)
            assertEquals(title, "title")
            assertEquals(overview, "overview")
            assertNull(backdropPath)
            assertNull(posterPath)
            assertEquals(voteAverage, 5.3f)
            assertEquals(voteCount, 30)
            assertEquals(ordinal, 1)
            assertEquals(query, "query")
        }
    }

    @Test
    fun `map network to domain when NO nulls present`() {
        val movie = MovieDbMovie(
            id = 1,
            title = "title",
            overview = "overview",
            posterPath = "/path1.jpg",
            backdropPath = "/path2.jpg",
            voteAverage = 5.3f,
            voteCount = 30
        )

        val domainMovie = mapper.networkToDomain(movie)

        with(domainMovie) {
            assertEquals(id, 1)
            assertEquals(title, "title")
            assertEquals(overview, "overview")
            assertEquals(thumbPosterUrl, "https://image.tmdb.org/t/p/w92/path1.jpg")
            assertEquals(posterUrl, "https://image.tmdb.org/t/p/w500/path1.jpg")
            assertEquals(thumbBackdropUrl, "https://image.tmdb.org/t/p/w300/path2.jpg")
            assertEquals(backdropUrl, "https://image.tmdb.org/t/p/original/path2.jpg")
            assertEquals(voteAverage, 5.3f)
            assertEquals(voteCount, 30)
        }
    }


    @Test
    fun `map network to domain when nulls present`() {
        val movie = MovieDbMovie(
            id = 1,
            title = "title",
            overview = "overview",
            backdropPath = null,
            posterPath = null,
            voteAverage = 5.3f,
            voteCount = 30
        )

        val domainMovie = mapper.networkToDomain(movie)

        with(domainMovie) {
            assertEquals(id, 1)
            assertEquals(title, "title")
            assertEquals(overview, "overview")
            assertNull(thumbPosterUrl)
            assertNull(posterUrl)
            assertNull(thumbBackdropUrl)
            assertNull(backdropUrl)
            assertEquals(voteAverage, 5.3f)
            assertEquals(voteCount, 30)
        }
    }

    @Test
    fun `map storage to domain when NO nulls present`() {
        val movie = StoredMovie(
            localId = "query1",
            id = 1,
            title = "title",
            overview = "overview",
            posterPath = "/path1.jpg",
            backdropPath = "/path2.jpg",
            voteAverage = 5.3f,
            voteCount = 30,
            ordinal = 1,
            query = "query",
        )

        val domainMovie = mapper.storageToDomain(movie)

        with(domainMovie) {
            assertEquals(id, 1)
            assertEquals(title, "title")
            assertEquals(overview, "overview")
            assertEquals(thumbPosterUrl, "https://image.tmdb.org/t/p/w92/path1.jpg")
            assertEquals(posterUrl, "https://image.tmdb.org/t/p/w500/path1.jpg")
            assertEquals(thumbBackdropUrl, "https://image.tmdb.org/t/p/w300/path2.jpg")
            assertEquals(backdropUrl, "https://image.tmdb.org/t/p/original/path2.jpg")
            assertEquals(voteAverage, 5.3f)
            assertEquals(voteCount, 30)
        }
    }

    @Test
    fun `map storage to domain when nulls present`() {
        val movie = StoredMovie(
            localId = "query1",
            id = 1,
            title = "title",
            overview = "overview",
            posterPath = null,
            backdropPath = null,
            voteAverage = 5.3f,
            voteCount = 30,
            ordinal = 1,
            query = "query",
        )

        val domainMovie = mapper.storageToDomain(movie)

        with(domainMovie) {
            assertEquals(id, 1)
            assertEquals(title, "title")
            assertEquals(overview, "overview")
            assertNull(thumbPosterUrl)
            assertNull(posterUrl)
            assertNull(thumbBackdropUrl)
            assertNull(backdropUrl)
            assertEquals(voteAverage, 5.3f)
            assertEquals(voteCount, 30)
        }
    }

    @Test
    fun `buildPosterUrl when path is null and isThumb is true`() {
        val posterUrl = mapper.buildPosterUrl(path = null, isThumb = true)

        assertNull(posterUrl)
    }

    @Test
    fun `buildPosterUrl when path is null and isThumb is false`() {
        val posterUrl = mapper.buildPosterUrl(path = null, isThumb = false)

        assertNull(posterUrl)
    }

    @Test
    fun `buildPosterUrl when path when isThumb is true`() {
        val posterUrl = mapper.buildPosterUrl(path = "/path.jpg", isThumb = true)

        assertEquals(posterUrl, "https://image.tmdb.org/t/p/w92/path.jpg")
    }

    @Test
    fun `buildPosterUrl when path when isThumb is false`() {
        val posterUrl = mapper.buildPosterUrl(path = "/path.jpg", isThumb = false)

        assertEquals(posterUrl, "https://image.tmdb.org/t/p/w500/path.jpg")
    }

    @Test
    fun `buildBackdropUrl when path is null and isThumb is true`() {
        val backdropUrl = mapper.buildBackdropUrl(path = null, isThumb = true)

        assertNull(backdropUrl)
    }

    @Test
    fun `buildBackdropUrl when path is null and isThumb is false`() {
        val backdropUrl = mapper.buildBackdropUrl(path = null, isThumb = false)

        assertNull(backdropUrl)
    }

    @Test
    fun `buildBackdropUrl when path when isThumb is true`() {
        val backdropUrl = mapper.buildBackdropUrl(path = "/path.jpg", isThumb = true)

        assertEquals(backdropUrl, "https://image.tmdb.org/t/p/w300/path.jpg")
    }

    @Test
    fun `buildBackdropUrl when path when isThumb is false`() {
        val backdropUrl = mapper.buildBackdropUrl(path = "/path.jpg", isThumb = false)

        assertEquals(backdropUrl, "https://image.tmdb.org/t/p/original/path.jpg")
    }
}