package com.inbar.feedme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.inbar.feedme.FeedMeContract.*;

/**
 * Created by Inbar on 9/25/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FeedMe.db";

    // Table creation sql statement
    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE " + RecipeEntry.TABLE_NAME + "( "
            + RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RecipeEntry.COL_NAME + " TEXT NOT NULL, "
            + RecipeEntry.COL_TIME + " INTEGER NOT NULL, "
            + RecipeEntry.COL_THUMBNAIL + " INTEGER NOT NULL, "
            + RecipeEntry.COL_FAVORITE + "INTEGER DEFAULT + "+ FALSE +");";

    private static final String CREATE_TABLE_INGREDIENTS = "CREATE TABLE " + IngredientEntry.TABLE_NAME + "( "
            + IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + IngredientEntry.COL_RECIPE + " INTEGER NOT NULL "
            + "FOREIGN KEY(" + IngredientEntry.COL_RECIPE + ") REFERENCES "+ RecipeEntry.TABLE_NAME +"(" + RecipeEntry._ID + "), "
            + IngredientEntry.COL_INGREDIENT + " TEXT NOT NULL, "
            + IngredientEntry.COL_AMOUNT + " INTEGER, "
            + IngredientEntry.COL_UNITS + " TEXT);";

    private static final String CREATE_TABLE_INSTRUCTIONS = "CREATE TABLE " + InstructionsEntry.TABLE_NAME + "( "
            + InstructionsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + InstructionsEntry.COL_RECIPE + " INTEGER NOT NULL "
            + "FOREIGN KEY(" + InstructionsEntry.COL_RECIPE + ") REFERENCES "+ RecipeEntry.TABLE_NAME +"(" + RecipeEntry._ID + "), "
            + InstructionsEntry.COL_STEP + " INTEGER AUTOINCREMENT, "
            + InstructionsEntry.COL_DIRECTION + " TEXT);";

    private static final String CREATE_TABLE_STORIES = "CREATE TABLE " + StoryEntry.TABLE_NAME + "( "
            + StoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StoryEntry.COL_NAME + " TEXT NOT NULL, "
            + StoryEntry.COL_AUTHOR + " TEXT, "
            + StoryEntry.COL_THUMB + " TEXT, "
            + StoryEntry.COL_PARA + " TEXT);";

    private static final String CREATE_TABLE_STORIES_RECIPES = "CREATE TABLE " + StoryRecipeEntry.TABLE_NAME + "( "
            + StoryRecipeEntry.COL_RECIPE + " INTEGER "
            + "FOREIGN KEY(" + StoryRecipeEntry.COL_RECIPE  + ") REFERENCES "+ RecipeEntry.TABLE_NAME +"(" + RecipeEntry._ID + "), "
            + StoryRecipeEntry.COL_STORY  + " INTEGER "
            + "FOREIGN KEY(" + StoryRecipeEntry.COL_STORY + ") REFERENCES "+ StoryEntry.TABLE_NAME +"(" + StoryEntry._ID + "));";

    private static final String DELETE_TABLE_RECIPES =
            "DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME;

    private static final String DELETE_TABLE_INGREDIENTS =
            "DROP TABLE IF EXISTS " + IngredientEntry.TABLE_NAME;

    private static final String DELETE_TABLE_INSTRUCTIONS =
            "DROP TABLE IF EXISTS " + InstructionsEntry.TABLE_NAME;

    private static final String DELETE_TABLE_STORIES =
            "DROP TABLE IF EXISTS " + StoryEntry.TABLE_NAME;

    private static final String DELETE_TABLE_STORIES_RECIPIES =
            "DROP TABLE IF EXISTS " + StoryRecipeEntry.TABLE_NAME;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create all tables
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_INGREDIENTS);
        db.execSQL(CREATE_TABLE_INSTRUCTIONS);
        db.execSQL(CREATE_TABLE_STORIES);
        db.execSQL(CREATE_TABLE_STORIES_RECIPES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {

        // Drop older table if existed
        db.execSQL(DELETE_TABLE_STORIES_RECIPIES);
        db.execSQL(DELETE_TABLE_STORIES);
        db.execSQL(DELETE_TABLE_INSTRUCTIONS);
        db.execSQL(DELETE_TABLE_INGREDIENTS);
        db.execSQL(DELETE_TABLE_RECIPES);

        // Create all tables
        onCreate(db);
    }
}
