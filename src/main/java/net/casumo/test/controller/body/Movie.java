package net.casumo.test.controller.body;

import net.casumo.test.util.ServerTimeUtils;

import java.util.UUID;

public class Movie {

    private UUID movieId;

    private Long timeStamp;

    private int days;

    public Movie() {
    }

    public Movie(final UUID movieId) {
        this(movieId, 0);
    }

    public Movie(final UUID movieId, final int days) {
        this.movieId = movieId;
        this.timeStamp = ServerTimeUtils.getCurrentTimeMillis();
        this.days = days;
    }

    public Movie(final UUID movieId, final Long timeStamp, final int days) {
        this.movieId = movieId;
        this.timeStamp = timeStamp;
        this.days = days;
    }

    public UUID getMovieId() {
        return movieId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public int getDays() {
        return days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Movie movie = (Movie) o;
        return !(movieId != null ? !movieId.equals(movie.movieId) : movie.movieId != null);
    }

    @Override
    public int hashCode() {
        return movieId != null ? movieId.hashCode() : 0;
    }
}
