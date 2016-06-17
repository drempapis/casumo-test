package net.casumo.test.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RegisteredMovie {

    private UUID id;

    private String name;

    private MovieType type;

    @JsonCreator
    public RegisteredMovie(@JsonProperty("uuid") final UUID id,
                           @JsonProperty("name") final String name,
                           @JsonProperty("type") final MovieType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MovieType getType() {
        return type;
    }
}
