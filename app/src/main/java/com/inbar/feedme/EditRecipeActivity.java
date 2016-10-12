package com.inbar.feedme;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.inbar.feedme.FeedMeContract.LOGCAT_DB;

public class EditRecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private FeedMeDataSource datasource;
    private RecyclerView ingredientsRecyclerView;
    private IngredientsAdapter ingredientsAdapter;
    private RecyclerView instructionsRecyclerView;
    private InstructionsAdapter instructionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        Intent intent  = getIntent();
        datasource = FeedMeDataSource.getInstance(this);
        datasource.open();

        // Make sure user reached this acivity properly with an intended recipe
        if (intent.hasExtra("recipe")) {

            Gson gson = new Gson();
            String strRecipe = getIntent().getStringExtra("recipe");
            recipe = gson.fromJson(strRecipe, Recipe.class);
            loadRecipe();
        }
        // Otherwise - new recipe
        else {
            newRecipe();
            
            /*new AlertDialog.Builder(this)
                    .setTitle("Recipe not found")
                    .setMessage("No recipe chosen\nplease choose a recipe from the list")
                    .setCancelable(false)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();*/
        }
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
            imgFavIcon.setImageResource(R.drawable.ic_favorite_holo_light);
        else
            imgFavIcon.setImageResource(R.drawable.ic_favorite_border_holo_light);

        // Ingredients and instrucions
        configureRecyclerView();
        ingredientsAdapter.notifyDataSetChanged();
        instructionsAdapter.notifyDataSetChanged();
    }

    private void configureRecyclerView() {
        // Ingredients
        ingredientsRecyclerView = (RecyclerView) findViewById(R.id.edit_recipe_ingredients);
        ingredientsAdapter = new IngredientsAdapter(this, recipe.getIngredients());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        ingredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        // Instructions
        instructionsRecyclerView = (RecyclerView) findViewById(R.id.edit_recipe_instructions);
        instructionsAdapter = new InstructionsAdapter(this, recipe.getInstructions());

        instructionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        instructionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        instructionsRecyclerView.setAdapter(instructionsAdapter);
    }

    private void newRecipe() {
        // TODO: implement
    }

    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
