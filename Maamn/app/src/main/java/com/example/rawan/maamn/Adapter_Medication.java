package com.example.rawan.maamn;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Adapter_Medication extends BaseAdapter{

        ArrayList<Medication> arratlist;
        Context c;

        public Adapter_Medication(Context context, ArrayList arr) {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_medication, parent,
                    false);

            TextView textView_id = (TextView) convertView.findViewById(R.id.adapter_med_name);
            textView_id.setText(arratlist.get(position).getMed_name());

            TextView textView_duration = (TextView) convertView.findViewById(R.id.adapter_med_size);
            textView_duration.setText(arratlist.get(position).getDose());

            TextView textView_time = (TextView) convertView.findViewById(R.id.adapter_med_time);
            textView_time.setText(arratlist.get(position).getMedtime());

            ImageView img_bin = (ImageView) convertView.findViewById(R.id.adapter_img_bin);
            img_bin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(c)
                            .setTitle("حذف التذكير")
                            .setMessage("هل تريد حذف التذكير ؟")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    String rem_id = arratlist.get(position).getRem_ID();

                                    try {
                                        ConnectionClass connectionClass = new ConnectionClass();
                                        String sql = "delete from medication_reminder where rem_ID = '" + rem_id + "'";
                                        int result = connectionClass.st.executeUpdate(sql);

                                        if (result >0) {
                                            Toast.makeText(c, "تم الحذف بنجاح", Toast.LENGTH_LONG).show();
                                            arratlist.remove(position);
                                            notifyDataSetChanged();
                                        }
                                        else {
                                            Toast.makeText(c, "فشلت عملية الحذف", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    catch (Exception ex) {
                                        Log.d("Adapter_Medication", ex.getMessage());
                                    }

                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();

                }
            });

            return convertView;
        }

}