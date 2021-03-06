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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.components.FavouriteButton;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.database.Favourite;
import graphical.wireless.espace.ui.data.database.LocalDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public NewsFragment() {
        LocalDatabase.getData(MainActivity.favourites);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View temp = inflater.inflate(R.layout.fragment_news, container, false);
//        Log.i(TAG, "onCreateView: ");
        recyclerView = (RecyclerView) temp.findViewById(R.id.news_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(temp.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new NewsAdapter(((MainActivity) getActivity()).newsDataset);
        recyclerView.setAdapter(mAdapter);

        return temp;
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
        private ArrayList<NewsData> data;

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
        public NewsAdapter(ArrayList<NewsData> myDataset) {
            data = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_news, parent, false);

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

            final NewsData article = data.get(pos);

            ((TextView) vg.findViewById(R.id.news_title)).setText(article.getTitleText());

            ImageView imageView = vg.findViewById(R.id.news_image);

            if (article.getImageURL() == null || article.getImageURL().length() == 0 || article.getImageURL().charAt(4) != 's')
                imageView.setImageResource(R.drawable.noimage);
            else {
                    Picasso.get().load(article.getImageURL()).into(imageView);
            }

            // Fav button stuff
            final FavouriteButton favouriteButton = (FavouriteButton) vg.findViewById(R.id.news_favourite_button);

            if(MainActivity.favourites.size() == 0)
                article.setFavourite(false);
            else {
                for (int i = 0; i < MainActivity.favourites.size(); i++)
                    if (MainActivity.favourites.get(i).title.equals(article.getTitleText())) {
                        article.setFavourite(true);
                        break;
                    } else
                        article.setFavourite(false);
            }

            favouriteButton.setChecked(article.isFavourite());
            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(article.isFavourite()){
                        favouriteButton.setChecked(false);
                        article.setFavourite(false);
                        Favourite favourite = new Favourite(Favourite.DATA_NEWS, article.getTitleText(), article.getDescriptionText(), article.getAuthorText(), article.getDateText(), article.getImageURL(), article.getImageID());
                        LocalDatabase.delete(favourite, null);
                    }
                    else {
                        favouriteButton.setChecked(true);
                        article.setFavourite(true);
                        Favourite favourite = new Favourite(Favourite.DATA_NEWS, article.getTitleText(), article.getDescriptionText(), article.getAuthorText(), article.getDateText(), article.getImageURL(), article.getImageID());
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


