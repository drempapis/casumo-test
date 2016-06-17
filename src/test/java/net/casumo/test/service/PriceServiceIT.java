package net.casumo.test.service;

import net.casumo.test.base.TestConfig;
import net.casumo.test.controller.body.Movie;
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
public class PriceServiceIT {

    @Autowired
    private PriceService priceService;

    @Test
    public void calculateRentingPrice_EmptyList_Test() {
        final List<Movie> moviesList = new ArrayList<>();
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(0, price);
    }

    @Test
    public void calculateRentingPrice_CombineMovies_Test() {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11, 1));
        moviesList.add(new Movie(SUPER_MAN, 5));
        moviesList.add(new Movie(SUPER_MAN_2, 2));
        moviesList.add(new Movie(OUT_OF_AFRICA, 7));
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(250, price);
    }
}
