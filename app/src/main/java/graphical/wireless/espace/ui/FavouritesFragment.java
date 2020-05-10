package graphical.wireless.espace.ui;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.data.database.Favourite;
import graphical.wireless.espace.ui.data.database.FavouriteDao;
import graphical.wireless.espace.ui.data.database.LocalDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] myDataset;

    public FavouritesFragment() {
        myDataset = new String[]{"Favourite Item", "Favourite Item", "Favourite Item", "Favourite Item", "Favourite Item"};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View temp = inflater.inflate(R.layout.fragment_favourites, container, false);

        ArrayList<Favourite> favouriteList = new ArrayList<>();

        recyclerView = (RecyclerView) temp.findViewById(R.id.favourites_recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(temp.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new FavouritesFragment.FavouritesAdapter(favouriteList);
        recyclerView.setAdapter(mAdapter);

//        new FavouriteAsyncTask(((MainActivity)getActivity())).execute();

        return temp;
    }

//    private static class FavouriteAsyncTask extends AsyncTask<Void, Void, Integer> {
//
//        //Prevent leak
//        private WeakReference<MainActivity> weakActivity;
//        private String email;
//        private String phone;
//        private String license;
//
//        public FavouriteAsyncTask(Activity activity) {
//            weakActivity = new WeakReference<MainActivity>((MainActivity) activity);
////            this.email = email;
////            this.phone = phone;
////            this.license = license;
//        }
//
//        @Override
//        protected Integer doInBackground(Void... params) {
//            FavouriteDao favouriteDao = FavouritesFragment.db.getFavouriteDao();
//            return favouriteDao.getAll().size();
//        }
//
//        @Override
//        protected void onPostExecute(Integer favouriteCount) {
//            Activity activity = weakActivity.get();
//            if(activity == null) {
//                return;
//            }
//
//            if (favouriteCount > 0) {
//                //2: If it already exists then prompt user
//                Toast.makeText(activity, "Agent already exists!", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(activity, "Agent does not exist! Hurray :)", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    class FavouritesAdapter extends RecyclerView.Adapter<FavouritesFragment.FavouritesAdapter.MyViewHolder> {
        private ArrayList<Favourite> mDataset;

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
        public FavouritesAdapter(ArrayList<Favourite> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public FavouritesFragment.FavouritesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                    int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_potd, parent, false);

            FavouritesFragment.FavouritesAdapter.MyViewHolder vh = new FavouritesFragment.FavouritesAdapter.MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(FavouritesFragment.FavouritesAdapter.MyViewHolder holder, int position) {
            ViewGroup vg = holder.cardView;
            final int pos = position;

            ((TextView) vg.findViewById(R.id.potd_title)).setText(mDataset.get(position).title);

//            vg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DetailsFragment detailsFragment = new DetailsFragment(mDataset.get(position));
//
//                    ((MainActivity) getActivity()).displayFragment(detailsFragment);
//                }
//            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
