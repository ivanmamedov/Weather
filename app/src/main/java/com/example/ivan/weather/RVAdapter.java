package com.example.ivan.weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder>{

    private ArrayList<CityNote> list;
    private Context context;
    AlertDialog.Builder dialog;

    public RVAdapter (ArrayList<CityNote> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_settings, parent,false);
        return new RVViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RVViewHolder holder, final int position) {
        holder.cityName.setText(list.get(position).getName());
        holder.season.setText(list.get(position).getSeason());
        final int id = list.get(position).getId();
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Удалить запись?");
                dialog.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        DBHelper dbHelper = new DBHelper(context);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("cities", "_id = " + id, null);
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new EditFragment();
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("id", list.get(position).getId());
                bundle.putString("name", list.get(position).getName());
                bundle.putString("type", list.get(position).getType());
                bundle.putString("season", list.get(position).getSeason());
                bundle.putDouble("first_month", list.get(position).getFirstMonth());
                bundle.putDouble("second_month", list.get(position).getSecondMonth());
                bundle.putDouble("third_month", list.get(position).getThirdMonth());
                fragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.content, fragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RVViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView cityName;
        TextView season;
        ImageView btnEdit;
        ImageView btnDelete;
        public RVViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_settings);
            cityName = itemView.findViewById(R.id.text_city);
            season = itemView.findViewById(R.id.text_season);
            btnEdit = itemView.findViewById(R.id.image_edit);
            btnDelete = itemView.findViewById(R.id.image_delete);
        }
    }
}
