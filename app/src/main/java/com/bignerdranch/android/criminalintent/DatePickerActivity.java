package com.bignerdranch.android.criminalintent;

import androidx.fragment.app.Fragment;

public class DatePickerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DatePickerFragment();
    }
}
