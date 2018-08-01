package com.alex.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        //Задаем новый LayoutManager - он занимается размещением элементов на экране
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Создаем адаптер и связываем его с RecyclerView
        updateUI();
        return v;
    }



    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.Get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mCrimeAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mCrimeAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ICrimeHolder{

        TextView mTitleTextView;
        TextView mDateTextView;
        ImageView mImageView;
        Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mImageView = itemView.findViewById(R.id.crime_solved);
            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            Date date = mCrime.getDate();
            if (date != null) {
                String dateString = date.toString();
                mDateTextView.setText(date.toString());
            }
            mImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimeStrongHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ICrimeHolder{

        TextView mTitleTextView;
        TextView mDateTextView;
        Button mButtonPolice;
        ImageView mImageView;
        Crime mCrime;

        public CrimeStrongHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime_strong, parent, false));

            mTitleTextView = itemView.findViewById(R.id.crime_string_title);
            mDateTextView = itemView.findViewById(R.id.crime_strong_date);
            mButtonPolice = itemView.findViewById(R.id.crime_strong_police);
            mImageView = itemView.findViewById(R.id.crime_solved_strong);
            itemView.setOnClickListener(this);

            //Устанавливаем обработчик событий для кнопки вызова полиции
            View.OnClickListener onClickListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    onClickPolice(view);
                }
            };

            mButtonPolice.setOnClickListener(onClickListener);
        }

        private void onClickPolice(View view) {
            Toast.makeText(getActivity(), "The Police will not come! Sorry!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), "Crime is strong! Call the police!", Toast.LENGTH_SHORT).show();
        }

        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            Date date = mCrime.getDate();
            if (date != null) {
                String dateString = date.toString();
                mDateTextView.setText(date.toString());
            }
            mImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);

        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            if (viewType == 0) {
                CrimeHolder newCrimeHolder = new CrimeHolder(layoutInflater, parent);
                return newCrimeHolder;
            }

            if (viewType == 1) {
                CrimeStrongHolder newCrimeHolder = new CrimeStrongHolder(layoutInflater, parent);
                return newCrimeHolder;
            }

            CrimeHolder newCrimeHolder = new CrimeHolder(layoutInflater, parent);

            return newCrimeHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //Получаем crime
            Crime crime = mCrimes.get(position);
            //Связываем CrimeHolder c crime
            ((ICrimeHolder)holder).bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            Crime crime = mCrimes.get(position);
            if (crime.getType() == TypesCrime.Simple){
                return 0;
            }
            if (crime.getType() == TypesCrime.Strong){
                return 1;
            }
            return super.getItemViewType(position);
        }
    }
}
