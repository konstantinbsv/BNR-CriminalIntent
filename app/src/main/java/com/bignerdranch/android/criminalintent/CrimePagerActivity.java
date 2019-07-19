package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.criminalintent.crime_id";

    private ViewPager mViewPager;   //Pager object
    private List<Crime> mCrimes;   //List to store crimes from CrimeLab

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class); //Create new intent to this Activity
        intent.putExtra(EXTRA_CRIME_ID, crimeId);   //Put crimeId as extra on the intent
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);  //Set root view of activity and inflate

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID); //Get crimeId from extras

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager); //

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager(); //Create FragmentManger object
                                        //for interacting with fragments associated with this activity
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++)
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }

    }
}
