package graphical.wireless.espace.ui;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.components.FavouriteButton;
import graphical.wireless.espace.ui.data.EspaceAdapter;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;
import graphical.wireless.espace.ui.data.database.Favourite;
import graphical.wireless.espace.ui.data.database.LocalDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private static FavouritesFragment.FavouritesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static MainActivity mainActivity;

    private static List<Favourite> favouriteList = new ArrayList<>();

    public FavouritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View temp = inflater.inflate(R.layout.fragment_favourites, container, false);

        mainActivity = (MainActivity) getActivity();

        Context context = mainActivity.getApplicationContext();

        recyclerView = temp.findViewById(R.id.favourites_recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(temp.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new FavouritesFragment.FavouritesAdapter(favouriteList);
        recyclerView.setAdapter(mAdapter);

        LocalDatabase.getData(mAdapter);

//        Button addBtn = temp.findViewById(R.id.addBtn);
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addData();
//            }
//        });

        Button getBtn = temp.findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDatabase.getData(mAdapter);
            }
        });


        return temp;
    }

    class FavouritesAdapter extends RecyclerView.Adapter<FavouritesFragment.FavouritesAdapter.FavouritesViewHolder>
                        implements EspaceAdapter {

        // Inner class View Holder
        public class FavouritesViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public CardView cardView;
            public TextView title;
            public TextView date;
            public ImageView image;
            public FavouriteButton button;

            public FavouritesViewHolder(CardView v) {
                super(v);
                cardView = v;
                title = v.findViewById(R.id.potd_title);
                date = v.findViewById(R.id.potd_date);
                image = v.findViewById(R.id.potd_image);
                button = v.findViewById(R.id.potd_favourite_button);
            }
        }

        private List<Favourite> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder


        // Provide a suitable constructor (depends on the kind of dataset)
        public FavouritesAdapter(List<Favourite> myDataset) {

            mDataset = new ArrayList(myDataset);
        }

        @Override
        public void updateData(List list) {
            mDataset = new ArrayList<>();
            mDataset.addAll(list);

            notifyDataSetChanged();
        }

        @Override
        public List getData(){
            return mDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public FavouritesFragment.FavouritesAdapter.FavouritesViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                            int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_potd, parent, false);

            FavouritesFragment.FavouritesAdapter.FavouritesViewHolder vh = new FavouritesFragment.FavouritesAdapter.FavouritesViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
            final Favourite fav = mDataset.get(position);

            // Set the UI elements
            holder.title.setText(fav.title);
            holder.date.setText(fav.date);
            if (fav.imageURL != null && !fav.imageURL.isEmpty())
                Picasso.get().load(fav.imageURL).into(holder.image);
            else
                holder.image.setImageResource(R.drawable.noimage);
            holder.button.setChecked(true);

            // Create click event handler to see details
            final int pos = position;
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsFragment detailsFragment = new DetailsFragment(mDataset.get(pos).toEspaceData());

                    ((MainActivity) requireActivity()).displayFragment(detailsFragment);
                }
            });

            // Fav button stuff
            final FavouriteButton favouriteButton = holder.button;

            //favouriteButton.setChecked(temp.isFavourite());
            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favouriteButton.setChecked(false);
                    LocalDatabase.delete(fav, mAdapter);
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
