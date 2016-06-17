package net.casumo.test.service;

import com.google.common.collect.Lists;
import net.casumo.test.base.TestConfig;
import net.casumo.test.controller.body.Movie;
import net.casumo.test.domain.Session;
import net.casumo.test.repository.SessionRepository;
import net.casumo.test.util.Identifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static net.casumo.test.base.Constants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class SessionServiceIT {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    public void addMoviesToSession_Test() {
        final UUID customerId = Identifier.generateUUID();
        final List<Movie> moviesList = Arrays.asList(new Movie(MATRIX_11, 1));
        sessionService.updateCustomerSession(customerId, moviesList, 0);
        final Session session = sessionRepository.getSession(customerId);
        assertEquals(1, session.getMoviesList().size());
    }

    @Test
    public void updateMoviesToSession_Test() {
        final UUID customerId = Identifier.generateUUID();
        final List<Movie> firstMoviesList = new ArrayList<>();
        firstMoviesList.add(new Movie(MATRIX_11, 1));
        sessionService.updateCustomerSession(customerId, firstMoviesList, 0);

        final List<Movie> secondMoviesList = new ArrayList<>();
        secondMoviesList.add(new Movie(SUPER_MAN, 1));
        sessionService.updateCustomerSession(customerId, secondMoviesList, 0);

        final Session session = sessionRepository.getSession(customerId);
        assertEquals(2, session.getMoviesList().size());
    }

    @Test
    public void deleteMoviesFromSession_Delete_One_Test() {
        final UUID customerId = Identifier.generateUUID();

        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11, 1));
        moviesList.add(new Movie(SUPER_MAN, 1));
        moviesList.add(new Movie(OUT_OF_AFRICA, 5));
        sessionService.updateCustomerSession(customerId, Lists.newArrayList(moviesList), 0);

        final List<Movie> deleteMoviesList = new ArrayList<>();
        deleteMoviesList.add(new Movie(MATRIX_11, 1));
        sessionService.deleteMoviesFromSession(customerId, Lists.newArrayList(deleteMoviesList));

        final Session session = sessionRepository.getSession(customerId);
        assertEquals(2, session.getMoviesList().size());
    }

    @Test
    public void deleteMoviesFromSession_Delete_All_Test() {
        final UUID customerId = Identifier.generateUUID();
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11, 1));
        moviesList.add(new Movie(OUT_OF_AFRICA, 5));
        sessionService.updateCustomerSession(customerId, Lists.newArrayList(moviesList), 0);
        sessionService.deleteMoviesFromSession(customerId, moviesList);
        final Session session = sessionRepository.getSession(customerId);
        assertEquals(0, session.getMoviesList().size());
    }

}
