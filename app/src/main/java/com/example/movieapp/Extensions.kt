package com.example.movieapp

fun WatchList.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        posterPath = this.poster_path,
        backdropPath = this.backdrop_path,
        title = this.title,
        releaseDate = this.release_date,
        runtime = this.runtime,
        genres = this.genre,
        rating = this.rating,
        overview = this.overview,
        category = this.category
    )
}
