package com.example.hostel.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {

    public CustomEditText (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }

}