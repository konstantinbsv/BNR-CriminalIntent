package com.bignerdranch.android.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    //String hold extra's key
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    /** Creates on explicit Intent object and attaches to it, as an extra, the
     *  UUID of the crime who's details need to be accessed.
     *
     * @param packageContext activity calling this function (hosting activity) as Context object
     * @param crimeId   UUID of crime to display details of
     * @return          intent object
     */
    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        //Retrieve extra from CrimeActivity's intent
        //note: getIntent() returns Intent that was used to start activity
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        //Pass it into newInstance, return the CrimeFragment
        return CrimeFragment.newInstance(crimeId);
    }

}
