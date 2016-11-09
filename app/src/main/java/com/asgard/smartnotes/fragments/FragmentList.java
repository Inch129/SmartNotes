package com.asgard.smartnotes.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgard.smartnotes.Importance;
import com.asgard.smartnotes.R;
import com.asgard.smartnotes.SpaceItems;
import com.asgard.smartnotes.activities.MainActivity;
import com.asgard.smartnotes.adapters.RecyclerViewAdapter;
import com.asgard.smartnotes.models.Note;

import java.util.List;

public class FragmentList extends Fragment {
    private View view;
    private RecyclerViewAdapter adapter;
    private RecyclerView listNotes;
    private List<Note> databaseList;

    private void initRecycler() {
        databaseList = ((MainActivity)getActivity()).initListFromDatabase();
        listNotes = (RecyclerView) view.findViewById(R.id.fragment_list_container);
        adapter = new RecyclerViewAdapter(getContext(), databaseList);
        listNotes.setLayoutManager(new LinearLayoutManager(getActivity()));
        listNotes.setAdapter(adapter);
        listNotes.addItemDecoration(new SpaceItems(25));
        //  adapter.setListFromDatabase(databaseList);


    }


    public void addNewItem(String header, String body, @Importance int importance, String date, int index) {

        adapter.addItem(new Note(header, body, importance, date, index));

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        initRecycler();
        setRetainInstance(true);
        return view;
    }

}
