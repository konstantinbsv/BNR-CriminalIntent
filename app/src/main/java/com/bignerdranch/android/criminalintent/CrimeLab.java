package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    //private List<Crime> mCrimes;
    Map<UUID,Crime> mCrimes;

    //Singleton method for CrimeLab
    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    //Private constructor for singleton
    private CrimeLab(Context context){
        mCrimes = new LinkedHashMap<>(); // Use Linked Hash Map for quick searching, insertion
                                         // and deletion, while maintaining order of items

        //Populate LinkedHasHMap<UUID,Crime> mCrimes with 100 test crimes
        for (int i = 0; i < 100; i++ ){
            Crime crime = new Crime();      //Create new Crime instance
            crime.setTitle("Crime #" + i);  //Set title with number
            crime.setSolved(i % 2 == 0);    //Set even as solved
            mCrimes.put(crime.getId(), crime);  //Put crime in LHM mCrimes with UUID as key
        }
    }

    //getCrimes getter
    public List<Crime> getCrimes(){
        return new ArrayList<>(mCrimes.values());
    }

    //Get specific crime by UUID
    public Crime getCrime(UUID id){
        return mCrimes.get(id);
    }
}
