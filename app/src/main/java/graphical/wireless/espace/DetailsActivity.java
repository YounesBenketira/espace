package graphical.wireless.espace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.Set;

import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.NewsData;
import graphical.wireless.espace.ui.data.PotdData;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        String dataType = "";
        for (String key : intent.getExtras().keySet())
            dataType = key;

        EspaceData parcel;
        switch (dataType) {
            case "potdData":
                parcel = intent.getParcelableExtra("potdData");

                ((TextView) findViewById(R.id.details_title)).setText(parcel.getMainText());
                ((TextView) findViewById(R.id.details_description)).setText(parcel.getAuxText());
                ((TextView) findViewById(R.id.details_date)).setText(parcel.getDateText());
                ((ImageView) findViewById(R.id.details_image)).setImageResource(parcel.getImageId());
                break;
            case "planetData":
                parcel = intent.getParcelableExtra("planetData");

                ((TextView) findViewById(R.id.details_title)).setText(parcel.getMainText());
                ((TextView) findViewById(R.id.details_description)).setText(parcel.getAuxText());
                ((ImageView) findViewById(R.id.details_image)).setImageResource(parcel.getImageId());
                ((TextView) findViewById(R.id.details_author)).setText("");
                ((TextView) findViewById(R.id.details_date)).setText("");
                break;
            case "newsData":
                parcel = intent.getParcelableExtra("newsData");

                ((TextView) findViewById(R.id.details_title)).setText(parcel.getMainText());
                ((TextView) findViewById(R.id.details_description)).setText(parcel.getAuxText());
                ((ImageView) findViewById(R.id.details_image)).setImageResource(parcel.getImageId());
                ((TextView) findViewById(R.id.details_author)).setText("");
                ((TextView) findViewById(R.id.details_date)).setText("");
                break;
        }


    }
}
