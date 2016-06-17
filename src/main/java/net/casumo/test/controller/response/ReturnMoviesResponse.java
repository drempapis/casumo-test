package net.casumo.test.controller.response;

import java.io.Serializable;

public class ReturnMoviesResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int price;

    public ReturnMoviesResponse(final int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
