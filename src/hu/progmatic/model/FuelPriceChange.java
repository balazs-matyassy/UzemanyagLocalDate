package hu.progmatic.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class FuelPriceChange {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    public static final double EURHUF = 307.7;

    private LocalDate date;
    private int gasPrice;
    private int dieselPrice;

    public FuelPriceChange() {

    }

    public FuelPriceChange(LocalDate date, int gasPrice, int dieselPrice) {
        this.date = date;
        this.gasPrice = gasPrice;
        this.dieselPrice = dieselPrice;
    }

    public FuelPriceChange(String line) {
        String[] parts = line.split(";");
        this.date = LocalDate.parse(parts[0], FORMATTER);
        this.gasPrice = Integer.parseInt(parts[1]);
        this.dieselPrice = Integer.parseInt(parts[2]);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isLeapDay() {
        return date.isLeapYear()
                && date.getMonth() == Month.FEBRUARY
                && date.getDayOfMonth() == 24;
    }

    public int getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(int gasPrice) {
        this.gasPrice = gasPrice;
    }

    public double getGasPriceEUR() {
        return gasPrice / EURHUF;
    }

    public int getDieselPrice() {
        return dieselPrice;
    }

    public void setDieselPrice(int dieselPrice) {
        this.dieselPrice = dieselPrice;
    }

    public double getDieselPriceEUR() {
        return dieselPrice / EURHUF;
    }

    public int getDifference() {
        return Math.abs(gasPrice - dieselPrice);
    }

    public double getDifferenceEUR() {
        return Math.abs(getGasPriceEUR() - getDieselPriceEUR());
    }

    public int getDaysPassedSince(FuelPriceChange fuelPriceChange) {
        return Period.between(fuelPriceChange.date, date).getDays();
    }

    @Override
    public String toString() {
        return String.format(
                "%s;%d;%d",
                date.format(FORMATTER),
                gasPrice,
                dieselPrice
        );
    }

    public String toStringEUR() {
        return String.format(
                "%s;%.2f;%.2f",
                date.format(FORMATTER),
                getGasPriceEUR(),
                getDieselPriceEUR()
        );
    }
}
