package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.UUID;

import static android.widget.CompoundButton.*;  //Imports all static members from CompoundButton type

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getIntent() returns Intent that was used to start activity
        UUID crimeId = (UUID) getActivity().getIntent()
                                           .getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
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

        mDateButton = (Button) v.findViewById(R.id.crime_date); //Inflate button
        mDateButton.setText(mCrime.getDate().toString());   //Set button text to date of mCrime object (current date by default)
        mDateButton.setEnabled(false);  //Disabled, user cannot interact with it

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
}
