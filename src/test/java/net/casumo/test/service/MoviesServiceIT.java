package net.casumo.test.service;

import net.casumo.test.base.TestConfig;
import net.casumo.test.controller.body.Movie;
import net.casumo.test.repository.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static net.casumo.test.base.Constants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MoviesServiceIT {

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private MovieRepository movieRepository;

    @Before
    public void initialize() {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11, 1));
        moviesList.add(new Movie(SUPER_MAN, 5));
        moviesList.add(new Movie(SUPER_MAN_2, 2));
        moviesList.add(new Movie(OUT_OF_AFRICA, 7));
        for(Movie movie : moviesList) {
            movieRepository.setMovieAvailability(movie.getMovieId(), Boolean.TRUE);
        }
    }

    @Test
    public void getAvailableMovies_All_Movies_Available_Test() {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11));
        moviesList.add(new Movie(SUPER_MAN));
        moviesList.add(new Movie(SUPER_MAN_2));
        moviesList.add(new Movie(OUT_OF_AFRICA));

        final List<Movie> availableMoviesList = moviesService.getAvailableMovies(moviesList);
        assertEquals(moviesList.size(), availableMoviesList.size());
    }

    @Test
    public void getAvailableMovies_Movies_Not_Available_Test() {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11));
        moviesList.add(new Movie(SUPER_MAN));
        moviesList.add(new Movie(SUPER_MAN_2));
        moviesList.add(new Movie(OUT_OF_AFRICA));

        List<Movie> availableMoviesList = moviesService.getAvailableMovies(moviesList);
        assertEquals(moviesList.size(), availableMoviesList.size());

        availableMoviesList = moviesService.getAvailableMovies(moviesList);
        assertEquals(0, availableMoviesList.size());
    }
}
