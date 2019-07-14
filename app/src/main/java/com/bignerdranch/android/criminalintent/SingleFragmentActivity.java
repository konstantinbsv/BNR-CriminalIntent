package com.bignerdranch.android.criminalintent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

//Abstract Activity that can host single fragment
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();   //Abstract method that creates and returns a Fragment
                                                    //To be implemented by subclass

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment); //Inflate layout based on activity_fragment.xml

        FragmentManager fm = getSupportFragmentManager(); //new FragmentManager fm
        Fragment fragment = fm.findFragmentById(R.id.fragment_container); //Get Fragment in FragmentManager with id: fragment_container

        if (fragment == null){  //If the fragment does not exist
            fragment = createFragment(); //Create it using abstract method createFragment()
            fm.beginTransaction()                           //begin fragment transaction
                    .add(R.id.fragment_container, fragment) //add fragment to FragmentManager fm
                    .commit();                              //commit the transaction

        }
    }
}
