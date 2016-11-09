package com.asgard.smartnotes.listeners;

import android.view.View;
import com.asgard.smartnotes.adapters.RecyclerViewAdapter;


public class DeleteItemPosition implements View.OnClickListener {
   private RecyclerViewAdapter adapter;
   private int position;

    public int getPosition() {
        return position;
    }

    public DeleteItemPosition(RecyclerViewAdapter adapter, int position) {
        setAdapter(adapter);
        setPosition(position);
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    private void setAdapter(RecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    private void setPosition(int position) {
        this.position = position;
    }



    @Override
    public void onClick(View v) {
        adapter.deleteItem(position);
    }
}
