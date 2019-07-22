package com.bignerdranch.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment  extends DialogFragment {

    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE); // get Date from arguments budle

        Calendar calendar = Calendar.getInstance(); // Gets a calendar using default timezone and locale
        calendar.setTime(date); // set calendar with Date date
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity()) // get LayoutInflater instance hooked up to the current context
                .inflate(R.layout.dialog_date, null); // inflate DatePicker from specified xml

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker); // return reference to first descendant view in hierarchy with given id
        mDatePicker.init(year, month, day, null); // initialize DatePicker

        return new AlertDialog.Builder(getActivity())
                .setView(v) // configure dialog to display DatePicker b/en title and button(s) (sets custom view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
