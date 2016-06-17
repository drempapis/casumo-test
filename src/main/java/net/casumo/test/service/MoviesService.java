package net.casumo.test.service;


import java.util.ArrayList;
import java.util.List;
import net.casumo.test.controller.body.Movie;
import net.casumo.test.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoviesService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAvailableMovies(final List<Movie> moviesList) {
        List<Movie> availableMoviesList = new ArrayList<>();
        for(Movie movie : moviesList) {
            if(movieRepository.isMovieAvailable(movie.getMovieId())) {
                movieRepository.setMovieAvailability(movie.getMovieId(), Boolean.FALSE);
                availableMoviesList.add(movie);
            }
        }
        return availableMoviesList;
    }
}
