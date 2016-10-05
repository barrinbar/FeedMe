package com.inbar.feedme;

/**
 * Created by Inbar on 9/26/2016.
 */

public class Ingredient {

    public enum Units {
        CUPS,
        TBSPS,
        TSPS,
        DROPS,
        ML,
        LITRES,
        FLOZ,
        PINTS,
        QUARTS,
        GALLONS,
        MG,
        GRAMS,
        KG,
        OZ,
        POUNDS,
        INCHES,
        CM,
        DASHES,
        PINCHES,
        CEL,
        FAR,
        UNITS,
        BUNDLES
    }

    private String ingredient;
    private double amount;
    private Units units;

    public Ingredient (String ingredient, double amount, Units units) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.units = units;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return amount + " " + units.name() + " " + ingredient;
    }
}