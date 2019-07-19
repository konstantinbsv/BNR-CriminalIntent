package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private List<Crime> mCrimes;    //List to store crimes from CrimeLab

    private Button mJumpToFirst;    //Jump to last and first cime buttons
    private Button mJumpToLast;

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
        final FragmentManager fragmentManager = getSupportFragmentManager(); //Create FragmentManger object
                                        //for interacting with fragments associated with this activity

        //Create anonymous PagerAdapter and set it on ViewPager
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


        mJumpToFirst = (Button) findViewById(R.id.jump_to_first_button);
        mJumpToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        mJumpToLast = (Button) findViewById(R.id.jump_to_last_button);
        mJumpToLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mCrimes.size() - 1);
            }
        });

        //Add OnPageChangeListener to ViewPager to enable/disable buttons
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageScrollStateChanged(int state) {  }

            @Override
            public void onPageSelected(int position) {
                updateButtonStatus(position);
            }
        });

        //Set position of ViewPager to match position of crime with crime ID that was
        //passed as extra on Intent from CrimeListFragment
        for (int i = 0; i < mCrimes.size(); i++)
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                updateButtonStatus(i);
                break;
            }

    }

    /**
     * Will enable/disable Jump to First and Last buttons depending
     * on current position of ViewPager
     *
     * @param position position of current item set by ViewPager
     */
    private void updateButtonStatus(int position){
        mJumpToFirst.setEnabled(position != 0); //Disable Jump to First button on first page
        mJumpToLast.setEnabled(position != (mCrimes.size() - 1) ); //Disable Last Button on last page
    }
}
