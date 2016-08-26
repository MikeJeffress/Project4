package com.example.michaeljeffress.project4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.michaeljeffress.project4.R;

import java.util.List;

/**
 * Created by michaeljeffress on 8/25/16.
 */
public class CustomSquadListAdapter extends ArrayAdapter<String> {
    int resource;
    private InfoButtonClickListener infoListener;
    private DeleteButtonClickListener deleteListener;

    public interface DeleteButtonClickListener {
        void deleteButtonClickListener();
    }

    public interface InfoButtonClickListener {
        void infoButtonClickListener();
    }

    public void setDeleteButtonListener(DeleteButtonClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setInfoButtonClickListener(InfoButtonClickListener infoListener) {
        this.infoListener = infoListener;

    }

    public CustomSquadListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
        }

        TextView name = (TextView) view.findViewById(R.id.textview_main_squadlist_shooter_name);
        Button deleteButton = (Button) view.findViewById(R.id.button_main_squadlist_delete_shooter);
        Button infoButton = (Button) view.findViewById(R.id.button_main_squadlist_shooter_info);

        final String squadMember = getItem(position);

        name.setText(squadMember);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(squadMember);
                deleteListener.deleteButtonClickListener();
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoListener.infoButtonClickListener();
            }
        });

        return view;
    }


}