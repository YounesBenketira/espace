package graphical.wireless.espace.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.PlanetData;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View temp = inflater.inflate(R.layout.fragment_search, container, false);
//        Log.i(TAG, "onCreateView: ");
        recyclerView = (RecyclerView) temp.findViewById(R.id.search_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(temp.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        final ArrayList<EspaceData> dataSet = new ArrayList<>();

        dataSet.addAll(((MainActivity) getActivity()).newsDataset);
        dataSet.addAll(((MainActivity) getActivity()).potdDataset);
        dataSet.addAll(((MainActivity) getActivity()).planetDataset);

        mAdapter = new SearchAdapter(dataSet);
        recyclerView.setAdapter(mAdapter);


        // Search bar
        SearchView searchView = temp.findViewById(R.id.txtbox_search);
        final Spinner spinner = temp.findViewById(R.id.filter_search);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = spinner.getSelectedItem().toString();

                switch (selected){
                    case "All":
                        dataSet.clear();
                        dataSet.addAll(((MainActivity) getActivity()).newsDataset);
                        dataSet.addAll(((MainActivity) getActivity()).potdDataset);
                        dataSet.addAll(((MainActivity) getActivity()).planetDataset);
                        Collections.shuffle(dataSet);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case "PotD":
                        dataSet.clear();
                        dataSet.addAll(((MainActivity) getActivity()).potdDataset);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case "News":
                        dataSet.clear();
                        dataSet.addAll(((MainActivity) getActivity()).newsDataset);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case "Planets":
                        dataSet.clear();
                        dataSet.addAll(((MainActivity) getActivity()).planetDataset);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        return temp;
    }

    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
        private ArrayList<EspaceData> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public CardView cardView;

            public MyViewHolder(CardView v) {
                super(v);
                cardView = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public SearchAdapter(ArrayList<EspaceData> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_potd, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            ViewGroup vg = holder.cardView;
            final int pos = position;

            EspaceData temp = mDataset.get(position);

            ((TextView) vg.findViewById(R.id.potd_title)).setText(temp.getTitleText());
            ((TextView) vg.findViewById(R.id.potd_date)).setText(temp.getDateText());

            ImageView imageView = vg.findViewById(R.id.potd_image);

            if (temp instanceof PlanetData)
                imageView.setImageResource(temp.getImageID());
            else {
                Picasso.get().load(temp.getImageURL()).into(imageView);
            }
            vg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsFragment detailsFragment = new DetailsFragment(mDataset.get(pos));

                    ((MainActivity) getActivity()).displayFragment(detailsFragment);
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}


