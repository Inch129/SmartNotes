package com.asgard.smartnotes.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.asgard.smartnotes.Importance;
import com.asgard.smartnotes.R;
import com.asgard.smartnotes.StorageDatabaseHelper;
import com.asgard.smartnotes.fragments.FragmentList;
import com.asgard.smartnotes.models.Note;
import com.vk.sdk.util.VKUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.color.white;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_NOTE_REQUEST = 1;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentList fragment;
    private StorageDatabaseHelper dbHelper;
    private List<Note> databaseList;
    private SQLiteDatabase database;
    private Cursor cursor;

    public static final String IMPORTANCE = "importance";
    public static final String HEADER = "header";
    public static final String BODY = "body";
    public static final String DATE = "date";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        System.out.println(Arrays.asList(fingerprints));
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new FragmentList();
        fragmentTransaction.replace(R.id.fragment_list_container, fragment);
        fragmentTransaction.commit();

        initToolbar();

    }

    public List<Note> initListFromDatabase() {
        dbHelper = new StorageDatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        databaseList = new ArrayList();

        cursor = database.query(dbHelper.TABLE_NOTES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            int dbIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
            int dbHeader = cursor.getColumnIndex(dbHelper.KEY_HEADER);
            int dbBody = cursor.getColumnIndex(dbHelper.KEY_BODY);

            @Importance
            int dbImportance = cursor.getColumnIndex(dbHelper.IMPORTANCE);

            int dbDate = cursor.getColumnIndex(dbHelper.DATE);

            do {
                @Importance
                int importance = cursor.getInt(dbImportance);

                databaseList.add(new Note(cursor.getString(dbHeader), cursor.getString(dbBody),
                        importance, cursor.getString(dbDate), cursor.getInt(dbIndex)));


            } while (cursor.moveToNext());
        } else

        cursor.close();
        dbHelper.close();

        return databaseList;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        if (resultCode == RESULT_OK) {

            @Importance
            int importance = data.getExtras().getInt(IMPORTANCE);

            String header = data.getExtras().getString(HEADER);
            String body = data.getExtras().getString(BODY);
            String date = data.getExtras().getString(DATE);

            contentValues.put(dbHelper.KEY_HEADER, header);
            contentValues.put(dbHelper.KEY_BODY, body);

            contentValues.put(dbHelper.IMPORTANCE, importance);
            Log.v("imp",""+importance);

            contentValues.put(dbHelper.DATE, date);

            long newItemIndex = database.insert(dbHelper.TABLE_NOTES, null, contentValues);

             fragment.addNewItem(header, body, importance, date, (int) newItemIndex);


            cursor.close();
        }
        dbHelper.close();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent = new Intent(this, NewNote.class);
                startActivityForResult(intent, NEW_NOTE_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Заметки");
        toolbar.setTitleTextColor(getResources().getColor(white));
    }


}
