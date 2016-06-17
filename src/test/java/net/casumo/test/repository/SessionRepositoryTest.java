package net.casumo.test.repository;

import net.casumo.test.controller.body.Movie;
import net.casumo.test.domain.Session;
import net.casumo.test.util.Identifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.casumo.test.base.Constants.MATRIX_11;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class SessionRepositoryTest {

    @InjectMocks
    private SessionRepository sessionRepository;

    @Test
    public void getSession_Not_Found_Test() {
        final UUID customerId = Identifier.generateUUID();
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11));
        sessionRepository.addSession(customerId, moviesList);
        final Session session = sessionRepository.getSession(Identifier.generateUUID());
        assertNull(session);
    }

    @Test
    public void getSessionTest() {
        final UUID customerId = Identifier.generateUUID();
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11));
        sessionRepository.addSession(customerId, moviesList);
        final Session session = sessionRepository.getSession(customerId);
        assertEquals(1, session.getMoviesList().size());
    }
}
