package net.casumo.test.inventory;

import net.casumo.test.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MoviesInventory {

    @Autowired
    private JsonUtils jsonUtils;

    private Map<UUID, MovieType> moviesInventoryType;

    private Map<UUID, Boolean> moviesAvailability;

    public MoviesInventory() {
        moviesInventoryType = new ConcurrentHashMap<>();
        moviesAvailability = new ConcurrentHashMap<>();
    }

    @PostConstruct
    public void inventoryInitialize() throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final String jsonInventory = IOUtils.toString(classLoader.getResourceAsStream("inventory/movies-inventory.json"), "UTF-8").replace("\n", "");
        final List<RegisteredMovie> registeredFilms = jsonUtils.deserializeAsList(jsonInventory, RegisteredMovie.class);

        for(RegisteredMovie movie : registeredFilms) {
            moviesInventoryType.put(movie.getId(), movie.getType());
            moviesAvailability.put(movie.getId(), Boolean.TRUE);
        }
    }

    public Map<UUID, MovieType> getMoviesInventoryType() {
        return moviesInventoryType;
    }

    public Map<UUID, Boolean> getMoviesAvailability() {
        return moviesAvailability;
    }
}
