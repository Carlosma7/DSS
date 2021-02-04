package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

public class MobileListAdapter extends ArrayAdapter<Mobile> {

    private int typeAdapter;

    public MobileListAdapter(Context context, List<Mobile> objects, int type) {
        super(context, 0, objects);
        typeAdapter = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Mobile m = getItem(position);

        if(convertView == null){
            if (typeAdapter == 0){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.mobile_item, parent, false);
            }else{
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.mobile_item_client, parent, false);
            }
        }

        TextView idText = convertView.findViewById(R.id.text_id);
        TextView brandText = convertView.findViewById(R.id.text_brand);
        TextView modelText = convertView.findViewById(R.id.text_model);
        TextView osText = convertView.findViewById(R.id.text_os);
        TextView yearText = convertView.findViewById(R.id.text_year);
        TextView colorText = convertView.findViewById(R.id.text_color);
        TextView priceText = convertView.findViewById(R.id.text_price);

        if (typeAdapter == 0) {
            idText.setText(Long.toString(m.getId()) + ".");
        }
        brandText.setText(m.getBrand());
        modelText.setText(m.getModel());
        osText.setText(m.getOS());
        yearText.setText(Integer.toString(m.getYear()));
        colorText.setText(m.getColor());
        priceText.setText(Integer.toString(m.getPrice()) + "€");
        return convertView;
    }
}
