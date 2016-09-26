package com.inbar.feedme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.inbar.feedme.FeedMeContract.*;

/**
 * Created by Inbar on 9/25/2016.
 */

public class FeedMeDataSource {

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHandler dbHelper;
    private String[] recipeCols = {
            RecipeEntry._ID,
            RecipeEntry.COL_NAME,
            RecipeEntry.COL_TIME,
            RecipeEntry.COL_THUMBNAIL,
            RecipeEntry.COL_FAVORITE
    };
    private String[] ingredientCols = {
            IngredientEntry._ID,
            IngredientEntry.COL_RECIPE,
            IngredientEntry.COL_INGREDIENT,
            IngredientEntry.COL_AMOUNT,
            IngredientEntry.COL_UNITS
    };

    private String[] instructionCols = {
            InstructionEntry._ID,
            InstructionEntry.COL_RECIPE,
            InstructionEntry.COL_INSTRUCTION
    };

    public FeedMeDataSource(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Recipe createRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(RecipeEntry.COL_NAME, recipe.getName());
        values.put(RecipeEntry.COL_TIME, recipe.getPrepTime());
        values.put(RecipeEntry.COL_THUMBNAIL, recipe.getThumbnail());
        long insertId = database.insert(RecipeEntry.TABLE_NAME, null, values);

        createIngredients(recipe);
        createInstructions(recipe);

        return getRecipe(insertId);
    }

