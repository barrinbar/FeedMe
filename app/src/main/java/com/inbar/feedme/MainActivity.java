package com.inbar.feedme;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.inbar.feedme.FeedMeContract.LOGCAT_DB;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipesAdapter adapter;
    private List<Recipe> recipeList;
    private FeedMeDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();

        datasource = FeedMeDataSource.getInstance(this);
        datasource.open();

        // If there are no recipes - create the DB
        if (datasource.getNextId(FeedMeContract.RecipeEntry.TABLE_NAME) == 1) {
            createDB();
        }

        configureRecyclerView();

        loadRecipes();

        // Set backdrop image
        try {
            Glide.with(this)
                    .load(R.drawable.rec_default)
                    .centerCrop()
                    .placeholder(R.drawable.feedme)
                    .crossFade()
                    .into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
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

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
          toolbar.setElevation(4);

        configureCollapsingToolbar();
    }

    /**
     * Config Recycler view with recipe cards
     */
    private void configureRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recipeList = new ArrayList<>();
        adapter = new RecipesAdapter(this, recipeList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void configureCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareRecipes() {
        int[] covers = new int[]{
                R.drawable.rec_israeli_salad,
                R.drawable.rec_scrambled_eggs,
                R.drawable.rec_pasta_egg,
                R.drawable.rec_kichri_rice,
                R.drawable.rec_kubbeh_hamusta,
                R.drawable.rec_red_kubbeh,
                R.drawable.rec_beet_kubbeh,
                R.drawable.rec_onion_soup,
                R.drawable.rec_mashed_fruits,
                R.drawable.rec_cheese_toast,
                R.drawable.rec_shakshuka};

        Recipe a = new Recipe(0, "Israeli Salad", 5, covers[0]);
        recipeList.add(a);

        a = new Recipe(1, "Scrambled Eggs", 5, covers[1]);
        recipeList.add(a);

        a = new Recipe(2, "Kushkush Pasta", 12, covers[2]);
        recipeList.add(a);

        a = new Recipe(3, "Kichri Rice", 30, covers[3]);
        recipeList.add(a);

        a = new Recipe(4, "Kubbeh Hamusta", 60, covers[4]);
        recipeList.add(a);

        a = new Recipe(5, "Red Kubbeh", 60, covers[5]);
        recipeList.add(a);

        a = new Recipe(6, "Beet Kubbeh", 60, covers[6]);
        recipeList.add(a);

        a = new Recipe(7, "Onion Soup", 120, covers[7]);
        recipeList.add(a);

        a = new Recipe(8, "Mashed Fruits", 15, covers[8]);
        recipeList.add(a);

        a = new Recipe(9, "Cheese Toast", 17, covers[9]);
        recipeList.add(a);

        a = new Recipe(10, "Shakshukah", 40, covers[10]);
        recipeList.add(a);

        adapter.notifyDataSetChanged();
    }

    private void createDB() {
        datasource.recreateDB();

        // Create recipes
        for (Recipe recipe : AllData.recipes) {
            datasource.createRecipe(recipe);
        }
        Log.d(LOGCAT_DB, AllData.recipes.size() + " Recipes created");

        // TODO: create stories
    }

    private void loadRecipes() {
        for (Recipe recipe : datasource.getAllRecipes())
            recipeList.add(recipe);
        Log.d(LOGCAT_DB, "Recipes loaded: " + recipeList.size());

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}