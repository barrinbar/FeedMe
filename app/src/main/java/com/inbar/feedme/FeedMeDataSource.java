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

    private static FeedMeDataSource instance;

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

    private FeedMeDataSource(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public static synchronized FeedMeDataSource getInstance(Context context)
    {
        if (instance == null)
            instance = new FeedMeDataSource(context);

        return instance;
    }

    public void open() throws SQLException {
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
            Log.d(LOGCAT_DB, "DB opened");
        }
    }
    public void recreateDB()
    {
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
            newRecipe.setIngredients(createIngredients(insertId, recipe.getIngredients()));
        if (recipe.getInstructions() != null && !recipe.getInstructions().isEmpty())
            newRecipe.setInstructions(createInstructions(insertId, recipe.getInstructions()));

        newRecipe = getRecipe(insertId);
        Log.d(LOGCAT_DB, "Finished creating recipe: " + newRecipe.toString());
        return newRecipe;
    }

    public ArrayList<Ingredient> createIngredients(int recipeId, ArrayList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients)
           createIngredient(ingredient, recipeId);

        return getRecipeIngredients(recipeId);
    }

    public Ingredient createIngredient(Ingredient ingredient, int recipeId) {
        int insertId = getNextId(IngredientEntry.TABLE_NAME);

        ContentValues values = new ContentValues();
        values.put(IngredientEntry._ID, insertId);
        values.put(IngredientEntry.COL_INGREDIENT, ingredient.getIngredient());
        values.put(IngredientEntry.COL_RECIPE, recipeId);
        values.put(IngredientEntry.COL_AMOUNT, ingredient.getAmount());
        values.put(IngredientEntry.COL_UNITS, ingredient.getUnits().ordinal());
        database.insertOrThrow(IngredientEntry.TABLE_NAME, null, values);

        return getIngredient(insertId);
    }

    public ArrayList<String> createInstructions(int recipeId, ArrayList<String> instructions) {

        for (String instruction : instructions) {
            createInstruction(instruction, recipeId);
        }

        return getRecipeInstructions(recipeId);
    }

    public String createInstruction(String instruction, int recipeId) {
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
        if (cursor.moveToFirst()) {
            newInstruction = cursor.getString(cursor.getColumnIndex(InstructionEntry.COL_INSTRUCTION));
            Log.d(LOGCAT_DB, "Created instruction " + newInstruction + " with ID #" + insertId);
        }
        else
            Log.d(LOGCAT_DB, "Couldn't create instruction " + instruction);

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

    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        //Cursor cursor = database.rawQuery("SELECT * FROM " + RecipeEntry.TABLE_NAME, null);
        Cursor cursor = database.query(RecipeEntry.TABLE_NAME, recipeCols, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe recipe = getRecipe(cursor.getInt(cursor.getColumnIndex(RecipeEntry._ID)));
            recipes.add(recipe);
            cursor.moveToNext();
        }

        Log.d(LOGCAT_DB, "Selected " + recipes.size() + " recipes from DB");

        // Close the cursor
        cursor.close();
        return recipes;
    }

    public Ingredient getIngredient(int ingredientId) {
        Ingredient newIngredient = null;
        Cursor cursor = database.query(IngredientEntry.TABLE_NAME,
                ingredientCols, IngredientEntry._ID + " = " + ingredientId, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            newIngredient = cursorToIngredient(cursor);
        }
        else
            Log.e(LOGCAT_DB, "Couldn't create ingredient " + ingredientId);

        cursor.close();
        return newIngredient;

    }

    public ArrayList<Ingredient> getRecipeIngredients(int recipeId) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        Cursor cursor = database.query(IngredientEntry.TABLE_NAME,
                ingredientCols, IngredientEntry.COL_RECIPE + " = " + recipeId, null,
                null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ingredients.add(getIngredient(cursor.getInt(cursor.getColumnIndex(IngredientEntry._ID))));
            cursor.moveToNext();
        }

        // Close the cursor
        cursor.close();
        return ingredients;
    }

    public ArrayList<String> getRecipeInstructions(int recipeId) {
        ArrayList<String> instructions = new ArrayList<>();

        Cursor cursor = database.query(InstructionEntry.TABLE_NAME,
                instructionCols, InstructionEntry.COL_RECIPE + " = " + recipeId,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            instructions.add(cursor.getString(cursor.getColumnIndex(InstructionEntry.COL_INSTRUCTION)));
            cursor.moveToNext();
        }

        // Close the cursor
        cursor.close();
        return instructions;
    }

    public Recipe getRecipe(int recipeId) {
        Recipe recipe = null;
        Cursor cursor = database.query(RecipeEntry.TABLE_NAME,
            recipeCols, RecipeEntry._ID + " = " + recipeId, null,
            null, null, null);
        if (cursor.moveToFirst()) {
            recipe = cursorToRecipe(cursor);
        }
        cursor.close();
        if (recipe != null) {
            ArrayList<Ingredient> ingredients = getRecipeIngredients(recipe.getId());
            recipe.setIngredients(ingredients);
            recipe.setInstructions(getRecipeInstructions(recipe.getId()));
        }

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
