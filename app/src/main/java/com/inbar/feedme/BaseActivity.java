package com.inbar.feedme;

import android.content.Context;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = BaseActivity.this;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        configureToolbar();
    }

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                toolbar.setElevation(4);
        }
    }

    @Override
    public abstract boolean onCreateOptionsMenu(Menu menu);

    @Override
    public abstract boolean onOptionsItemSelected(MenuItem item);

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
        // TODO: implement
    }
}
