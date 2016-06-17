package net.casumo.test.controller;

import com.fasterxml.jackson.databind.JsonNode;
import net.casumo.test.base.TestConfig;
import net.casumo.test.controller.body.Body;
import net.casumo.test.controller.body.Movie;
import net.casumo.test.repository.MovieRepository;
import net.casumo.test.util.Identifier;
import net.casumo.test.util.JsonUtils;
import net.casumo.test.util.ServerTimeUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.casumo.test.base.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MoviesControllerIT {

    @Autowired
    private MoviesController moviesController;

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private MovieRepository movieRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(moviesController).build();

        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11, 1));
        moviesList.add(new Movie(SUPER_MAN, 5));
        moviesList.add(new Movie(SUPER_MAN_2, 2));
        moviesList.add(new Movie(OUT_OF_AFRICA, 7));
        for(Movie movie : moviesList) {
            movieRepository.setMovieAvailability(movie.getMovieId(), Boolean.TRUE);
        }
    }

    @Test
    public void rentMoviesControllerTest() throws Exception {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11, 1));
        moviesList.add(new Movie(SUPER_MAN, 5));
        moviesList.add(new Movie(SUPER_MAN_2, 2));
        moviesList.add(new Movie(OUT_OF_AFRICA, 7));
        final Body rentMoviesBody = new Body(Identifier.generateUUID(), moviesList);

        final MockHttpServletResponse response = this.mockMvc.perform(post("/rent").
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                content(jsonUtils.toJson(rentMoviesBody))).
                andExpect(status().is(HttpStatus.SC_OK)).andReturn().getResponse();

        final String content = response.getContentAsString();
        final JsonNode jsonNode = jsonUtils.toJsonNode(content);
        assertEquals(250, Integer.parseInt(jsonNode.get("price").toString()));
        assertEquals(5, Integer.parseInt(jsonNode.get("bonus").toString()));
    }

    @Test
    public void rentMoviesController_NotAvailale_Movies_Test() throws Exception {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(MATRIX_11, 1));
        moviesList.add(new Movie(SUPER_MAN, 5));
        moviesList.add(new Movie(SUPER_MAN_2, 2));
        moviesList.add(new Movie(OUT_OF_AFRICA, 7));
        final Body rentMoviesBody = new Body(Identifier.generateUUID(), moviesList);

        MockHttpServletResponse response = this.mockMvc.perform(post("/rent").
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                content(jsonUtils.toJson(rentMoviesBody))).
                andExpect(status().is(HttpStatus.SC_OK)).andReturn().getResponse();

        String content = response.getContentAsString();
        JsonNode jsonNode = jsonUtils.toJsonNode(content);
        assertEquals(250, Integer.parseInt(jsonNode.get("price").toString()));
        assertEquals(5, Integer.parseInt(jsonNode.get("bonus").toString()));

        response = this.mockMvc.perform(post("/rent").
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                content(jsonUtils.toJson(rentMoviesBody))).
                andExpect(status().is(HttpStatus.SC_OK)).andReturn().getResponse();

        content = response.getContentAsString();
        jsonNode = jsonUtils.toJsonNode(content);
        assertEquals(0, Integer.parseInt(jsonNode.get("price").toString()));
        assertEquals(0, Integer.parseInt(jsonNode.get("bonus").toString()));
    }

    @Test
    public void rentMoviesController_UnRegistered_Movie_Test() throws Exception {
        final List<Movie> moviesList = new ArrayList<>();
        moviesList.add(new Movie(UNREGISTERED_MOVIE, 1));
        final Body rentMoviesBody = new Body(Identifier.generateUUID(), moviesList);

        final MockHttpServletResponse response = this.mockMvc.perform(post("/rent").
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                content(jsonUtils.toJson(rentMoviesBody))).
                andExpect(status().is(HttpStatus.SC_OK)).andReturn().getResponse();

        final String content = response.getContentAsString();
        final JsonNode jsonNode = jsonUtils.toJsonNode(content);
        assertEquals(0, Integer.parseInt(jsonNode.get("price").toString()));
        assertEquals(0, Integer.parseInt(jsonNode.get("bonus").toString()));
    }

    @Test
    public void returnMoviesControllerTest() throws Exception {
        final UUID customerId = Identifier.generateUUID();

        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(2));

        final List<Movie> rentMoviesList = new ArrayList<>();
        rentMoviesList.add(new Movie(MATRIX_11, rentMovieTimeStamp, 1));
        rentMoviesList.add(new Movie(SUPER_MAN, rentMovieTimeStamp, 5));
        rentMoviesList.add(new Movie(SUPER_MAN_2, rentMovieTimeStamp, 2));
        rentMoviesList.add(new Movie(OUT_OF_AFRICA, rentMovieTimeStamp, 7));
        final Body rentMoviesBody = new Body(customerId, rentMoviesList);

        this.mockMvc.perform(post("/rent").
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                content(jsonUtils.toJson(rentMoviesBody))).
                andExpect(status().is(HttpStatus.SC_OK)).andReturn().getResponse();

        final List<Movie> returnMoviesList = new ArrayList<>();
        returnMoviesList.add(new Movie(MATRIX_11));
        final Body returnMoviesBody = new Body(customerId, returnMoviesList);

        final MockHttpServletResponse response = this.mockMvc.perform(post("/return").
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                content(jsonUtils.toJson(returnMoviesBody))).
                andExpect(status().is(HttpStatus.SC_OK)).andReturn().getResponse();

        final String content = response.getContentAsString();
        final JsonNode jsonNode = jsonUtils.toJsonNode(content);
        assertEquals(40, Integer.parseInt(jsonNode.get("price").toString()));
    }

}




