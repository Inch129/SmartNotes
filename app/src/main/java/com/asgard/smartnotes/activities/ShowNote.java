package com.asgard.smartnotes.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.asgard.smartnotes.R;

import static android.R.color.white;


public class ShowNote extends AppCompatActivity {

    private TextView header;
    private TextView body;
    private TextView date;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note_activity);
        initViews();
        initToolbar();


    }

    private void initViews() {
        header = (TextView) findViewById(R.id.show_header);
        body = (TextView) findViewById(R.id.show_body);
        date = (TextView) findViewById(R.id.show_date);

        String headerText = getIntent().getExtras().getString("header");
        String bodyText = getIntent().getExtras().getString("body");
        String halfDateText = getIntent().getExtras().getString("date");

        StringBuilder dateText = new StringBuilder();
        dateText.append("Дата создания: ").append(halfDateText);

        header.setText(headerText);
        body.setText(bodyText);
        date.setText(dateText.toString());
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.show_note_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Заметка");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(white));
    }


}


