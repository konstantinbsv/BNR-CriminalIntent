package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "com.bignerdranch.android.criminalintent.time";
    public static final String ARG_TIME = "time";

    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.setArguments(args);
        return timeFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_TIME); // get date from bundle arguments

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour   = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);


        // Inflate layout with activity's current instance of Inflater
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        // Get reference to TimePicker widget and initialize
        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        mTimePicker.setIs24HourView(DateFormat.is24HourFormat(getActivity()));
        mTimePicker.setCurrentHour(hour); // API <23
        mTimePicker.setCurrentMinute(minute);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hour = mTimePicker.getCurrentHour();
                        int minute = mTimePicker.getCurrentMinute();

                        Date date = new GregorianCalendar(year, month, day, hour, minute).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();

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
        intent.putExtra(EXTRA_TIME, date); // put date as an extra to the intent

        // get target fragment and pass data back to it
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
