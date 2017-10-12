package com.masaibar.ormarxjavasample;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    private LayoutInflater mInflater;

    public ItemAdapter(@NonNull Context context, @LayoutRes int resource, List<Item> items) {
        super(context, resource, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        final Item item = getItem(position);

        ((TextView) view.findViewById(android.R.id.text1)).setText(item.text);

        return view;
    }
}
