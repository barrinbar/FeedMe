package com.inbar.feedme;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent intent  = getIntent();

        // Make sure user reached this acivity properly with an intended recipe
        if (intent.hasExtra("recipe")) {
            Gson gson = new Gson();
            String strRecipe = getIntent().getStringExtra("recipe");
            recipe = gson.fromJson(strRecipe, Recipe.class);
            Log.i("FEEDME", "Loading recipe " + recipe.getTitle());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe, menu);
        // Associate searchable configuration with the SearchView
        /*SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.miSearch).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;*/


        /*final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.miSearch));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_favourite: {
                if (recipe.isFavorite()) {
                    item.setIcon(R.drawable.ic_favorite_border_white_18dp);
                    item.setTitle(R.string.remove_from_favorites);
                    recipe.setFavorite(false);
                }
                else {
                    item.setIcon(R.drawable.ic_favorite_white_18dp);
                    item.setTitle(R.string.add_to_favorites);
                    recipe.setFavorite(true);
                }
                return true;
            }
            case R.id.miSearch: {
                return true;
            }
            case R.id.miShare: {
                return true;
            }

            default:
                return false;
        }
    }

    public void loadRecipe() {
        ImageView imgRecipePhoto = (ImageView)findViewById(R.id.recipe_photo);
        TextView txtTitle = (TextView)findViewById(R.id.recipe_title);
        TextView txtPrepMins = (TextView)findViewById(R.id.prep_minutes);
        ImageView imgFavIcon = (ImageView)findViewById(R.id.img_fav);

        // loading picture using Glide library
        Glide.with(this).load(recipe.getThumbnail())
                .centerCrop()
                .placeholder(R.drawable.feedme)
                .error(R.drawable.rec_default)
                .crossFade()
                .into(imgRecipePhoto);

        txtTitle.setText(recipe.getTitle());

        Resources res = getResources();
        String prepTime = res.getQuantityString(R.plurals.numberOfMinutes, recipe.getPrepTime(), recipe.getPrepTime());
        txtPrepMins.setText(prepTime);

        if (recipe.isFavorite())
            imgFavIcon.setImageResource(R.drawable.ic_favorite_holo_dark);
        else
            imgFavIcon.setImageResource(R.drawable.ic_favorite_border_holo_dark);
    }

    public void gotoStory(View view) {
        // TODO: go to story activity
    }
}
