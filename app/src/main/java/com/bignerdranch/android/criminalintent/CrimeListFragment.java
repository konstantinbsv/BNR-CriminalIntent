package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int mClickedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity()); //Call singleton method of CrimeLab
        List<Crime> crimes = crimeLab.getCrimes();  //Get list of crimes from CrimeLab object crimeLab, assign it to List<of Crimes> crimes

        //If adapter does not exist, create a new one
        if(mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes); //New CrimeAdapter object initialized with List<of Crimes> crimes
            mCrimeRecyclerView.setAdapter(mAdapter); //Set mCrimesRecyclerView's adapter to our new mAdapter
        }
        //If it does, notify it that the data set may have changed
        else{
            mAdapter.notifyItemChanged(mClickedPosition);   //Update only clicked item
        }
    }


    /* Inner Classes */

        /** ViewHolder will inflate and own layout */
        private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mTitleTextView;    //Will display crime title
            private TextView mDateTextView;     //Will display crime date
            private ImageView mSolvedImageView;  //Handcuffs icon for solved crime

            private Crime mCrime;
            private int mPosition;  //Position of holder assigned by Adapter

            /** CrimeHolder constructor, called by Adapter's overridden onCreateViewHolder()
             *
             * @param inflater  inflater which will inflate list item
             * @param parent    parent ViewGroup
             */
            private CrimeHolder(LayoutInflater inflater, ViewGroup parent){

                /* Call ViewHolder's (parent's) constructor, pass to it inflated
                 * list_item_crime layout */
                super(inflater.inflate(R.layout.list_item_crime, parent, false));
                /* base ViewHolder class (super) will hold fragment_crime_list hierarchy
                 * in ViewHolder's itemView field */

                //itemView holds reference to View for entire row (passed to super above)
                itemView.setOnClickListener(this);

                /* Pull out and inflate TextView Widgets */
                mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
                mDateTextView  = (TextView) itemView.findViewById(R.id.crime_date);
                mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            }

            /** Called each time a new crime should be displayed in CrimeHolder (the ViewHolder)
             * @param crime Crime object to be displayed, i.e., bound to the Holder
             */
            public void bind(Crime crime, int position){
                mCrime = crime;
                mPosition = position;   //Position of this holder as specified by adapter
                mTitleTextView.setText(mCrime.getTitle());
                mDateTextView.setText(DateFormat.format("EEEE, MMM d, yyyy 'at' HH:mm zzz", mCrime.getDate()));
                mSolvedImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);
            }

            /* CrimeHolder itself implements OnClickListener interface => it is the receiver for lick events */
            @Override
            public void onClick(View view){
                mClickedPosition = mPosition;   //Set the position of the item clicked
                Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
                startActivity(intent);
            }
        }



        /** Adapter will help RecycleView display a new ViewHolder
         * and connect Crime object's data with it */
        private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

            private List<Crime> mCrimes; //Define a list of Crime objects

            /** Constructor
             *
             * @param crimes list of Crime objects
             */
            public CrimeAdapter(List<Crime> crimes){
                mCrimes = crimes;   //Populate mCrimes list from list passed to constructor
            }

            @Override
            /* Called by RecycleView when it needs a new ViewHolder
             * with which to display an item */
            public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType){
                //Create layout inflater
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

                //Pass layoutInflater and construct a new CrimeHolder
                return new CrimeHolder(layoutInflater, parent);
            }

            @Override
            public void onBindViewHolder(CrimeHolder holder, int position){
                Crime crime = mCrimes.get(position);
                holder.bind(crime, position);
            }

            @Override
            public int getItemCount(){
                return mCrimes.size();
            }
        }
}
