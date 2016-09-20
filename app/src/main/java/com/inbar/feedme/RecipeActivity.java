package com.inbar.feedme;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class RecipeActivity extends AppCompatActivity {

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent intent  = getIntent();

        // Make sure user reached this acivity properly with an intended recipe
        if (intent.hasExtra("recipe_id")) {
            long recipeID = intent.getLongExtra("recipe_id",0);
            Log.i("FEEDME", "Loading recipe #" + recipeID);
            loadRecipe(recipeID);
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
                if (item.getIcon().equals(R.drawable.ic_favorite_white_18dp)) {
                    item.setIcon(R.drawable.ic_favorite_border_white_18dp);
                    // TODO: remove from user favorites
                }
                else {
                    item.setIcon(R.drawable.ic_favorite_white_18dp);
                    // TODO: add to user favorites
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

    public void loadRecipe(long id) {
        ImageView imgRecipePhoto = (ImageView)findViewById(R.id.recipe_photo);
        TextView txtTitle = (TextView)findViewById(R.id.recipe_title);
        TextView txtPrepMins = (TextView)findViewById(R.id.prep_minutes);
        ImageView imgFavIcon = (ImageView)findViewById(R.id.img_fav);
    }

    public void gotoStory(View view) {
        // TODO: go to story activity
    }
}
