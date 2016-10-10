package com.inbar.feedme;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import static com.inbar.feedme.FeedMeContract.LOGCAT_DB;

public class EditRecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private FeedMeDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        Intent intent  = getIntent();

        // Make sure user reached this acivity properly with an intended recipe
        if (intent.hasExtra("recipe")) {
            datasource = FeedMeDataSource.getInstance(this);
            datasource.open();

            Gson gson = new Gson();
            String strRecipe = getIntent().getStringExtra("recipe");
            recipe = gson.fromJson(strRecipe, Recipe.class);
            loadRecipe();
        }
        else
            new AlertDialog.Builder(this)
                    .setTitle("Recipe not found")
                    .setMessage("No recipe chosen\nplease choose a recipe from the list")
                    .setCancelable(false)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        datasource.close();
    }


    public void loadRecipe() {
        ImageView imgRecipePhoto = (ImageView)findViewById(R.id.edit_recipe_photo);
        TextView txtTitle = (TextView)findViewById(R.id.edit_recipe_title);
        TextView txtPrepMins = (TextView)findViewById(R.id.edit_prep_minutes);
        ImageView imgFavIcon = (ImageView)findViewById(R.id.img_fav);
        /*TextView txtIngredients = (TextView)findViewById(R.id.recipe_ingredients);
        TextView txtInstructions = (TextView)findViewById(R.id.recipe_instructions);*/

        // loading picture using Glide library
        Glide.with(this).load(recipe.getThumbnail())
                .centerCrop()
                .error(R.drawable.rec_default)
                .crossFade()
                .into(imgRecipePhoto);

        txtTitle.setText(recipe.getName());

        Resources res = getResources();
        String prepTime = res.getQuantityString(R.plurals.numberOfMinutes, recipe.getPrepTime(), recipe.getPrepTime());
        txtPrepMins.setText(prepTime);

        if (recipe.isFavorite())
            imgFavIcon.setImageResource(R.drawable.ic_favorite_holo_dark);
        else
            imgFavIcon.setImageResource(R.drawable.ic_favorite_border_holo_dark);

        Log.d(LOGCAT_DB, "Loading "+ recipe.getIngredients().size() +" ingredients");
        String ingredientsText = "";
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientsText += ingredient.toString() + "\n";
            Log.d(LOGCAT_DB, ingredient.toString());
        }
        //txtIngredients.setText(ingredientsText);

        //}

        //if (!recipe.getInstructions().isEmpty()) {
        Log.d(LOGCAT_DB, "Loading "+ recipe.getInstructions().size() +" instructions");
        //txtInstructions.setText("");
        for (String instruction: recipe.getInstructions()) {
            //txtInstructions.append(instruction + "\n");
            Log.d(LOGCAT_DB, instruction);
        }
        //}
    }
}
