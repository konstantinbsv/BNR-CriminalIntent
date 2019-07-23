package com.bignerdranch.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

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
        Date date = (Date) getArguments().getSerializable("ARG_TIME"); // get date from bundle arguments

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour   = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        // Inflate layout with activity's current instance of Inflater
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        // Get reference to TimePicker widget and initialize
        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        mTimePicker.setCurrentHour(hour); // API <23
        mTimePicker.setHour(hour); // API >=23
        mTimePicker.setCurrentMinute(minute);
        mTimePicker.setMinute(minute);        ;

    }

}
