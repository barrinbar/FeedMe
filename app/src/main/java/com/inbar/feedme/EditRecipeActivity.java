package com.inbar.feedme;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.inbar.feedme.FeedMeContract.LOGCAT_DB;

public class EditRecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private FeedMeDataSource datasource;

    private ImageView imgRecipePhoto;
    private TextView txtTitle;
    private TextView txtPrepMins;
    private ImageView imgFavIcon;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView instructionsRecyclerView;

    private IngredientsAdapter ingredientsAdapter;
    private InstructionsAdapter instructionsAdapter;

    private boolean isCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        Intent intent  = getIntent();
        datasource = FeedMeDataSource.getInstance(this);
        datasource.open();

        imgRecipePhoto = (ImageView)findViewById(R.id.edit_recipe_photo);
        txtTitle = (TextView)findViewById(R.id.edit_recipe_title);
        txtPrepMins = (TextView)findViewById(R.id.edit_prep_minutes);
        imgFavIcon = (ImageView)findViewById(R.id.img_fav);


        // Make sure user reached this acivity properly with an intended recipe
        if (intent.hasExtra("recipe") && !getIntent().getStringExtra("recipe").isEmpty()) {

            isCreate = false;
            Gson gson = new Gson();
            String strRecipe = getIntent().getStringExtra("recipe");
            recipe = gson.fromJson(strRecipe, Recipe.class);
            loadRecipe();
        }
        // Otherwise - new recipe
        else {
            isCreate = true;
            newRecipe();
            loadRecipe();
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
        // loading picture using Glide library
        Glide.with(this).load(recipe.getThumbnail())
                .centerCrop()
                .error(R.drawable.rec_default)
                .crossFade()
                .into(imgRecipePhoto);

        imgRecipePhoto.setTag(recipe.getThumbnail());

        txtTitle.setText(recipe.getName());

        //Resources res = getResources();
        //String prepTime = res.getQuantityString(R.plurals.numberOfMinutes, recipe.getPrepTime(), recipe.getPrepTime());
        txtPrepMins.setText(recipe.getPrepTime());

        if (recipe.isFavorite()) {
            imgFavIcon.setImageResource(R.drawable.ic_favorite_holo_light);
            imgFavIcon.setTag(R.drawable.ic_favorite_holo_light);
        }
        else {
            imgFavIcon.setImageResource(R.drawable.ic_favorite_border_holo_light);
            imgFavIcon.setTag(R.drawable.ic_favorite_border_holo_light);
        }

        // Favorite
        imgFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((int)imgFavIcon.getTag() == R.drawable.ic_favorite_holo_light) {
                    imgFavIcon.setImageResource(R.drawable.ic_favorite_border_holo_light);
                    imgFavIcon.setTag(R.drawable.ic_favorite_border_holo_light);
                    recipe.setFavorite(false);
                }
                else {
                    imgFavIcon.setImageResource(R.drawable.ic_favorite_holo_light);
                    imgFavIcon.setTag(R.drawable.ic_favorite_holo_light);
                    recipe.setFavorite(true);
                }
            }
        });

        // Ingredients and instructions
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

    public void addIngredient(View view) {
        ingredientsAdapter.addNewIngredient();
    }

    public void addInstruction(View view) {
        instructionsAdapter.addNewInstruction();
    }

    public void saveChanges(View view) {

        recipe.setName(txtTitle.getText().toString().trim());
        recipe.setPrepTime(Integer.parseInt(txtPrepMins.toString().trim()));
        recipe.setThumbnail((int)imgRecipePhoto.getTag());
        recipe.setFavorite((int)imgFavIcon.getTag() == R.drawable.ic_favorite_holo_light);
        recipe.setIngredients(new ArrayList<>(ingredientsAdapter.getIngredients()));
        recipe.setInstructions(new ArrayList<>(instructionsAdapter.getInstructions()));

        if (recipe.isEmpty())
            new AlertDialog.Builder(this)
                    .setTitle("Empty entry")
                    .setMessage("The recipe is empty, do you want to discard this entry?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        else {
            boolean validInput = true;
            if (txtTitle.getText().toString().trim().isEmpty()) {
                txtTitle.setError("Recipe name must be set");
                validInput = false;
            }
            else {
                txtTitle.setError("");
            }

            if (txtPrepMins.getText().toString().trim().isEmpty() || recipe.getPrepTime() == 0) {
                txtPrepMins.setError("Recipe preparation time must be set");
                validInput = false;
            }
            else {
                txtPrepMins.setError("");
            }

            /*if (imgRecipePhoto.getTag() == R.drawable.rec_default) {

            }

            if (recipe.getIngredients().isEmpty()) {

            }
            if (recipe.getInstructions().isEmpty()) {

            }*/

            if (validInput) {
                if (isCreate) {
                    recipe.setId(datasource.getNextId(FeedMeContract.RecipeEntry.TABLE_NAME));
                    datasource.createRecipe(recipe);
                }
                else {
                    datasource.updateRecipe(recipe);
                }
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit without saving")
                .setMessage("Are you sur you want to leave and discard this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void newRecipe() {
        recipe = new Recipe();
    }
}
