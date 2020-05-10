package graphical.wireless.espace.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PlanetData;
import graphical.wireless.espace.ui.data.PotdData;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    String date;
    String imageURL;
    int imageID;
    String title;
    String author;
    String desc;

    public DetailsFragment(EspaceData data) {
        if (data instanceof PotdData) {
            PotdData temp = (PotdData) data;

            date = temp.getDateText();
            imageURL = temp.getImageURL();
            title = temp.getTitleText();
            author = temp.getAuthorText();
            desc = temp.getDescriptionText();

        } else if (data instanceof NewsData) {
            NewsData temp = (NewsData) data;

            date = temp.getDateText();
            imageURL = temp.getImageURL();
            title = temp.getTitleText();
            author = temp.getAuthorText();
            desc = temp.getDescriptionText();
        } else {
            PlanetData temp = (PlanetData) data;

            date = "";
            imageID = temp.getImageID();
            title = temp.getTitleText();
            author = "";
            desc = temp.getDescriptionText();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View temp = inflater.inflate(R.layout.fragment_details, container, false);

        ((TextView) temp.findViewById(R.id.details_title)).setText(title);
        ((TextView) temp.findViewById(R.id.details_author)).setText(author);
        ((TextView) temp.findViewById(R.id.details_description)).setText(desc);
        ((TextView) temp.findViewById(R.id.details_date)).setText(date);

        ImageView imageView = temp.findViewById(R.id.details_image);
        if(imageURL != null)
            Picasso.get().load(imageURL).into(imageView);
        else
            imageView.setImageResource(imageID);

        return temp;
    }
}
