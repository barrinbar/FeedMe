package com.inbar.feedme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Inbar on 9/15/2016.
 */
public class Recipe implements Serializable {
    private long id;
    private String title;
    private int prepTime;
    private boolean favorite;
    private int thumbnail;
    private ArrayList<String> ingredients;
    private ArrayList<String> instructions;

    public Recipe(long id, String title, int prepTime, int thumbnail) {
        this.id = id;
        this.title = title;
        this.prepTime = prepTime;
        this.thumbnail = thumbnail;
        this.favorite = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    public void addInstruction(String instruction) {
        instructions.add(instruction);
    }

    public boolean removeIngredient(String ingredient) {
        return ingredients.remove(ingredient);
    }

    public boolean removeIngredient(int index) {
        return (ingredients.remove(index) != null);
    }

    public boolean removeInstruction(String instruction) {
        return ingredients.remove(instruction);
    }

    public boolean removeInstruction(int index) {
        return ingredients.remove(index) != null;
    }
}
