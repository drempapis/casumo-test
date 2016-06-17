package net.casumo.test.repository;

import net.casumo.test.inventory.MovieType;
import net.casumo.test.inventory.MoviesInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class MovieRepository {

    @Autowired
    private MoviesInventory moviesInventory;

    public MovieType getMovieType(final UUID movieId) {
        MovieType movieType = moviesInventory.getMoviesInventoryType().get(movieId);
        if(movieType == null) {
            movieType = MovieType.NOT_FOUND;
        }
        return movieType;
    }

    public boolean isMovieAvailable(final UUID movieId) {
        Boolean isAvailable = moviesInventory.getMoviesAvailability().get(movieId);
        if(isAvailable == null) {
            isAvailable = Boolean.FALSE;
        }
        return isAvailable;
    }

    public void setMovieAvailability(final UUID movieId, final boolean available) {
        moviesInventory.getMoviesAvailability().put(movieId, available);
    }
}
