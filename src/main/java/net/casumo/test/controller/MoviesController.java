package net.casumo.test.controller;

import java.util.List;

import net.casumo.test.controller.body.Body;
import net.casumo.test.controller.body.Movie;
import net.casumo.test.controller.response.RentMoviesResponse;
import net.casumo.test.controller.response.ReturnMoviesResponse;
import net.casumo.test.service.BonusService;
import net.casumo.test.service.MoviesService;
import net.casumo.test.service.PriceService;
import net.casumo.test.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MoviesController {

    private static final Logger logger = LoggerFactory.getLogger(MoviesController.class);

    @Autowired
    private BonusService bonusService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private MoviesService moviesService;

    @RequestMapping(value = "/rent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RentMoviesResponse rentMovies(final @RequestBody Body body) {
        final List<Movie> availableMovies = moviesService.getAvailableMovies(body.getMoviesList());
        final int bonus = bonusService.calculateBonus(availableMovies);
        final int price = priceService.calculateRentingPrice(availableMovies);
        sessionService.updateCustomerSession(body.getCustomerId(), availableMovies, bonus);
        logger.debug(String.format("rentMovies:[/rent], customerId[%s], chargedPrice:[%d], bonus:[%d]", body.getCustomerId().toString(), price, bonus));
        return new RentMoviesResponse(price, bonus);
    }

    @RequestMapping(value = "/return", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReturnMoviesResponse returnMovies(final @RequestBody Body body) {
        final int price = priceService.calculateReturningPrice(body.getCustomerId(), body.getMoviesList());
        sessionService.deleteMoviesFromSession(body.getCustomerId(), body.getMoviesList());
        logger.debug(String.format("returnMovies:[/return], customerId[%s], subChargedPrice:[%d]", body.getCustomerId().toString(), price));
        return new ReturnMoviesResponse(price);
    }
}
