package net.casumo.test.domain;

import net.casumo.test.controller.body.Movie;
import net.casumo.test.util.Identifier;

import java.util.List;
import java.util.UUID;

public class Session {

    private UUID sessionId;

    private List<Movie> moviesList;

    private long bonus;

    public Session(final List<Movie> moviesList) {
        this.sessionId = Identifier.generateUUID();
        this.moviesList = moviesList;
        this.bonus = 0;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public long getBonus() {
        return bonus;
    }

    public void incrementBonus(final long increment) {
        this.bonus += increment;
    }
}
