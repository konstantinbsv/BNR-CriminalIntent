package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.*;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    //private List<Crime> mCrimes;
    // private Map<UUID,Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //Singleton method for CrimeLab
    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    //Private constructor for singleton
    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        // mCrimes = new LinkedHashMap<>(); // Use Linked Hash Map for quick searching, insertion
                                         // and deletion, while maintaining order of items
    }

    public void addCrime(Crime c) {
        // mCrimes.put(c.getId(), c);
    }

    public void deleteCrime(Crime c) {
        // mCrimes.remove(c.getId());
    }

    // getCrimes getter
    public List<Crime> getCrimes() {
        return new ArrayList<>();
    }

    //Get specific crime by UUID
    public Crime getCrime(UUID id){
        // return mCrimes.get(id);
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }
}
