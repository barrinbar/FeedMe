package com.inbar.feedme;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
