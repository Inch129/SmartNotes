package com.asgard.smartnotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.asgard.smartnotes.Importance;
import com.asgard.smartnotes.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNote extends AppCompatActivity {
    private EditText header;
    private EditText body;
    private Toolbar toolbar;
    private static Intent intent = new Intent();
    private Spinner spinner;
    private Button save;


    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return dateFormat.format(new Date());
    }

    private void initiateSpinner() {
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.importance_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);
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

        header.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (header.getText().length() == 32) {
                    header.setError("Слишком длинный заголовок");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String resultHeader = header.getText().toString();
                String resultBody = body.getText().toString();

                if (resultHeader.length() == 0) {
                    header.setError("Необходимо заполнить");
                    return;
                } else {
                    intent.putExtra("header", resultHeader);

                }
                if (resultBody.length() == 0) {
                    body.setError("Необходимо заполнить");
                    return;
                } else {
                    intent.putExtra("body", resultBody);
                }
                intent.putExtra("date", getDate());

                setResult(RESULT_OK, intent);

                finish();

            }
        });

    }


    private void initViews() {
        header = (EditText) findViewById(R.id.create_new_header);
        body = (EditText) findViewById(R.id.create_new_body);
        spinner = (Spinner) findViewById(R.id.new_spinner_note_importance);
        save = (Button) findViewById(R.id.btn_save);

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.new_note_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Новая заметка");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }


}
