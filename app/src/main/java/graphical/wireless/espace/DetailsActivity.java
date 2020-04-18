package graphical.wireless.espace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String date = intent.getStringExtra("date");

        ((TextView) findViewById(R.id.details_title)).setText(title);
        ((TextView) findViewById(R.id.details_description)).setText(desc);
        ((TextView) findViewById(R.id.details_date)).setText(date);

    }
}
