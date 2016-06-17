package net.casumo.test.service;

import net.casumo.test.controller.body.Movie;
import net.casumo.test.inventory.MovieType;
import net.casumo.test.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.casumo.test.base.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class BonusServiceTest {

    @InjectMocks
    private BonusService bonusService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    public void calculateBonus_EmptyList_Test() {
        final List<Movie> moviesList = new ArrayList<>();
        doReturn(MovieType.NEW).when(movieRepository).getMovieType(any(UUID.class));
        int bonus = bonusService.calculateBonus(moviesList);
        assertEquals(0, bonus);
    }

    @Test
    public void calculateBonus_NewRelease_Test() {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11));
        doReturn(MovieType.NEW).when(movieRepository).getMovieType(any(UUID.class));
        int bonus = bonusService.calculateBonus(moviesList);
        assertEquals(2, bonus);
    }

    @Test
    public void calculateBonus_RegularRelease_Test() {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(SUPER_MAN));
        doReturn(MovieType.REGULAR).when(movieRepository).getMovieType(any(UUID.class));
        int bonus = bonusService.calculateBonus(moviesList);
        assertEquals(1, bonus);
    }

    @Test
    public void calculateBonus_OldRelease_Test() {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(OUT_OF_AFRICA));
        doReturn(MovieType.OLD).when(movieRepository).getMovieType(any(UUID.class));
        int bonus = bonusService.calculateBonus(moviesList);
        assertEquals(1, bonus);
    }
}
