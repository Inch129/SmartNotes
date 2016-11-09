package com.asgard.smartnotes.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.asgard.smartnotes.Importance;
import com.asgard.smartnotes.StorageDatabaseHelper;
import com.asgard.smartnotes.activities.EditingNote;
import com.asgard.smartnotes.R;
import com.asgard.smartnotes.activities.ShowNote;
import com.asgard.smartnotes.listeners.DeleteItemPosition;
import com.asgard.smartnotes.models.Note;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKWallPostResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListHolder> {
    private List<Note> itemList;
    private Context context;
    private SQLiteDatabase database;
    private StorageDatabaseHelper dbHelper;

    private List<Note> getItemList() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }

        return itemList;
    }

    private void setListFromDatabase(List<Note> itemList) {

        for (Note item : itemList) {
            this.getItemList().add(item);

            notifyDataSetChanged();
        }

    }


    private void makePost(VKApi att, String msg) {
        VKParameters parameters = new VKParameters();
        //  parameters.put(VKApiConst.OWNER_ID, String.valueOf(ownerId));
        parameters.put(VKApiConst.ATTACHMENTS, att);
        parameters.put(VKApiConst.MESSAGE, msg);
        VKRequest post = VKApi.wall().post(parameters);
        post.setModelClass(VKWallPostResult.class);
        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {

            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(context, "Хана", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void addItem(Note note) {
        itemList.add(note);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        dbHelper = new StorageDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();

        int id = itemList.get(position).getId();

        String deleteId = String.valueOf(id);

        database.delete(StorageDatabaseHelper.TABLE_NOTES, "_id = ?", new String[]{deleteId});

        dbHelper.close();

        itemList.remove(position);

        notifyDataSetChanged();
    }


    public RecyclerViewAdapter(Context context, List<Note> list) {
        this.context = context;
        setListFromDatabase(list);
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View wordView = inflater.inflate(R.layout.list_item, parent, false);

        return new ListHolder(wordView);
    }


    @Override
    public void onBindViewHolder(final ListHolder holder, final int position) {
        dbHelper = new StorageDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();

        final Note item = getItemList().get(position);

        holder.getDelete().setOnClickListener(new DeleteItemPosition(this, position));

        switch (item.getImportance()) {

            case Importance.noMatter:
                holder.getItemImportance().setCardBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case Importance.green:
                holder.getItemImportance().setCardBackgroundColor(Color.parseColor("#4caf50"));
                break;
            case Importance.yellow:
                holder.getItemImportance().setCardBackgroundColor(Color.parseColor("#f8f32b"));
                break;
            case Importance.red:
                holder.getItemImportance().setCardBackgroundColor(Color.parseColor("#ff2400"));
                break;


        }

        holder.getShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        holder.getEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditingNote.class);
                intent.putExtra("header", getItemList().get(position).getHeader());
                intent.putExtra("body", getItemList().get(position).getBody());
                intent.putExtra("date", getItemList().get(position).getDate());
                context.startActivity(intent);


            }
        });

        holder.getHeader().setText(item.getHeader());

        ((View) holder.getHeader().getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowNote.class);
                intent.putExtra("header", getItemList().get(position).getHeader());
                intent.putExtra("body", getItemList().get(position).getBody());
                intent.putExtra("date", getItemList().get(position).getDate());
                intent.putExtra("index", getItemList().get(position).getId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return getItemList().size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        private ImageButton delete;
        private TextView header;
        private Button edit;
        private CardView itemImportance;
        private Button share;

        public Button getShare() {
            return share;
        }

        public void setShare(View share) {
            this.share = (Button) share;
        }

        CardView getItemImportance() {
            return itemImportance;
        }

        void setItemImportance(View itemImportance) {
            this.itemImportance = (CardView) itemImportance;
        }

        ImageButton getDelete() {
            return delete;
        }

        void setDelete(View delete) {
            this.delete = (ImageButton) delete;
        }

        Button getEdit() {
            return edit;
        }

        void setEdit(View edit) {
            this.edit = (Button) edit;
        }

        TextView getHeader() {
            return header;
        }

        void setHeader(View header) {
            this.header = (TextView) header;
        }

        ListHolder(View itemView) {
            super(itemView);
            setHeader(itemView.findViewById(R.id.header_item));
            setDelete(itemView.findViewById(R.id.btn_item_delete));
            setEdit(itemView.findViewById(R.id.btn_item_edit));
            setItemImportance(itemView.findViewById(R.id.itemBackground));
            setShare(itemView.findViewById(R.id.btn_share));
        }


    }

}