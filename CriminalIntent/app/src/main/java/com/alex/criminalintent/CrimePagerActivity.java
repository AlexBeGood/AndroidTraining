package com.alex.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private TextView mCrimeLast;
    private TextView mCrimeFirst;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    public static UUID getCrimeIdChanged(Intent intent)
    {
        return CrimeFragment.getCrimeIdChanged(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mCrimeFirst = findViewById(R.id.crime_first);
        mCrimeLast = findViewById(R.id.crime_last);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.Get(this).getCrimes();
        int crimePosition = CrimeLab.Get(this).getCrimePosition(crimeId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                Fragment fragment = CrimeFragment.newInstance(crime.getId());
                setEnabled(position);
                return fragment;
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        mViewPager.setCurrentItem(crimePosition);
    }

    private void setEnabled(int position)
    {
        mCrimes = CrimeLab.Get(this).getCrimes();
        //int currentItem = mViewPager.getCurrentItem();
        int currentItem = position;
        if (currentItem == (mCrimes.size() - 1))
            mCrimeLast.setEnabled(false);
        else
            mCrimeLast.setEnabled(true);

        if (currentItem == 0)
            mCrimeFirst.setEnabled(false);
        else
            mCrimeFirst.setEnabled(true);
    }

    public void onCrimeLastClick(View view) {
        mCrimes = CrimeLab.Get(this).getCrimes();
        mViewPager.setCurrentItem(mCrimes.size() - 1);
        setEnabled(mCrimes.size() - 1);
    }

    public void onCrimeFirstClick(View view) {
        mCrimes = CrimeLab.Get(this).getCrimes();
        mViewPager.setCurrentItem(0);
        setEnabled(0);
    }
}
