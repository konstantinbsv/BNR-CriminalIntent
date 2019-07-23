package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

import static android.widget.CompoundButton.*;  // Imports all static members from CompoundButton type

public class CrimeFragment extends Fragment {

    private static String ARG_CRIME_ID = "crime_id";
    private static String DIALOG_TIME = "DialogTime";
    private static String DIALOG_DATE = "DialogDate";   // parameter to uniquely identify DialogFragment
                                                        // in FragmentManager's list
    private static int REQUEST_DATE = 0; // request code for date to id DatePickerFragment reporting back
    private static int REQUEST_TIME = 1; // request code for date to id TimePickerFragment reporting back

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;

    /**
     * Call this method when you need a new CrimeFragment
     * <p>
     * Creates new CrimeFragment and attaches an Bundle containing
     * crimeId as an argument
     * </p>
     * @param crimeId crime UUID
     * @return CrimeFragment with attached crime UUID as args Bundle
     */
    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retrieve crime UUID from Bundle's arguments
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                //Blank for now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                mCrime.setTitle(s.toString());      //Call Title setter of Crime object
            }

            @Override
            public void afterTextChanged(Editable s){
                //Also empty
            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date); // Inflate button
        updateDate();
        mDateButton.setOnClickListener(new OnClickListener() { // Create DatePicker dialog
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager(); // get FM associated with this fragment's activity
                DatePickerFragment dateDialog = DatePickerFragment
                        .newInstance(mCrime.getDate()); // create new DPF and passes date to it
                dateDialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE); // set this fragment as the target fragment of DPF
                dateDialog.show(manager, DIALOG_DATE); // call DPF's show() method, passing in fManager and id string
            }
        });

        mTimeButton = (Button) v.findViewById(R.id.crime_time);
        updateTime();
        mTimeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment timeDialog = new TimePickerFragment();
            }
        });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                mCrime.setSolved(isChecked);
            }

        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.setDate(date);
            updateTime();
        }
    }

    /**
     * Updates DateButton with date in Crime object
     */
    private void updateDate() {
        CharSequence date = DateFormat.format("EEEE, MMM d, yyyy", mCrime.getDate());
        mDateButton.setText(date);
    }

    /**
     * Updates TimeButton with time in Crime object
     */
    private void updateTime() {
        CharSequence time = DateFormat.format("HH:mm z", mCrime.getDate());
        mTimeButton.setText(time);
    }
}
