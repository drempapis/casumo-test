package net.casumo.test.controller.response;

import java.io.Serializable;

public class RentMoviesResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int price;

    private int bonus;

    public RentMoviesResponse(final int price, final int bonus) {
        this.price = price;
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public int getPrice() {
        return price;
    }
}
