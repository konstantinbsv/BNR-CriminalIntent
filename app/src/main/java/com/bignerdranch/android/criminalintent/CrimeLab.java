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
    }

    public void addCrime(Crime c) {
        mCrimes.put(c.getId(), c);
    }

    public void deleteCrime(Crime c) {
        mCrimes.remove(c);
    }

    // getCrimes getter
    public List<Crime> getCrimes() {
        return new ArrayList<>(mCrimes.values());
    }

    //Get specific crime by UUID
    public Crime getCrime(UUID id){
        return mCrimes.get(id);
    }
}
