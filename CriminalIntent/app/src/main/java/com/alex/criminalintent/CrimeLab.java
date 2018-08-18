package com.alex.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        FillTestCrimes(100);
    }

    private void FillTestCrimes(int countCrimes)
    {
        if (mCrimes == null)
            return;

        for (int i = 0; i < countCrimes; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            crime.setType(i % 2 == 0 ? TypesCrime.Simple : TypesCrime.Strong);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes){
            if (crime.getId().equals(id))
                return crime;
        }
        return null;
    }

    public int getCrimePosition(UUID id){
        for (int i = 0; i < mCrimes.size(); i++){
            Crime crime = mCrimes.get(i);
            if (crime.getId().equals(id))
                return i;
        }
        return 0;
    }

    public static CrimeLab Get(Context context){
        if (sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);

        return sCrimeLab;
    }
}
