package com.example.ivan.weather;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditFragment extends Fragment {

    EditText cityName;
    Spinner cityType;
    Spinner season;
    EditText firstMonth;
    EditText secondMonth;
    EditText thirdMonth;
    TextView tvFirstMonth;
    TextView tvSecondMonth;
    TextView tvThirdMonth;
    Button btnSave;
    Button btnCancel;

    DBHelper dbHelper;

    int idBundle;
    boolean isCreate;

    public EditFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        cityName = view.findViewById(R.id.et_city_name);
        cityType = view.findViewById(R.id.edit_spin_type);
        season = view.findViewById(R.id.edit_spin_season);
        firstMonth = view.findViewById(R.id.et_first_month);
        secondMonth = view.findViewById(R.id.et_second_month);
        thirdMonth = view.findViewById(R.id.et_third_month);
        tvFirstMonth = view.findViewById(R.id.tv_first_month);
        tvSecondMonth = view.findViewById(R.id.tv_second_month);
        tvThirdMonth = view.findViewById(R.id.tv_third_month);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);

        Bundle bundle = this.getArguments();
        try {
            idBundle = bundle.getInt("id", 0);
            cityName.setText(bundle.getString("name"));
            cityType.setSelection(getIndex(cityType, bundle.getString("type")));
            season.setSelection(getIndex(season, bundle.getString("season")));
            firstMonth.setText(String.valueOf(bundle.getDouble("first_month")));
            secondMonth.setText(String.valueOf(bundle.getDouble("second_month")));
            thirdMonth.setText(String.valueOf(bundle.getDouble("third_month")));
            isCreate = false;
        } catch (Exception ex) {
            isCreate = true;
        }

        dbHelper = new DBHelper(getContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cityName.getText().toString().equals("") &&
                        !firstMonth.getText().toString().equals("") &&
                        !secondMonth.getText().toString().equals("") &&
                        !thirdMonth.getText().toString().equals("")) {
                    ContentValues cv = new ContentValues();
                    // Объект для данных
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    cv.put("name", cityName.getText().toString());
                    cv.put("type", cityType.getSelectedItem().toString());
                    cv.put("season", season.getSelectedItem().toString());
                    cv.put("first_month", firstMonth.getText().toString());
                    cv.put("second_month", secondMonth.getText().toString());
                    cv.put("third_month", thirdMonth.getText().toString());

                    if (isCreate)
                        db.insert("cities", null, cv);
                    else
                        db.update("cities", cv, "_id = " + idBundle, null);

                    SettingsFragment nextFrag = new SettingsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, nextFrag)
                            .commit();
                } else
                    Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFragment nextFrag = new SettingsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, nextFrag)
                        .commit();
            }
        });

        season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) season.getSelectedItem();
                switch (s) {
                    case "Зима":
                        tvFirstMonth.setText("Декабрь");
                        tvSecondMonth.setText("Январь");
                        tvThirdMonth.setText("Февраль");
                        break;
                    case "Весна":
                        tvFirstMonth.setText("Март");
                        tvSecondMonth.setText("Апрель");
                        tvThirdMonth.setText("Май");
                        break;
                    case "Лето":
                        tvFirstMonth.setText("Июнь");
                        tvSecondMonth.setText("Июль");
                        tvThirdMonth.setText("Август");
                        break;
                    case "Осень":
                        tvFirstMonth.setText("Сентябрь");
                        tvSecondMonth.setText("Октябрь");
                        tvThirdMonth.setText("Ноябрь");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firstMonth.addTextChangedListener(new AddListenerOnTextChange(getContext(), firstMonth));
        secondMonth.addTextChangedListener(new AddListenerOnTextChange(getContext(), secondMonth));
        thirdMonth.addTextChangedListener(new AddListenerOnTextChange(getContext(), thirdMonth));

        return view;
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

}
