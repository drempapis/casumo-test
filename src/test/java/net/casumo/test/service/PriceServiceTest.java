package net.casumo.test.service;

import net.casumo.test.controller.body.Movie;
import net.casumo.test.domain.Session;
import net.casumo.test.repository.MovieRepository;
import net.casumo.test.repository.SessionRepository;
import net.casumo.test.inventory.MovieType;
import net.casumo.test.util.Identifier;
import net.casumo.test.util.ServerTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static net.casumo.test.base.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class PriceServiceTest {

    private static final int ONE_DAY_TO_RENT = 1;
    private static final int TWO_DAYS_TO_RENT = 2;
    private static final int THREE_DAYS_TO_RENT = 3;
    private static final int FIVE_DAYS_TO_RENT = 5;
    private static final int SEVEN_DAYS_TO_RENT = 7;

    @InjectMocks
    private PriceService priceService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Test
    public void calculateRentingPrice_NewRelease_OneDay_Test() {
        final List<Movie> moviesList = Arrays.asList(new Movie(MATRIX_11, ONE_DAY_TO_RENT));
        doReturn(MovieType.NEW).when(movieRepository).getMovieType(any(UUID.class));
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(40, price);
    }

    @Test
    public void calculateRentingPrice_NewRelease_TwoDays_Test() {
        final List<Movie> moviesList = Arrays.asList(new Movie(MATRIX_11, TWO_DAYS_TO_RENT));
        doReturn(MovieType.NEW).when(movieRepository).getMovieType(any(UUID.class));
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(80, price);
    }

    @Test
    public void calculateRentingPrice_RegularRelease_OneDay_Test() {
        final List<Movie> moviesList = Arrays.asList(new Movie(SUPER_MAN, ONE_DAY_TO_RENT));
        doReturn(MovieType.REGULAR).when(movieRepository).getMovieType(any(UUID.class));
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(30, price);
    }

    @Test
    public void calculateRentingPrice_RegularRelease_ThreeDays_Test() {
        final List<Movie> moviesList = Arrays.asList(new Movie(SUPER_MAN, THREE_DAYS_TO_RENT));
        doReturn(MovieType.REGULAR).when(movieRepository).getMovieType(any(UUID.class));
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(30, price);
    }

    @Test
    public void calculateRentingPrice_RegularRelease_FiveDays_Test() {
        final List<Movie> moviesList = Arrays.asList(new Movie(SUPER_MAN, FIVE_DAYS_TO_RENT));
        doReturn(MovieType.REGULAR).when(movieRepository).getMovieType(any(UUID.class));
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(90, price);
    }

    @Test
    public void calculateRentingPrice_OldRelease_OneDay_Test() {
        final List<Movie> moviesList = Arrays.asList(new Movie(OUT_OF_AFRICA, ONE_DAY_TO_RENT));
        doReturn(MovieType.OLD).when(movieRepository).getMovieType(any(UUID.class));
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(30, price);
    }

    @Test
    public void calculateRentingPrice_OldRelease_FiveDays_Test() {
        final List<Movie> moviesList = Arrays.asList(new Movie(OUT_OF_AFRICA, FIVE_DAYS_TO_RENT));
        doReturn(MovieType.OLD).when(movieRepository).getMovieType(any(UUID.class));
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(30, price);
    }

    @Test
    public void calculateRentingPrice_OldRelease_SevenDays_Test() {
        final List<Movie> moviesList = Arrays.asList(new Movie(OUT_OF_AFRICA, SEVEN_DAYS_TO_RENT));
        doReturn(MovieType.OLD).when(movieRepository).getMovieType(any(UUID.class));
        int price = priceService.calculateRentingPrice(moviesList);
        assertEquals(90, price);
    }

    @Test
    public void calculateReturningPrice_NewRelease_No_ExtraDay_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(1));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(MATRIX_11, rentMovieTimeStamp, ONE_DAY_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.NEW).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(MATRIX_11));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(0, price);
    }

    @Test
    public void calculateReturningPrice_NewRelease_1_ExtraDay_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(2));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(MATRIX_11, rentMovieTimeStamp, ONE_DAY_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.NEW).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(MATRIX_11));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(40, price);
    }

    @Test
    public void calculateReturningPrice_NewRelease_2_ExtraDays_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(3));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(MATRIX_11, rentMovieTimeStamp, ONE_DAY_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.NEW).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(MATRIX_11));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(80, price);
    }

    @Test
    public void calculateReturningPrice_RegularRelease_OneDayToRent_NoExtraDays_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(1));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(SUPER_MAN, rentMovieTimeStamp, ONE_DAY_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.REGULAR).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(SUPER_MAN));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(0, price);
    }

    @Test
    public void calculateReturningPrice_RegularRelease_ThreeDayToRent_NoExtraDays_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(3));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(SUPER_MAN, rentMovieTimeStamp, THREE_DAYS_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.REGULAR).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(SUPER_MAN));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(0, price);
    }

    @Test
    public void calculateReturningPrice_RegularRelease_OneDayToRent_OneExtraDay_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(2));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(SUPER_MAN, rentMovieTimeStamp, ONE_DAY_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.REGULAR).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(SUPER_MAN));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(0, price);
    }

    @Test
    public void calculateReturningPrice_RegularRelease_TwoDaysToRent_TwoExtraDays_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(4));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(SUPER_MAN, rentMovieTimeStamp, TWO_DAYS_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.REGULAR).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(SUPER_MAN));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(30, price);
    }

    @Test
    public void calculateReturningPrice_RegularRelease_ThreeDaysToRent_OneExtraDay_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(4));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(SUPER_MAN, rentMovieTimeStamp, THREE_DAYS_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.REGULAR).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(SUPER_MAN));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(30, price);
    }

    @Test
    public void calculateReturningPrice_OldRelease_OneDayToRent_NoExtraDays_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(1));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(OUT_OF_AFRICA, rentMovieTimeStamp, ONE_DAY_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.OLD).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(OUT_OF_AFRICA));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(0, price);
    }

    @Test
    public void calculateReturningPrice_OldRelease_ThreeDayToRent_OneExtraDay_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(6));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(MATRIX_11, rentMovieTimeStamp, ONE_DAY_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.OLD).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(MATRIX_11));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(30, price);
    }

    @Test
    public void calculateReturningPrice_OldRelease_FiveDayToRent_NoExtraDay_Test() {
        final LocalDateTime rentMovieTime = ServerTimeUtils.getCurrentLocalDateTime();
        final Long rentMovieTimeStamp = ServerTimeUtils.getTimeInMillis(rentMovieTime.minusDays(5));
        final List<Movie> rentedMovieList = Arrays.asList(new Movie(MATRIX_11, rentMovieTimeStamp, FIVE_DAYS_TO_RENT));
        doReturn(new Session(rentedMovieList)).when(sessionRepository).getSession(any(UUID.class));
        doReturn(MovieType.OLD).when(movieRepository).getMovieType(any(UUID.class));
        final List<Movie> tobeReturnedMoviesList = Arrays.asList(new Movie(MATRIX_11));
        int price = priceService.calculateReturningPrice(Identifier.generateUUID(), tobeReturnedMoviesList);
        assertEquals(0, price);
    }

}
