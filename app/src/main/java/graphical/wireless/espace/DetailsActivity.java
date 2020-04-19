package graphical.wireless.espace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import graphical.wireless.espace.ui.data.EspaceData;
import graphical.wireless.espace.ui.data.PotdData;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        EspaceData parcel = intent.getParcelableExtra("potdData");

        ((TextView) findViewById(R.id.details_title)).setText(parcel.getMainText());
        ((TextView) findViewById(R.id.details_description)).setText(parcel.getAuxText());
        ((TextView) findViewById(R.id.details_date)).setText(parcel.getDateText());
        ((ImageView)findViewById(R.id.details_image)).setImageResource(parcel.getImageId());
    }
}
