package com.example.ivan.weather;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class AddListenerOnTextChange implements TextWatcher {
    private Context mContext;
    EditText mEditText;

    public AddListenerOnTextChange(Context context, EditText editText) {
        super();
        this.mContext = context;
        this.mEditText= editText;
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEditText.getText().toString().equals("."))
            mEditText.setText("");
    }
}
