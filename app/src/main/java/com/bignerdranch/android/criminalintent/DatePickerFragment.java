package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment  extends DialogFragment {

    public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";
    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;
    private Button mPositiveButton;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE); // get Date from arguments budle

        Calendar calendar = Calendar.getInstance(); // Gets a calendar using default timezone and locale
        calendar.setTime(date); // set calendar with Date date
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR);
        final int minute = calendar.get(Calendar.MINUTE);

        View view = inflater // get LayoutInflater instance hooked up to the current context
                .inflate(R.layout.dialog_date, container, false); // inflate DatePicker from specified xml

        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_picker); // return reference to first descendant view in hierarchy with given id
        mDatePicker.init(year, month, day, null); // initialize DatePicker

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view) // configure dialog to display DatePicker b/en title and button(s) (sets custom view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // set OCL on positive button
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        int year  = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day   = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day, hour, minute).getTime(); // create new Date object with date from DatePicker
                        sendResult(Activity.RESULT_OK, date); // send date back to target
                    }
                })
                .create();

        mPositiveButton = (Button) view.findViewById(R.id.positive_button);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year  = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day   = mDatePicker.getDayOfMonth();
                Date date = new GregorianCalendar(year, month, day, hour, minute).getTime(); // create new Date object with date from DatePicker
                sendResult(Activity.RESULT_OK, date); // send date back to target
            }
        });

        return view;
    }

    /**
     * The method will put the Date passed to it on an intent as an extra and send the
     * intent to the target fragment by calling onActivityResult(int,int,Intent).
     * A result code is passed as well.
     * @param resultCode result code returned to target fragment
     * @param date Date to put on Intent
     */
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent(); // create new intent
        intent.putExtra(EXTRA_DATE, date); // put date as an extra to the intent

        // get target fragment and pass data back to it
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
