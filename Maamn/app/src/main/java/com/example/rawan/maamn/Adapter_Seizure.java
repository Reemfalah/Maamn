package com.example.rawan.maamn;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Seizure extends BaseAdapter{

        ArrayList<Seizure> arratlist;
        Context c;

        public Adapter_Seizure(Context context, ArrayList arr) {
            arratlist = arr;
            c = context;
        }

        @Override
        public int getCount() {
            return arratlist.size();
        }

        @Override
        public Object getItem(int position) {
            return arratlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_seizure, parent,
                    false);

           // TextView textView_id = (TextView) convertView.findViewById(R.id.adapter_sei_id);
//           textView_id.setText(""+position);
         //   textView_id.setText(arratlist.get(position).getSei_ID());

            TextView textView_duration = (TextView) convertView.findViewById(R.id.adapter_sei_duration);
            textView_duration.setText(arratlist.get(position).getDuration());

            TextView textView_time = (TextView) convertView.findViewById(R.id.adapter_sei_time);
            textView_time.setText(arratlist.get(position).getTime());

            TextView textView_date = (TextView) convertView.findViewById(R.id.adapter_sei_date);
            textView_date.setText(arratlist.get(position).getDate());

            TextView textView_description = (TextView) convertView.findViewById(R.id.adapter_sei_description);
            textView_description.setText(arratlist.get(position).getDescription());

            return convertView;
        }
    }