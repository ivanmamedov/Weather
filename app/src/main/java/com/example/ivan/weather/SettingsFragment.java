package com.example.ivan.weather;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    DBHelper myDbHelper;
    ArrayList<CityNote> list;
    RVAdapter rvAdapter;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditFragment nextFrag = new EditFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, nextFrag)
                        .commit();
            }
        });

        recyclerView = view.findViewById(R.id.rv_settings);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        myDbHelper = new DBHelper(getContext());

        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor c = db.query("cities", null, null,
                null, null, null, null);

        list = new ArrayList<>();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("_id");
            int nameColIndex = c.getColumnIndex("name");
            int typeColIndex = c.getColumnIndex("type");
            int seasonColIndex = c.getColumnIndex("season");
            int firstColIndex = c.getColumnIndex("first_month");
            int secondColIndex = c.getColumnIndex("second_month");
            int thirdColIndex = c.getColumnIndex("third_month");
            do {
                list.add(new CityNote(c.getInt(idColIndex), c.getString(nameColIndex),
                        c.getString(typeColIndex), c.getString(seasonColIndex),
                        Double.parseDouble(c.getString(firstColIndex)),
                        Double.parseDouble(c.getString(secondColIndex)),
                        Double.parseDouble(c.getString(thirdColIndex))));
            } while (c.moveToNext());
            rvAdapter = new RVAdapter(list, getContext());
            recyclerView.setAdapter(rvAdapter);
            rvAdapter.notifyDataSetChanged();

        } else
            Log.d("TAGSUCCESS", "0 rows");
        c.close();



        return view;
    }



}
