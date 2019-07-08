package com.example.jsonuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    private List<Data> dataList;
    private Context context;

    public CustomAdapter(Context context, List<Data> dataList) {
        super(context, R.layout.custom_list, dataList);
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View listView = layoutInflater.inflate(R.layout.custom_list, null, true);

        TextView textView = listView.findViewById(R.id.textViewData);
        TextView textViewTwo = listView.findViewById(R.id.textViewDataiki);

        Data data = dataList.get(position);

        textView.setText(data.get_BinaryCode());
        textViewTwo.setText(data.get_CountryName());

        return listView;
    }
}
