package model;

import java.math.BigDecimal;
import java.util.Objects;

public class Lender {

    private String name;
    private BigDecimal rate;
    private Integer available;

    public Lender(final String name, final BigDecimal rate, final Integer available) {
        this.name = name;
        this.rate = rate;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Integer getAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lender lender = (Lender) o;
        return Objects.equals(name, lender.name) &&
                Objects.equals(rate, lender.rate) &&
                Objects.equals(available, lender.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rate, available);
    }
}