    public ArrayList<Ingredient> createIngredients(Recipe recipe) {

       for (Ingredient ingredient : recipe.getIngredients())
           createIngredient(ingredient, recipe.getId());

        return getRecipeIngredients(recipe.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient, long recipeId) {
        ContentValues values = new ContentValues();
        values.put(IngredientEntry.COL_INGREDIENT, ingredient.getIngredient());
        values.put(IngredientEntry.COL_RECIPE, recipeId);
        values.put(IngredientEntry.COL_AMOUNT, ingredient.getAmount());
        values.put(IngredientEntry.COL_UNITS, ingredient.getUnits().ordinal());
        long insertId = database.insert(IngredientEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(IngredientEntry.TABLE_NAME,
                ingredientCols, IngredientEntry._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Ingredient newIngredient = cursorToIngredient(cursor);
        cursor.close();
        return newIngredient;
    }

    public ArrayList<String> createInstructions(Recipe recipe) {

        for (String instruction: recipe.getInstructions()) {
            ContentValues values = new ContentValues();
            values.put(InstructionEntry.COL_INSTRUCTION, instruction);
            values.put(InstructionEntry.COL_RECIPE, recipe.getId());
            database.insert(IngredientEntry.TABLE_NAME, null, values);
        }
        return getRecipeInstructions(recipe.getId());
    }

    public String createInstruction(String instruction, long recipeId) {
        ContentValues values = new ContentValues();
        values.put(InstructionEntry.COL_INSTRUCTION, instruction);
        values.put(InstructionEntry.COL_RECIPE, recipeId);
        long insertId = database.insert(InstructionEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(InstructionEntry.TABLE_NAME,
                ingredientCols, InstructionEntry._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        String newInstruction = cursor.getString(2);
        cursor.close();
        return newInstruction;
    }

    public int updateRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(RecipeEntry.COL_NAME, recipe.getName());
        values.put(RecipeEntry.COL_TIME, recipe.getPrepTime());
        values.put(RecipeEntry.COL_THUMBNAIL, recipe.getThumbnail());
        values.put(RecipeEntry.COL_FAVORITE, recipe.isFavorite() ? 1 : 0);
        return database.update(RecipeEntry.TABLE_NAME, values,
                RecipeEntry._ID + " = ?", new String[] { String.valueOf(recipe.getId()) });
    }

    public int updateIngredient(Ingredient ingredient, long recipeId) {
        ContentValues values = new ContentValues();
        values.put(IngredientEntry.COL_INGREDIENT, ingredient.getIngredient());
        values.put(IngredientEntry.COL_RECIPE, recipeId);
        values.put(IngredientEntry.COL_AMOUNT, ingredient.getAmount());
        values.put(IngredientEntry.COL_UNITS, ingredient.getUnits().ordinal());
        return database.update(IngredientEntry.TABLE_NAME, values,
                IngredientEntry.COL_RECIPE + " = ? " + IngredientEntry.COL_INGREDIENT + " = ?",
                new String[] { String.valueOf(recipeId), ingredient.getIngredient() });
    }

    public int updateInstruction(String instruction, long recipeId) {
        ContentValues values = new ContentValues();
        values.put(InstructionEntry.COL_INSTRUCTION, instruction);
        values.put(InstructionEntry.COL_RECIPE, recipeId);
        return database.update(InstructionEntry.TABLE_NAME, values,
                InstructionEntry.COL_RECIPE + " = ? " + InstructionEntry.COL_INSTRUCTION + " = ?",
                new String[] { String.valueOf(recipeId), instruction });
    }

    public void deleteRecipe(Recipe recipe) {
        long id = recipe.getId();
        database.delete(IngredientEntry.TABLE_NAME, IngredientEntry.COL_RECIPE + " = ?",
                new String[] { String.valueOf(recipe.getId()) });
        database.delete(InstructionEntry.TABLE_NAME, InstructionEntry.COL_RECIPE + " = ?",
                new String[] { String.valueOf(recipe.getId()) });
        database.delete(RecipeEntry.TABLE_NAME, RecipeEntry._ID + " = ?",
                new String[] { String.valueOf(recipe.getId()) });
    }

    public void deleteIngredient(Ingredient ingredient, long recipeId) {
        database.delete(IngredientEntry.TABLE_NAME, IngredientEntry.COL_RECIPE + " = ? "
                        + IngredientEntry.COL_INGREDIENT + " = ?",
                new String[] { String.valueOf(recipeId), ingredient.getIngredient() });
    }

    public void deleteInstruction(String instruction, long recipeId) {
        database.delete(InstructionEntry.TABLE_NAME, InstructionEntry.COL_RECIPE + " = ? "
                        + InstructionEntry.COL_INSTRUCTION + " = ?",
                new String[] { String.valueOf(recipeId), instruction });
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        Cursor cursor = database.query(RecipeEntry.TABLE_NAME,
                recipeCols, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe recipe = cursorToRecipe(cursor);

            ArrayList<Ingredient> ingredients = getRecipeIngredients(recipe.getId());
            recipe.setIngredients(ingredients.isEmpty() ? null : ingredients);

            ArrayList<String> instructions = getRecipeInstructions(recipe.getId());
            recipe.setInstructions(instructions.isEmpty() ? null : instructions);

            recipes.add(recipe);
            cursor.moveToNext();
        }

        // Close the cursor
        cursor.close();
        return recipes;
    }

    public ArrayList<Ingredient> getRecipeIngredients(long recipeId) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        Cursor cursor = database.query(IngredientEntry.TABLE_NAME,
                ingredientCols, IngredientEntry.COL_RECIPE + " = ?",
                new String[] { String.valueOf(recipeId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ingredient ingredient = cursorToIngredient(cursor);
            ingredients.add(ingredient);
            cursor.moveToNext();
        }

        // Close the cursor
        cursor.close();
        return ingredients;
    }

    public ArrayList<String> getRecipeInstructions(long recipeId) {
        ArrayList<String> instructions = new ArrayList<>();

        Cursor cursor = database.query(InstructionEntry.TABLE_NAME,
                instructionCols, InstructionEntry.COL_RECIPE + " = ?",
                new String[] { String.valueOf(recipeId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String instruction = cursor.getString(2);
            instructions.add(instruction);
            cursor.moveToNext();
        }

        // Close the cursor
        cursor.close();
        return instructions;
    }

    public Recipe getRecipe(long recipeId) {
        Cursor cursor = database.query(RecipeEntry.TABLE_NAME,
            recipeCols, RecipeEntry._ID + " = " + recipeId, null,
            null, null, null);
        cursor.moveToFirst();
        Recipe recipe = cursorToRecipe(cursor);
        cursor.close();
        recipe.setIngredients(getRecipeIngredients(recipe.getId()));
        recipe.setInstructions(getRecipeInstructions(recipe.getId()));
        return recipe;
    }

        private Recipe cursorToRecipe(Cursor cursor) {
    return new Recipe(cursor.getLong(0), cursor.getString(1), cursor.getInt(2),
            cursor.getInt(3), cursor.getInt(4)==FeedMeContract.TRUE);
    }

    private Ingredient cursorToIngredient(Cursor cursor) {
        return new Ingredient(cursor.getString(0), cursor.getDouble(1),
                Ingredient.Units.values()[cursor.getInt(2)]);
    }
}
