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
    private int id;
    private String name;
    private int prepTime;
    private boolean favorite;
    private int thumbnail;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> instructions;

    public Recipe(int id, String name, int prepTime, int thumbnail, boolean isFavorite,
                  ArrayList<Ingredient> ingredients, ArrayList<String> instructions) {
        this.id = id;
        this.name = name;
        this.prepTime = prepTime;
        this.thumbnail = thumbnail;
        this.favorite = isFavorite;
        this.ingredients = (ingredients == null) ? new ArrayList<Ingredient>() : ingredients;
        this.instructions = (instructions == null) ? new ArrayList<String>() : instructions;
    }

    public Recipe(int id, String name, int prepTime, int thumbnail, boolean isFavorite) {
        this(id, name, prepTime, thumbnail, isFavorite, null, null);
    }

    public Recipe(int id, String name, int prepTime, int thumbnail) {
        this(id, name, prepTime, thumbnail, false);
    }

    public Recipe(Recipe recipe) {
        this(recipe.getId(), recipe.getName(), recipe.getPrepTime(), recipe.getThumbnail(), recipe.isFavorite());
        this.setIngredients(recipe.getIngredients());
        this.setInstructions(recipe.getInstructions());
    }

    public Recipe() {
        this(-1, "", 0, R.drawable.rec_default, false);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
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

    @Override
    public String toString() {
        return "Recipe #" + id + " " + name + "\n" +
                "Prep time: " + prepTime + " mins" + "\n" +
                "Thumbnail id: " + thumbnail + "\n" +
                "Favorite: " + favorite + "\n" +
                "Ingredients: " + ingredients + "\n" +
                "Instructions" + instructions;
    }

    public boolean isEmpty() {
        return (name == "" &&
                prepTime == 0 &&
                thumbnail == R.drawable.rec_default &&
                (ingredients == null || ingredients.isEmpty()) &&
                (instructions == null || instructions.isEmpty()));
    }
}
