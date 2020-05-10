package graphical.wireless.espace.ui.data.database;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.DetailsFragment;
import graphical.wireless.espace.ui.components.FavouriteButton;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    class CardViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView date;
        private final ImageView imageView;
        private final FavouriteButton favButton;

        private CardViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            date = itemView.findViewById(R.id.card_date);
            imageView = itemView.findViewById(R.id.card_image);
            favButton = itemView.findViewById(R.id.card_favourite_button);
        }
    }

    private final LayoutInflater mInflater;
    private List<CardData> mCardData; // Cached copy of words

    public CardListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.layout_card, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        if (mCardData != null) {
            final View v = holder.itemView;
            final CardData current = mCardData.get(position);
            Log.i("Favourite", "onBind  pos? " + position + ", isNull? " + (current == null) + ", mCardSize? " + mCardData.size());
            Log.i("Favourite", "   curr" + current.toString());

            holder.title.setText(current.title);
            holder.date.setText(current.date);
            holder.favButton.setChecked(true); // Always

            if (!current.imageURL.isEmpty())
                Picasso.get().load(current.imageURL).into(holder.imageView);
            else
                holder.imageView.setImageResource(R.drawable.noimage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsFragment detailsFragment = new DetailsFragment(current.getData());

                    ((MainActivity) v.getContext().getApplicationContext()).displayFragment(detailsFragment);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            Log.i("Favourite", " onBindViewHolder, mFavourites is null");
        }
    }

    public void setCardData(List<CardData> cardData) {
        mCardData = cardData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCardData != null)
            return mCardData.size();
        else return 0;
    }
}
