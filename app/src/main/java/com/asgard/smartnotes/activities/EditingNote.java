package com.asgard.smartnotes.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.asgard.smartnotes.Importance;
import com.asgard.smartnotes.R;

import java.io.File;
import java.io.FileOutputStream;

import static android.R.color.white;

public class EditingNote extends AppCompatActivity {
    private EditText header;
    private EditText body;
    private Spinner spinner;
    private int importance;
    private String headerText;
    private Toolbar toolbar;
    private String bodyText;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editing_note);

        initToolbar();
        initViews();
        initiateSpinner();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                intent = new Intent();
                switch (spinner.getSelectedItemPosition()) {

                    case 0:
                        intent.putExtra("importance", Importance.noMatter);
                        break;
                    case 1:
                        intent.putExtra("importance", Importance.green);
                        break;
                    case 2:
                        intent.putExtra("importance", Importance.yellow);
                        break;
                    case 3:
                        intent.putExtra("importance", Importance.red);
                        break;
                    default:
                        intent.putExtra("importance", Importance.noMatter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void initiateSpinner() {
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.importance_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.edit_note_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(white));
        getSupportActionBar().setTitle("Редактирование");
    }

    private void initViews() {

        header = (EditText) findViewById(R.id.edit_header);
        body = (EditText) findViewById(R.id.edit_body);
        spinner = (Spinner) findViewById(R.id.edit_spinner);

        headerText = getIntent().getExtras().getString(MainActivity.HEADER);
        bodyText = getIntent().getExtras().getString(MainActivity.BODY);

        importance = getIntent().getExtras().getInt("importance");

        header.setText(headerText);
        body.setText(bodyText);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.importFile:
                String FILENAME = "Note";
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(FILENAME, MODE_PRIVATE);
                    outputStream.write(headerText.getBytes());
                    outputStream.write(bodyText.getBytes());

                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return true;
            case R.id.delete_note:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}