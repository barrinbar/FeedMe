package com.inbar.feedme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.inbar.feedme.FeedMeContract.*;

import static com.inbar.feedme.FeedMeContract.FALSE;
import static com.inbar.feedme.FeedMeContract.LOGCAT_DB;
import static com.inbar.feedme.FeedMeContract.TRUE;

/**
 * Created by Inbar on 9/25/2016.
 */

public class FeedMeDataSource {

    // Database fields
    private SQLiteDatabase database = null;
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
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
            Log.d(LOGCAT_DB, "DB opened");
        }
    }
    public void recreateDB() {
        dbHelper.onCreate(database);
    }

    public void close() {
        dbHelper.close();
    }

    public Recipe createRecipe(Recipe recipe) {
        int insertId = getNextId(RecipeEntry.TABLE_NAME);

        ContentValues values = new ContentValues();
        values.put(RecipeEntry._ID, insertId);
        values.put(RecipeEntry.COL_NAME, recipe.getName());
        values.put(RecipeEntry.COL_TIME, recipe.getPrepTime());
        values.put(RecipeEntry.COL_THUMBNAIL, recipe.getThumbnail());
        values.put(RecipeEntry.COL_FAVORITE, recipe.isFavorite() ? TRUE : FALSE);
        database.insertOrThrow(RecipeEntry.TABLE_NAME, null, values);

        Recipe newRecipe = null;
        Cursor cursor = database.query(RecipeEntry.TABLE_NAME,
                recipeCols, RecipeEntry._ID + " = " + insertId, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            newRecipe = cursorToRecipe(cursor);
            Log.d(LOGCAT_DB, "Created recipe " + newRecipe.getName() + " with ID #" + newRecipe.getId());
        }
        else
            Log.d(LOGCAT_DB, "Couldn't create recipe " + newRecipe.getName());
        cursor.close();

        if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty())
            createIngredients(recipe);
        if (recipe.getInstructions() != null && !recipe.getInstructions().isEmpty())
            createInstructions(recipe);

        return getRecipe(insertId);
    }

    public ArrayList<Ingredient> createIngredients(Recipe recipe) {

       for (Ingredient ingredient : recipe.getIngredients())
           createIngredient(ingredient, recipe.getId());

        return getRecipeIngredients(recipe.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient, long recipeId) {
        int insertId = getNextId(IngredientEntry.TABLE_NAME);
        Ingredient newIngredient = null;

        ContentValues values = new ContentValues();
        values.put(IngredientEntry._ID, insertId);
        values.put(IngredientEntry.COL_INGREDIENT, ingredient.getIngredient());
        values.put(IngredientEntry.COL_RECIPE, recipeId);
        values.put(IngredientEntry.COL_AMOUNT, ingredient.getAmount());
        values.put(IngredientEntry.COL_UNITS, ingredient.getUnits().ordinal());
        database.insertOrThrow(IngredientEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(IngredientEntry.TABLE_NAME,
                ingredientCols, IngredientEntry._ID + " = " + insertId, null,
                null, null, null);
        if (cursor.moveToFirst())
            newIngredient = cursorToIngredient(cursor);

        cursor.close();

        return newIngredient;
    }

    public ArrayList<String> createInstructions(Recipe recipe) {

        for (String instruction : recipe.getInstructions()) {
            createInstruction(instruction, recipe.getId());
        }

        return getRecipeInstructions(recipe.getId());
    }

    public String createInstruction(String instruction, long recipeId) {
        int insertId = getNextId(InstructionEntry.TABLE_NAME);
        String newInstruction = null;

        ContentValues values = new ContentValues();
        values.put(InstructionEntry._ID, insertId);
        values.put(InstructionEntry.COL_INSTRUCTION, instruction);
        values.put(InstructionEntry.COL_RECIPE, recipeId);
        database.insertOrThrow(InstructionEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(InstructionEntry.TABLE_NAME,
                instructionCols, InstructionEntry._ID + " = " + insertId, null,
                null, null, null);
        if (cursor.moveToFirst())
            newInstruction = cursor.getString(2);
        cursor.close();

        return newInstruction;
    }

    public int updateRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(RecipeEntry.COL_NAME, recipe.getName());
        values.put(RecipeEntry.COL_TIME, recipe.getPrepTime());
        values.put(RecipeEntry.COL_THUMBNAIL, recipe.getThumbnail());
        values.put(RecipeEntry.COL_FAVORITE, recipe.isFavorite() ? TRUE : FALSE);
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
                IngredientEntry.COL_RECIPE + " = ? AND " + IngredientEntry.COL_INGREDIENT + " = ?",
                new String[] { String.valueOf(recipeId), ingredient.getIngredient() });
    }

    public int updateInstruction(String instruction, long recipeId) {
        ContentValues values = new ContentValues();
        values.put(InstructionEntry.COL_INSTRUCTION, instruction);
        values.put(InstructionEntry.COL_RECIPE, recipeId);
        return database.update(InstructionEntry.TABLE_NAME, values,
                InstructionEntry.COL_RECIPE + " = ? AND " + InstructionEntry.COL_INSTRUCTION + " = ?",
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
        ArrayList<Recipe> recipes = new ArrayList<>();

        /*Cursor cursor = database.query(RecipeEntry.TABLE_NAME,
                recipeCols, null, null, null, null, null);*/

        Cursor cursor = database.rawQuery("SELECT * FROM " + RecipeEntry.TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe recipe = getRecipe(cursor.getInt(cursor.getColumnIndex(RecipeEntry._ID)));

            /*Recipe recipe = cursorToRecipe(cursor);

            ArrayList<Ingredient> ingredients = getRecipeIngredients(recipe.getId());
            recipe.setIngredients(ingredients.isEmpty() ? null : ingredients);

            ArrayList<String> instructions = getRecipeInstructions(recipe.getId());
            recipe.setInstructions(instructions.isEmpty() ? null : instructions);*/

            recipes.add(recipe);
            cursor.moveToNext();
        }

        Log.d(LOGCAT_DB, "Selected " + recipes.size() + " recipes from DB");

        // Close the cursor
        cursor.close();
        return recipes;
    }

    public ArrayList<Ingredient> getRecipeIngredients(long recipeId) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        Cursor cursor = database.query(IngredientEntry.TABLE_NAME,
                ingredientCols, IngredientEntry.COL_RECIPE + " = " + recipeId,
                null, null, null, null);

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
                instructionCols, InstructionEntry.COL_RECIPE + " = " + recipeId,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String instruction = cursor.getString(cursor.getColumnIndex(InstructionEntry.COL_INSTRUCTION));
            instructions.add(instruction);
            cursor.moveToNext();
        }

        // Close the cursor
        cursor.close();
        return instructions;
    }

    public Recipe getRecipe(long recipeId) {
        Recipe recipe = null;
        Cursor cursor = database.query(RecipeEntry.TABLE_NAME,
            recipeCols, RecipeEntry._ID + " = " + recipeId, null,
            null, null, null);
        if (cursor.moveToFirst()) {
            recipe = cursorToRecipe(cursor);
            if (recipe != null) {
                recipe.setIngredients(getRecipeIngredients(recipe.getId()));
                recipe.setInstructions(getRecipeInstructions(recipe.getId()));
            }
        }
        cursor.close();
        return recipe;
    }

    private Recipe cursorToRecipe(Cursor cursor) {
        if (cursor == null || cursor.isClosed() || cursor.isAfterLast())
            return null;
        return new Recipe(cursor.getInt(cursor.getColumnIndex(RecipeEntry._ID)),
                cursor.getString(cursor.getColumnIndex(RecipeEntry.COL_NAME)),
                cursor.getInt(cursor.getColumnIndex(RecipeEntry.COL_TIME)),
                cursor.getInt(cursor.getColumnIndex(RecipeEntry.COL_THUMBNAIL)),
                cursor.getInt(cursor.getColumnIndex(RecipeEntry.COL_FAVORITE))== TRUE);
    }

    private Ingredient cursorToIngredient(Cursor cursor) {
        if (cursor == null || cursor.isClosed() || cursor.isAfterLast())
            return null;
        return new Ingredient(cursor.getString(cursor.getColumnIndex(IngredientEntry.COL_INGREDIENT)),
                cursor.getDouble(cursor.getColumnIndex(IngredientEntry.COL_AMOUNT)),
                Ingredient.Units.values()[cursor.getInt(cursor.getColumnIndex(IngredientEntry.COL_UNITS))]);
    }

    public int getNextId(String table) {
        int id = 0;
        final String MY_QUERY = "SELECT MAX(_id) AS _id FROM " + table;
        Cursor cursor = database.rawQuery(MY_QUERY, null);
        try {
            if (cursor.moveToFirst())
                id = cursor.getInt(0) + 1;
            else
                Log.d(LOGCAT_DB, "no id found");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return id;

    }
}
