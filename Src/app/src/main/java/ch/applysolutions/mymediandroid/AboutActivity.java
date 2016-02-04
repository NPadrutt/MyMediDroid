package ch.applysolutions.mymediandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.example.applysolutions.mymediandroid.BuildConfig;
import com.example.applysolutions.mymediandroid.R;

public class AboutActivity extends AppCompatActivity {

    private TextView versionLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);

        versionLabel = (TextView)  findViewById(R.id.textViewVersion);

        versionLabel.setText(BuildConfig.VERSION_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }
}
