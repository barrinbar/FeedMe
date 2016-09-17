package com.inbar.feedme;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class RecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
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


    public void gotoStory(View view) {
        // TODO: go to story activity
    }
}