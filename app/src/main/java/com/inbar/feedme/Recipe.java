package com.inbar.feedme;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Inbar on 9/15/2016.
 */
public class Recipe {
    private String title;
    private int prepTime;
    private boolean favorite;
    private int thumbnail;
    private ArrayList<String> ingredients;
    private ArrayList<String> instructions;

    public Recipe() {
    }

    public Recipe(String title, int prepTime, int thumbnail) {
        this.title = title;
        this.prepTime = prepTime;
        this.thumbnail = thumbnail;
        this.favorite = false;
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

    public boolean getFavorite() {
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
        if (ingredients.contains(ingredient))
            ingredients.remove(ingredient);
        else
            return false;
        return true;
    }

    public boolean removeIngredient(int index) {
        if (ingredients.size() >= index)
            ingredients.remove(index);
        else
            return false;
        return true;
    }

    public boolean removeInstruction(String instruction) {
        if (ingredients.contains(instruction))
            ingredients.remove(instruction);
        else
            return false;
        return true;
    }

    public boolean removeInstruction(int index) {
        if (ingredients.size() >= index)
            ingredients.remove(index);
        else
            return false;
        return true;
    }
}
