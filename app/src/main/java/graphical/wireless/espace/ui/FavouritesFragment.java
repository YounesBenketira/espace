package graphical.wireless.espace.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.PotdData;
import graphical.wireless.espace.ui.data.database.Favourite;
import graphical.wireless.espace.ui.data.database.FavouriteDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavouritesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<EspaceData> dataset;

    public FavouritesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataset = new ArrayList<>();
        final FavouritesFragment ff = this;

        // Async favourites database read
        ListenableFuture<List<EspaceData>> futureDataset = FavouriteDatabase.getEspaceFavourites();

        // Callback on completion of async database read
        Futures.addCallback(futureDataset, new FutureCallback<List<EspaceData>>() {
            @Override
            public void onSuccess(@javax.annotation.Nullable List<EspaceData> result) {
                if(result != null) {
                    ff.mAdapter.setItems(new ArrayList<>(result));
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, FavouriteDatabase.service);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View temp = inflater.inflate(R.layout.fragment_favourites, container, false);

        recyclerView = (RecyclerView) temp.findViewById(R.id.favourites_recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(temp.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new FavouritesFragment.FavouritesAdapter(dataset);
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
        public FavouritesAdapter(ArrayList<EspaceData> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public FavouritesFragment.FavouritesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                    int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_card, parent, false);

            FavouritesFragment.FavouritesAdapter.MyViewHolder vh = new FavouritesFragment.FavouritesAdapter.MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(FavouritesFragment.FavouritesAdapter.MyViewHolder holder, int position) {
            CardView vg = holder.cardView;
            final int pos = position;

            mDataset.get(pos).getCardView(vg);
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

        public void setItems(ArrayList<EspaceData> data) {
            mDataset = data;
            this.notifyDataSetChanged();
        }
    }
}
