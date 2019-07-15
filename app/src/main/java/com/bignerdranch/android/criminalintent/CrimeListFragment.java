package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
        /* Inner Class. ViewHolder will inflate and own layout */
        private class CrimeHolder extends RecyclerView.ViewHolder{
            private CrimeHolder(LayoutInflater inflater, ViewGroup parent){

                /* Call ViewHolder's (parent's) constructor, pass to it inflated
                 * list_item_crime layout */
                super(inflater.inflate(R.layout.list_item_crime, parent, false));

                /* base ViewHolder class will hold fragment_crime_list hierarchy
                 * in ViewHolder's itemView field */
            }
        }

        /* Another Inner Class. Adapter will help RecycleView display a new ViewHolder
         * and connect Crime object's data with it */
        private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

            private List<Crime> mCrimes; //Define a list of Crime objects

            public CrimeAdapter(List<Crime> crimes){
                mCrimes = crimes;   //Populate mCrimes list from list passed to constructor
            }

            @Override
            /* Called by RecycleView when it needs a new ViewHolder
             * with which to display an item */
            public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType){
                //Create layout inflater
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

                //Use layoutInflater to construct a new CrimeHolder
                return new CrimeHolder(layoutInflater, parent);
            }

            @Override
            public void onBindViewHolder(CrimeHolder holder, int position){

            }

            @Override
            public int getItemCount(){
                return mCrimes.size();
            }
        }
}
