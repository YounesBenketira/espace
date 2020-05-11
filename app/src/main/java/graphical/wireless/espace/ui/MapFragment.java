package graphical.wireless.espace.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.components.FavouriteButton;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.database.Favourite;
import graphical.wireless.espace.ui.data.database.LocalDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public MapFragment() {
        LocalDatabase.getData(MainActivity.favourites);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View temp = inflater.inflate(R.layout.fragment_map, container, false);
//        Log.i(TAG, "onCreateView: ");
        recyclerView = (RecyclerView) temp.findViewById(R.id.map_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(temp.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MapAdapter(((MainActivity)getActivity()).planetDataset);
        recyclerView.setAdapter(mAdapter);



        return temp;
    }

    class MapAdapter extends RecyclerView.Adapter<MapAdapter.MyViewHolder> {
        private ArrayList<PlanetData> data;

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
        public MapAdapter(ArrayList<PlanetData> myDataset) {
            data = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MapAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_planet, parent, false);

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

            vg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsFragment detailsFragment = new DetailsFragment(data.get(pos));

                    ((MainActivity) getActivity()).displayFragment(detailsFragment);
                }
            });

            final PlanetData planet = data.get(pos);

            ((TextView)vg.findViewById(R.id.planet_name)).setText(planet.getTitleText());

            ImageView imageView = vg.findViewById(R.id.planet_image);
            imageView.setImageResource(planet.getImageID());

            // Fav button stuff
            final FavouriteButton favouriteButton = (FavouriteButton) vg.findViewById(R.id.planet_favourite_button);


            if(MainActivity.favourites.size() == 0)
                planet.setFavourite(false);
            else {
                for (int i = 0; i < MainActivity.favourites.size(); i++)
                    if (MainActivity.favourites.get(i).title.equals(planet.getTitleText())) {
                        planet.setFavourite(true);
                        break;
                    } else
                        planet.setFavourite(false);
            }

            favouriteButton.setChecked(planet.isFavourite());
            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(planet.isFavourite()){
                        favouriteButton.setChecked(false);
                        planet.setFavourite(false);
                        Favourite favourite = new Favourite(Favourite.DATA_PLANET, planet.getTitleText(), planet.getDescriptionText(), planet.getAuthorText(), planet.getDateText(), planet.getImageURL(), planet.getImageID());
                        LocalDatabase.delete(favourite, null);
                    }
                    else {
                        favouriteButton.setChecked(true);
                        planet.setFavourite(true);
                        Favourite favourite = new Favourite(Favourite.DATA_PLANET, planet.getTitleText(), planet.getDescriptionText(), planet.getAuthorText(), planet.getDateText(), planet.getImageURL(), planet.getImageID());
                        LocalDatabase.addData(favourite, null);
                    }
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}


