package net.casumo.test.service;

import net.casumo.test.controller.body.Movie;
import net.casumo.test.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BonusService {

    private static final Logger logger = LoggerFactory.getLogger(BonusService.class);

    @Autowired
    private MovieRepository movieRepository;

    public int calculateBonus(final List<Movie> moviesList) {
        int bonus = 0;
        for (Movie movie : moviesList) {
            switch (movieRepository.getMovieType(movie.getMovieId())) {
                case NEW:
                    bonus += 2;
                    break;
                case REGULAR:
                case OLD:
                    bonus += 1;
                    break;
                default:
                    logger.info(String.format("[movie-not-found], movieId:[%s]", movie.getMovieId().toString()));
            }
        }
        return bonus;
    }
}
