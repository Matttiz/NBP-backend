package nbp.pl.model;

import lombok.Getter;

public class Rate {
    @Getter
    private String currency;
    @Getter
    private String code;
    @Getter
    private double mid;

    @Override
    public String toString() {
        return "Rate{" +
                "currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", mid=" + mid +
                '}';
    }
}