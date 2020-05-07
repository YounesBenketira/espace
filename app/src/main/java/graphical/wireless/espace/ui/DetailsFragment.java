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

    public DetailsFragment(Object data) {
        if (data instanceof PotdData) {
            PotdData temp = (PotdData) data;

            date = temp.getDate();
            imageURL = temp.getUrl();
            title = temp.getTitle();
            author = temp.getCopyright();
            desc = temp.getExplanation();

        } else if (data instanceof NewsData) {
            NewsData temp = (NewsData) data;

            date = temp.getPublishedAt();
            imageURL = temp.getUrlToImage();
            title = temp.getTitle();
            author = temp.getAuthor();
            desc = temp.getDescription();
        } else {
            PlanetData temp = (PlanetData) data;

            date = "";
            imageID = temp.getImageId();
            title = temp.getName();
            author = "";
            desc = temp.getDesc();
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
