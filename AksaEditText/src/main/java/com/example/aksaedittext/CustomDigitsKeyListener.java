package com.example.aksaedittext;

import android.text.InputType;
import android.text.method.NumberKeyListener;

public class CustomDigitsKeyListener extends NumberKeyListener {

    private char[] mAccepted;
    private int inputType;

    public CustomDigitsKeyListener(char[] mAccepted, int inputType) {
        this.mAccepted = mAccepted;
        this.inputType = inputType;
    }

    public CustomDigitsKeyListener() {
    }

    public static CustomDigitsKeyListener getInstance(String accepted) {
        CustomDigitsKeyListener cust = new CustomDigitsKeyListener();
        cust.mAccepted = new char[accepted.length()];
        accepted.getChars(0, accepted.length(), cust.mAccepted, 0);
        return cust;
    }

    @Override
    protected char[] getAcceptedChars() {
        return mAccepted;
    }

    @Override
    public int getInputType() {
        if (inputType == InputType.TYPE_CLASS_NUMBER || inputType == InputType.TYPE_NUMBER_FLAG_DECIMAL)
            return InputType.TYPE_CLASS_NUMBER;
        else
            return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
    }
}
