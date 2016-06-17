package net.casumo.test.repository;

import net.casumo.test.base.TestConfig;
import net.casumo.test.inventory.MovieType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MovieRepositoryIT {

    @Autowired
    private MovieRepository movieReposiroty;

    @Test
    public void getFilmTypeTest_FilmId_Valid() {
        final UUID movieId = UUID.fromString("f8bab770-6109-11e5-9d70-feff819cdc9f");
        final MovieType movieType = movieReposiroty.getMovieType(movieId);
        assertEquals(MovieType.NEW, movieType);
    }

    @Test
    public void getFilmTypeTest_MovieId_NotFound() {
        final UUID movieId = UUID.fromString("f8bab770-6109-11e5-9d70-feff816cdc9f");
        final MovieType movieType = movieReposiroty.getMovieType(movieId);
        assertEquals(MovieType.NOT_FOUND, movieType);
    }
}
