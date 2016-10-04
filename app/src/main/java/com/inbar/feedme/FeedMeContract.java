package com.inbar.feedme;

import android.provider.BaseColumns;

/**
 * Created by Inbar on 9/25/2016.
 */

public class FeedMeContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedMeContract() {}

    public static final int FALSE = 0;
    public static final int TRUE = 1;
    public static final String LOGCAT_DB = "FEEDME_DB";

    /* Recipes table */
    public static class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipes";
        public static final String COL_NAME = "name";
        public static final String COL_TIME = "prepTime";
        public static final String COL_THUMBNAIL = "thumbnail";
        public static final String COL_FAVORITE = "favorite";
    }

    /* Stories table */
    public static class StoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "stories";
        public static final String COL_NAME = "name";
        public static final String COL_AUTHOR = "author";
        public static final String COL_THUMB = "thumbnail";
        public static final String COL_PARA = "paragraph";
    }

    /* Ingredients table */
    public static class IngredientEntry implements BaseColumns {
        public static final String TABLE_NAME = "ingredients";
        public static final String COL_RECIPE = "recipe";
        public static final String COL_INGREDIENT= "ingredient";
        public static final String COL_AMOUNT= "amount";
        public static final String COL_UNITS = "units";
    }

    /* Instructions table */
    public static class InstructionEntry implements BaseColumns {
        public static final String TABLE_NAME = "instructions";
        public static final String COL_RECIPE = "recipe";
        public static final String COL_INSTRUCTION = "instruction";
    }

    /* Story Recipe table */
    public static class StoryRecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "story_recipe";
        public static final String COL_RECIPE = "recipe";
        public static final String COL_STORY = "story";
    }
}
