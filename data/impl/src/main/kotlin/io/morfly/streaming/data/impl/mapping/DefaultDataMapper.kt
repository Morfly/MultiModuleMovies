package io.morfly.streaming.data.impl.mapping

import io.morfly.streaming.common.domain.Author
import io.morfly.streaming.common.domain.Credit
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.domain.Review
import io.morfly.streaming.data.impl.network.MovieDbApi
import io.morfly.streaming.data.impl.network.model.MovieDbAuthor
import io.morfly.streaming.data.impl.network.model.MovieDbCredit
import io.morfly.streaming.data.impl.network.model.MovieDbMovie
import io.morfly.streaming.data.impl.network.model.MovieDbReview
import io.morfly.streaming.data.impl.storage.entity.StoredAuthor
import io.morfly.streaming.data.impl.storage.entity.StoredMovie
import io.morfly.streaming.data.impl.storage.entity.StoredReview
import java.math.RoundingMode
import javax.inject.Inject


class DefaultDataMapper @Inject constructor() : DataMapper {

    override fun networkToStorage(movie: MovieDbMovie, ordinal: Int, query: String) = with(movie) {
        StoredMovie(
            localId = query + "_" + id,
            id = id,
            ordinal = ordinal,
            query = query,
            title = title,
            overview = overview,
            backdropPath = backdropPath,
            posterPath = posterPath,
            voteAverage = voteAverage.rounded(),
            voteCount = voteCount
        )
    }

    override fun networkToStorage(review: MovieDbReview, movieId: Int) = with(review) {
        StoredReview(
            id = id,
            movieId = movieId,
            author = networkToStorage(authorDetails),
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun networkToStorage(author: MovieDbAuthor) = with(author) {
        StoredAuthor(
            name = name,
            username = username,
            avatarPath = avatarPath,
            rating = rating
        )
    }

    override fun networkToDomain(movie: MovieDbMovie) = with(movie) {
        Movie(
            id = id,
            title = title,
            overview = overview,
            thumbPosterUrl = buildPosterUrl(posterPath, isThumb = true),
            posterUrl = buildPosterUrl(posterPath),
            thumbBackdropUrl = buildBackdropUrl(backdropPath, isThumb = true),
            backdropUrl = buildBackdropUrl(backdropPath),
            voteAverage = voteAverage.rounded(),
            voteCount = voteCount
        )
    }

    override fun networkToDomain(credit: MovieDbCredit) = with(credit) {
        Credit(
            id = id,
            name = name,
            character = character,
            department = knownForDepartment,
            profilePath = profilePath,
            order = order
        )
    }

    override fun storageToDomain(movie: StoredMovie) = with(movie) {
        Movie(
            id = id,
            title = title,
            overview = overview,
            thumbPosterUrl = buildPosterUrl(posterPath, isThumb = true),
            posterUrl = buildPosterUrl(posterPath),
            thumbBackdropUrl = buildBackdropUrl(backdropPath, isThumb = true),
            backdropUrl = buildBackdropUrl(backdropPath),
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }

    private fun Float.rounded(): Float =
        toBigDecimal().setScale(1, RoundingMode.UP).toFloat()

    override fun storageToDomain(review: StoredReview) = with(review) {
        Review(
            id = id,
            author = storageToDomain(author),
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun storageToDomain(author: StoredAuthor) = with(author) {
        Author(
            name = name,
            username = username,
            avatarPath = buildProfileUrl(avatarPath),
            rating = rating
        )
    }

    internal fun buildPosterUrl(path: String?, isThumb: Boolean = false): String? {
        if (path == null) return null
        val resolution = if (isThumb) "w92" else "w500"
        return "${MovieDbApi.IMAGES_BASE_URL}$resolution${path}"
    }

    internal fun buildBackdropUrl(path: String?, isThumb: Boolean = false): String? {
        if (path == null) return null
        val resolution = if (isThumb) "w300" else "original"
        return "${MovieDbApi.IMAGES_BASE_URL}$resolution${path}"
    }

    internal fun buildProfileUrl(path: String?): String? {
        if (path == null) return null
        if (path.startsWith("http")) return path
        if (path.startsWith("/http")) return path.substring(1)
        val resolution = "w185"
        return "${MovieDbApi.IMAGES_BASE_URL}${resolution}${path}"
    }
}