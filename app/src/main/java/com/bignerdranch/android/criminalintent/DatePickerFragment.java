package com.bignerdranch.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

public class DatePickerFragment  extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()) // get LayoutInflater instance hooked up to the current context
                .inflate(R.layout.dialog_date, null); // inflate DatePicker from specified xml

        return new AlertDialog.Builder(getActivity())
                .setView(v) // configure dialog to display DatePicker b/en title and button(s) (sets custom view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
