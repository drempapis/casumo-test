package net.casumo.test.service;

import net.casumo.test.controller.body.Movie;
import net.casumo.test.domain.Session;
import net.casumo.test.repository.MovieRepository;
import net.casumo.test.repository.SessionRepository;
import net.casumo.test.util.ServerTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class PriceService {

    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    private static final int REGULAR_FILM_OFFSET = 3;
    private static final int PREMIUM_PRICE = 40;
    private static final int BASIC_PRICE = 30;
    private static final int OLD_FILM_OFFSET = 5;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public int calculateRentingPrice(final List<Movie> movieList) {
        int price = 0;
        for (Movie movie : movieList) {
            int days = movie.getDays();
            switch (movieRepository.getMovieType(movie.getMovieId())) {
                case NEW:
                    price += days * PREMIUM_PRICE;
                    break;
                case REGULAR:
                    price += calculatePrice(days, REGULAR_FILM_OFFSET);
                    break;
                case OLD:
                    price += calculatePrice(days, OLD_FILM_OFFSET);
                    break;
                default:
                    logger.info(String.format("[movie-not-found], movieId:[%s]", movie.getMovieId().toString()));
            }
        }
        return price;
    }

    private long calculatePrice(final long days, final int dayOffset) {
        long price = 0;
        if (days <= dayOffset) {
            price = BASIC_PRICE;
        } else {
            price = BASIC_PRICE + BASIC_PRICE * (days - dayOffset);
        }
        return price;
    }

    public int calculateReturningPrice(final UUID customerId, final List<Movie> returnedMovieList) {
        final Session session = sessionRepository.getSession(customerId);
        int price = 0;
        for (Movie returnedMovie : returnedMovieList) {
            Movie rentedMovie = getRentedMovieFromSession(session, returnedMovie);
            long extraDays = calculateExtraDays(returnedMovie.getTimeStamp(), rentedMovie);
            int rentedDays = rentedMovie.getDays();
            switch (movieRepository.getMovieType(returnedMovie.getMovieId())) {
                case NEW:
                    price += extraDays * PREMIUM_PRICE;
                    break;
                case REGULAR:
                    price += calculatePrice(extraDays, rentedDays, REGULAR_FILM_OFFSET);
                    break;
                case OLD:
                    price += calculatePrice(extraDays, rentedDays, OLD_FILM_OFFSET);
                    break;
                default:
                    logger.info(String.format("[movie-not-found], movieId:[%s]", returnedMovie.getMovieId().toString()));
            }
        }
        return price;
    }

    private long calculatePrice(final long extraDays, final long rentedDays, final int dayOffset) {
        long price = 0;
        long daysToAdd = extraDays - (dayOffset - rentedDays);
        if (daysToAdd > 0) {
            price = BASIC_PRICE * daysToAdd;
        }
        return price;
    }

    private long calculateExtraDays(final Long returnMovieTimeStmp, final Movie rentedMovie) {
        final LocalDateTime returnMovieTime = ServerTimeUtils.getLocalDateTime(returnMovieTimeStmp);
        final LocalDateTime rentMovieTime = ServerTimeUtils.getLocalDateTime(rentedMovie.getTimeStamp());
        return rentMovieTime.until(returnMovieTime, ChronoUnit.DAYS) - rentedMovie.getDays();
    }

    private Movie getRentedMovieFromSession(final Session session, final Movie returnedMovie) {
        Movie rentedMovie = null;
        for (Movie movie : session.getMoviesList()) {
            if (movie.equals(returnedMovie)) {
                rentedMovie = movie;
                break;
            }
        }
        return rentedMovie;
    }

}
