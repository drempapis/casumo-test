package net.casumo.test.repository;

import net.casumo.test.controller.body.Movie;
import net.casumo.test.domain.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SessionRepository {

    private Map<UUID, Session> sessionMap;

    public SessionRepository() {
        sessionMap = new ConcurrentHashMap<>();
    }

    public void addSession(final UUID customerId, final List<Movie> movieList) {
        sessionMap.put(customerId, new Session(movieList));
    }

    public Session getSession(final UUID customerId) {
        return sessionMap.get(customerId);
    }
}
