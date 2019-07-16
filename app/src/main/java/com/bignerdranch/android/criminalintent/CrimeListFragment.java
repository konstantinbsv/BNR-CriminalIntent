package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity()); //Call singleton method of CrimeLab
        List<Crime> crimes = crimeLab.getCrimes();  //Get list of crimes from CrimeLab object crimeLab, assign it to List<of Crimes> crimes

        mAdapter = new CrimeAdapter(crimes); //New CrimeAdapter object initialized with List<of Crimes> crimes
        mCrimeRecyclerView.setAdapter(mAdapter); //Set mCrimesRecyclerView's adapter to our new mAdapter
    }


    /*** Inner Classes***/

        /** ViewHolder will inflate and own layout */
        private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mTitleTextView;    //Will display crime title
            private TextView mDateTextView;     //Will display crime date

            private Crime mCrime;

            /** CrimeHolder constructor, called by Adapter's overridden onCreateViewHolder()
             *
             * @param inflater  inflater which will inflate list item
             * @param parent    parent ViewGroup
             */
            private CrimeHolder(LayoutInflater inflater, ViewGroup parent, int viewType){


                /* Call ViewHolder's (parent's) constructor, pass to it inflated
                 * list_item_crime layout */
                //Will inflate different layout depending on viewType
                super(inflater.inflate((viewType == 1) ? R.layout.list_item_crime_serious : R.layout.list_item_crime, parent, false));
                /* base ViewHolder class (super) will hold fragment_crime_list hierarchy
                 * in ViewHolder's itemView field */

                //itemView holds reference to View for entire row (passed to super above)
                itemView.setOnClickListener(this);

                /* Pull out and inflate TextView Widgets */
                mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
                mDateTextView  = (TextView) itemView.findViewById(R.id.crime_date);

                /* If this is a serious crime with emergency button, add a listener to it */
                if(viewType == 1){
                   Button mCallPoliceButton = (Button) itemView.findViewById(R.id.emergency_button);
                   mCallPoliceButton.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Toast.makeText(getActivity(), getString(R.string.emergency_button_label), Toast.LENGTH_LONG).show();
                       }
                   });
                }
            }

            /** Called each time a new crime should be displayed in CrimeHolder (the ViewHolder)
             * @param crime Crime object to be displayed, i.e., bound to the Holder
             */
            public void bind(Crime crime){
                mCrime = crime;
                mTitleTextView.setText(mCrime.getTitle());
                mDateTextView.setText(mCrime.getDate().toString());
            }

            /* CrimeHolder itself implements OnClickListener interface => it is the receiver for lick events */
            @Override
            public void onClick(View view){
                Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_LONG).show();
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
                return new CrimeHolder(layoutInflater, parent, viewType);
            }

            @Override
            public void onBindViewHolder(CrimeHolder holder, int position){
                Crime crime = mCrimes.get(position);
                holder.bind(crime);
            }

            @Override
            public int getItemCount(){
                return mCrimes.size();
            }

            @Override
            public int getItemViewType(int position){
                if(mCrimes.get(position).isRequiresPolice()){
                    return 1;   //ViewType = 1 requires police
                }
                else{
                    return 0;   //ViewType = 0 normal
                }
            }
        }
}
