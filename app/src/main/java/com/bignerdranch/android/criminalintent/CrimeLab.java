package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    //Private constructor for singleton
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>(); //Infer list items type based on variable declaration
    }

    //getCrimes getter
    public List<Crime> getCrimes(){
        return mCrimes;
    }

    //Get specific crime by UUID
    public Crime getCrime(UUID id){
        for(Crime crime: mCrimes){  //Search for crime in mCrimes ArrayList
            if(crime.getId().equals(id)){   //If UUIDs match, return crime
                return crime;
            }
        }
        return null;    //If no crimes match. return null
    }
}
