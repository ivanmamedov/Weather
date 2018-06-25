package com.example.ivan.weather;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.weather.Strategy.Algoritm;
import com.example.ivan.weather.Strategy.PatternStrategy;
import com.example.ivan.weather.Strategy.StrategyCelsiy;
import com.example.ivan.weather.Strategy.StrategyCelvin;
import com.example.ivan.weather.Strategy.StrategyFarengeiht;

import java.util.ArrayList;


public class ShowFragment extends Fragment {

    Spinner spinnerCity;
    TextView textSeason;
    TextView result;
    TextView cityType;
    RadioGroup radioGroup;

    DBHelper dbHelper;

    ArrayList<CityNote> cityList;

    private double temper;
    PatternStrategy patternStrategy;

    public ShowFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false);

        spinnerCity = view.findViewById(R.id.spinner_city);
        textSeason = view.findViewById(R.id.text_season);
        result = view.findViewById(R.id.tv_result);
        cityType = view.findViewById(R.id.tv_type_city);
        radioGroup = view.findViewById(R.id.radiogroup);

        dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("cities", null, null,
                null, null, null, null);

        cityList = new ArrayList<>();
        ArrayList<String> listNames = new ArrayList<>();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("_id");
            int nameColIndex = c.getColumnIndex("name");
            int typeColIndex = c.getColumnIndex("type");
            int seasonColIndex = c.getColumnIndex("season");
            int firstColIndex = c.getColumnIndex("first_month");
            int secondColIndex = c.getColumnIndex("second_month");
            int thirdColIndex = c.getColumnIndex("third_month");
            do {
                cityList.add(new CityNote(c.getInt(idColIndex), c.getString(nameColIndex),
                        c.getString(typeColIndex), c.getString(seasonColIndex),
                        Double.parseDouble(c.getString(firstColIndex)),
                        Double.parseDouble(c.getString(secondColIndex)),
                        Double.parseDouble(c.getString(thirdColIndex))));
            } while (c.moveToNext());
        } else
            Log.d("TAGSUCCESS", "0 rows");
        c.close();

        for (int i = 0; i < cityList.size(); i++){
            listNames.add(i + 1 + ". " + cityList.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, listNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                temper = 0;
                textSeason.setText(cityList.get(i).getSeason());
                cityType.setText(cityList.get(i).getType());
                radioGroup.check(R.id.rb_cels);
                temper = (cityList.get(i).getFirstMonth() +
                        cityList.get(i).getSecondMonth() +
                        cityList.get(i).getThirdMonth()) / 3;
                temper = (double) Math.round(temper*100)/100;
                result.setText(String.valueOf(temper));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            String type = "celsiy";
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Algoritm algoritm;
                switch (i){            // Реализация паттерна Strategy
                    case R.id.rb_cels:
                        algoritm = new Algoritm(new StrategyCelsiy());
                        temper = (double) Math.round(algoritm.calculate(temper, type) * 100) / 100;
                        result.setText(String.valueOf(temper));
                        type = "celsiy";
                        break;
                    case R.id.rb_farenheight:
                        algoritm = new Algoritm(new StrategyFarengeiht());
                        temper = (double) Math.round(algoritm.calculate(temper, type) * 100) / 100;
                        result.setText(String.valueOf(temper));
                        type = "fahrenheit";
                        break;
                    case R.id.rb_kelvin:
                        algoritm = new Algoritm(new StrategyCelvin());
                        temper = (double) Math.round(algoritm.calculate(temper, type) * 100) / 100;
                        result.setText(String.valueOf(temper));
                        type = "kelvin";
                        break;
                }
            }
        });

        return view;
    }

}
