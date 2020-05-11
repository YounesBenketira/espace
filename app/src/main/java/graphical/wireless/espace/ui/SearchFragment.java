package graphical.wireless.espace.ui;


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
import java.util.List;

import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.components.FavouriteButton;
import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;
import graphical.wireless.espace.ui.data.database.Favourite;
import graphical.wireless.espace.ui.data.database.LocalDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public SearchFragment() {
        LocalDatabase.getData(MainActivity.favourites);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
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
        final Spinner spinner = temp.findViewById(R.id.filter_search);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = spinner.getSelectedItem().toString();

                ArrayList<EspaceData> temp = new ArrayList<>();
                switch (selected) {
                    case "All":
                        temp = dataSet;
                        break;
                    case "PotD":
                        temp.addAll(((MainActivity) getActivity()).potdDataset);
                        break;
                    case "News":
                        temp.addAll(((MainActivity) getActivity()).newsDataset);
                        break;
                    case "Planets":
                        temp.addAll(((MainActivity) getActivity()).planetDataset);
                        break;
                }
                mAdapter.updateData(temp);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        SearchView searchView = temp.findViewById(R.id.txtbox_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() == 0){
                    ArrayList<EspaceData> temp = new ArrayList<>();
                    switch(spinner.getSelectedItem().toString()){
                        case "All":
                            mAdapter.updateData(dataSet);
                            break;
                        case "PotD":
                            temp.addAll(((MainActivity) getActivity()).potdDataset);
                            mAdapter.updateData(temp);
                            break;
                        case "News":
                            temp.addAll(((MainActivity) getActivity()).newsDataset);
                            mAdapter.updateData(temp);
                            break;
                        case "Planets":
                            temp.addAll(((MainActivity) getActivity()).planetDataset);
                            mAdapter.updateData(temp);
                            break;
                    }
                    mAdapter.notifyDataSetChanged();
                }else {
                    ArrayList<EspaceData> copy = new ArrayList<>();
                    copy.addAll(mAdapter.getData());

                    for (int i = copy.size()-1; i >= 0; i--) {
                        EspaceData temp = copy.get(i);

                        if (!temp.getTitleText().contains(newText)) {
                            copy.remove(i);
                        }
                    }
                    mAdapter.updateData(copy);
                    mAdapter.notifyDataSetChanged();
                }

                return false;
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


        public void updateData(ArrayList<EspaceData> data){
            mDataset = new ArrayList<>();
            mDataset.addAll(data);
        }

        public ArrayList<EspaceData> getData(){
            return mDataset;
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

            final EspaceData temp = mDataset.get(position);

            ((TextView) vg.findViewById(R.id.potd_title)).setText(temp.getTitleText());
            ((TextView) vg.findViewById(R.id.potd_date)).setText(temp.getDateText());

            ImageView imageView = vg.findViewById(R.id.potd_image);

            String imageURL = temp.getImageURL();
            if (temp instanceof PlanetData)
                imageView.setImageResource(temp.getImageID());
            else if (imageURL == null || imageURL.length() == 0 || imageURL.charAt(4) != 's') {
                imageView.setImageResource(R.drawable.noimage);
            } else {
                Picasso.get().load(imageURL).into(imageView);
            }

            vg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsFragment detailsFragment = new DetailsFragment(mDataset.get(pos));
                    Log.i("TEST", "onBindViewHolder: " + temp.getTitleText());
                    ((MainActivity) getActivity()).displayFragment(detailsFragment);
                }
            });

            // Fav button stuff
            final FavouriteButton favouriteButton = (FavouriteButton) vg.findViewById(R.id.potd_favourite_button);

            for(int i = 0; i < MainActivity.favourites.size(); i++)
                if(MainActivity.favourites.get(i).title.equals(temp.getTitleText()))
                    temp.setFavourite(true);

            favouriteButton.setChecked(temp.isFavourite());
            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int instanceType = 3;
                    if(temp instanceof PotdData) {
                        instanceType = Favourite.DATA_POTD;
                    } else if(temp instanceof PlanetData) {
                        instanceType = Favourite.DATA_PLANET;
                    } else if(temp instanceof NewsData) {
                        instanceType = Favourite.DATA_NEWS;
                    }
                    if(temp.isFavourite()){
                        favouriteButton.setChecked(false);
                        temp.setFavourite(false);
                        Favourite favourite = new Favourite(instanceType, temp.getTitleText(), temp.getDescriptionText(), temp.getAuthorText(), temp.getDateText(), temp.getImageURL(), temp.getImageID());
                        LocalDatabase.delete(favourite, null);
                    }
                    else {
                        favouriteButton.setChecked(true);
                        temp.setFavourite(true);
                        Favourite favourite = new Favourite(instanceType, temp.getTitleText(), temp.getDescriptionText(), temp.getAuthorText(), temp.getDateText(), temp.getImageURL(), temp.getImageID());
                        LocalDatabase.addData(favourite, null);
                    }
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


