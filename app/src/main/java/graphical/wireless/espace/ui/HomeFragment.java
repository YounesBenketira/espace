package graphical.wireless.espace.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import graphical.wireless.espace.DetailsActivity;
import graphical.wireless.espace.MainActivity;
import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private PotdData[] myDataset;

    public HomeFragment() {
        myDataset = new PotdData[]{
                new PotdData("2020 April 17", "WindmillStarTrails1024.jpg",
                        "Stars can't turn these old wooden arms, but it does look like they " +
                                "might in this scene from a rotating planet. The well-composed " +
                                "night skyscape was recorded from Garafia, a municipality on the " +
                                "island of La Palma, Canary Islands, planet Earth. The center of " +
                                "the once working windmill, retired since 1953, is lined-up with " +
                                "the north celestial pole, the planet's rotation axis projected on " +
                                "to the northern sky. From a camera fixed to a tripod, the star " +
                                "trails are a reflection of the planet's rotation traced in a " +
                                "digital composite of 39 sequential exposures each 25 seconds long." +
                                "Brought out by highlighting the final exposure in the sequence, " +
                                "the stars themselves appear at the ends of their short concentric " +
                                "arcs. A faint band of winter's Milky Way and even a diffuse glow " +
                                "from our neighboring Andromeda Galaxy also shine in the night."),
                new PotdData("2020 April 16", "C2019Y4_20.04.13_1100px.jpg",
                        "Cruising through the inner solar system, Comet ATLAS (C/2019 Y4) has" +
                                " apparently fragmented. Multiple separate condensations within its" +
                                " diffuse coma are visible in this telescopic close-up from April" +
                                " 12, composed of frames tracking the comet's motion against " +
                                "trailing background stars. Discovered at the end of December " +
                                "2019, this comet ATLAS showed a remarkably rapid increase in" +
                                " brightness in late March. Northern hemisphere comet watchers " +
                                "held out hope that it would become a bright naked-eye comet as" +
                                " it came closer to Earth in late April and May. But fragmenting" +
                                " ATLAS is slowly fading in northern skies. The breakup of comets" +
                                " is not uncommon though. This comet ATLAS is in an orbit similar" +
                                " to the Great Comet of 1844 (C/1844 Y1) and both may be fragments" +
                                " of a single larger comet."),
                new PotdData("2020 April 16", "MVP_Aspinall_960.jpg",
                        "Explanation: It was an astronomical triple play. Setting on the " +
                                "left, just after sunset near the end of last month, was our Moon " +
                                "-- showing a bright crescent phase. Setting on the right was " +
                                "Venus, the brightest planet in the evening sky last month -- and" +
                                " this month, too. With a small telescope, you could tell that " +
                                "Venus' phase was half, meaning that only half of the planet, as " +
                                "visible from Earth, was exposed to direct sunlight and brightly " +
                                "lit. High above and much further in the distance was the Pleiades" +
                                " star cluster. Although the Moon and Venus move with respect to" +
                                " the background stars, the Pleiades do not -- because they are" +
                                " background stars. In the beginning of this month, Venus appeared" +
                                " to move right in front of the Pleiades, a rare event that" +
                                " happens only once every eight years. The featured image captured" +
                                " this cosmic triangle with a series of exposures taken from the" +
                                " same camera over 70 minutes near Avonlea, Saskatchewan, Canada." +
                                " The positions of the celestial objects was predicted. The only" +
                                " thing unpredicted was the existence of the foreground tree -- and" +
                                " the astrophotographer is still unsure what type of tree that is."),
                new PotdData("2020 April 15", "NGC253_HstSubaruEsoNew_960.jpg",
                        "Explanation: NGC 253 is one of the brightest spiral galaxies " +
                                "visible, but also one of the dustiest. Dubbed the Silver Coin for" +
                                " its appearance in smalltelescopes, it is more formally known as" +
                                " the Sculptor Galaxy for its location within the boundaries of the" +
                                " southern constellation Sculptor. Discovered in 1783 by " +
                                "mathematician and astronomer Caroline Herschel, the dusty island " +
                                "universe lies a mere 10 million light-years away. About 70" +
                                " thousand light-years across, NGC 253, pictured, is the largest" +
                                " member of the Sculptor Group of Galaxies, the nearest to our own" +
                                " Local Group of galaxies. In addition to its spiral dust lanes," +
                                " tendrils of dust seem to be rising from a galactic disk laced" +
                                " with young star clusters and star forming regions in this sharp" +
                                " color image. The high dust content accompanies frantic star" +
                                " formation, earning NGC 253 the designation of a starburst galaxy." +
                                " NGC 253 is also known to be a strong source of high-energy x-rays" +
                                " and gamma rays, likely due to massive black holes near the" +
                                " galaxy's center. Take a trip through extragalactic space in this" +
                                " short video flyby of NGC 253.")};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View temp = inflater.inflate(R.layout.fragment_home, container, false);
//        Log.i(TAG, "onCreateView: ");
        recyclerView = (RecyclerView) temp.findViewById(R.id.home_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(temp.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new POTDAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

        return temp;
    }

    class POTDAdapter extends RecyclerView.Adapter<POTDAdapter.MyViewHolder> {
        private PotdData[] mDataset;

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
        public POTDAdapter(PotdData[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public POTDAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent,
                                                           int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_potd, parent, false);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date = ((TextView) v.findViewById(R.id.potd_date)).getText().toString();
                    String title = ((TextView) v.findViewById(R.id.potd_title)).getText().toString();
//                    Drawable image = ((ImageView) v.findViewById(R.id.potd_image)).getDrawable();
//                    String desc = ((TextView) v.findViewById(R.id.potd_desc)).getText().toString();

                    String desc = mDataset[0].getDesc();

                    PotdData data = new PotdData(date,title,desc);

                    Intent intent = new Intent(parent.getContext(), DetailsActivity.class);

                    Log.i("DOG", "showDetails: " + data.getClass());

                    intent.putExtra("date", data.getDate());
                    intent.putExtra("desc", data.getDesc());
                    intent.putExtra("title", data.getImageName());
                    startActivity(intent);
                }
            });

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            String title = mDataset[position].getImageName();
            String date = mDataset[position].getDate();
            String desc = mDataset[position].getDesc();

            ( (TextView) holder.cardView.findViewById(R.id.potd_title)).setText(title);
//            ( (TextView) holder.cardView.findViewById(R.id.potd_desc)).setText(desc);
            ( (TextView) holder.cardView.findViewById(R.id.potd_date)).setText(date);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }

}


