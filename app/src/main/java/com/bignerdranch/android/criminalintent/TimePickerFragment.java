package com.bignerdranch.android.criminalintent;

import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "com.bignerdranch.android.criminalintent.time";
    public static final String ARG_TIME = "time";

    private TimePicker mTimePicker;

}
