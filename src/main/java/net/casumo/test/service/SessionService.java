package net.casumo.test.service;

import net.casumo.test.controller.body.Movie;
import net.casumo.test.domain.Session;
import net.casumo.test.repository.MovieRepository;
import net.casumo.test.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MovieRepository movieRepository;

    public void updateCustomerSession(final UUID customerId, final List<Movie> movieList, final long bonus) {
        final Session session = sessionRepository.getSession(customerId);
        if (session == null) {
            sessionRepository.addSession(customerId, movieList);
        } else {
            session.getMoviesList().addAll(movieList);
        }
        updateBonus(customerId, bonus);
    }

    public void deleteMoviesFromSession(final UUID customerId, final List<Movie> movieList) {
        final Session session = sessionRepository.getSession(customerId);
        for (Movie movie : movieList) {
            movieRepository.setMovieAvailability(movie.getMovieId(), Boolean.TRUE);
            session.getMoviesList().remove(movie);
        }
    }

    private void updateBonus(final UUID customerId, final long bonus) {
        final Session session = sessionRepository.getSession(customerId);
        session.incrementBonus(bonus);
    }
}
