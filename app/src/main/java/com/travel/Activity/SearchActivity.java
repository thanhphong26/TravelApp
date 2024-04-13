package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.travel.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.handleSearchGlobal();
    }

    public void handleSearchGlobal() {
        Intent intent = getIntent();
        String query = intent.getStringExtra("search");

        SearchView searchView = (SearchView) findViewById(R.id.search_detail);
        searchView.setIconified(false);
        searchView.setQuery(query, false);
    }
}