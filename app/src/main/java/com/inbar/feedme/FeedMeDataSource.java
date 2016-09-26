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
    private String[] recipeCols = { RecipeEntry._ID,
            RecipeEntry.COL_NAME,
            RecipeEntry.COL_TIME,
            RecipeEntry.COL_THUMBNAIL,
            RecipeEntry.COL_FAVORITE
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

    public Recipe createRecipe(String name, int prepTime, int thumbnailID) {
        ContentValues values = new ContentValues();
        values.put(RecipeEntry.COL_NAME, name);
        values.put(RecipeEntry.COL_TIME, prepTime);
        values.put(RecipeEntry.COL_THUMBNAIL, thumbnailID);
        long insertId = database.insert(RecipeEntry.TABLE_NAME, null, values);
        Cursor cursor = database.query(RecipeEntry.TABLE_NAME,
                recipeCols, RecipeEntry._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Recipe newRecipe = cursorToRecipe(cursor);
        cursor.close();
        return newRecipe;
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

    public void deleteRecipe(Recipe recipe) {
        long id = recipe.getId();
        database.delete(RecipeEntry.TABLE_NAME, RecipeEntry._ID + " = ?",
                new String[] { String.valueOf(recipe.getId()) });
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> Recipes = new ArrayList<>();

        Cursor cursor = database.query(RecipeEntry.TABLE_NAME,
                recipeCols, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe Recipe = cursorToRecipe(cursor);
            Recipes.add(Recipe);
            cursor.moveToNext();
        }

        // Close the cursor
        cursor.close();
        return Recipes;
    }

    private Recipe cursorToRecipe(Cursor cursor) {
        return new Recipe(cursor.getLong(0), cursor.getString(1), cursor.getInt(2),
                cursor.getInt(3), cursor.getInt(4)==FeedMeContract.TRUE);
    }
}
