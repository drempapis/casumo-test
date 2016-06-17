package net.casumo.test.controller.body;

import java.util.List;
import java.util.UUID;

public class Body {

    private UUID customerId;

    private List<Movie> moviesList;

    public Body() {
    }

    public Body(final UUID customerId, final List<Movie> moviesList) {
        this.customerId = customerId;
        this.moviesList = moviesList;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }
}

