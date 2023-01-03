/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ca.coffeeshopstudio.meksheets.R;

public class ActivityAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Email = new Intent(Intent.ACTION_SEND);
                    Email.setType("text/email");
                    Email.putExtra(Intent.EXTRA_EMAIL,
                            new String[]{"support@coffeeshopstudio.ca"});  //developer 's email
                    Email.putExtra(Intent.EXTRA_SUBJECT,
                            "Budget Miser"); // Email 's Subject
                    startActivity(Intent.createChooser(Email, "Send Feedback:"));
                }
            });
        }

        PackageInfo nfo;
        String versionName = "0.00";
        int versionCode = 0;
        try {
            nfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = nfo.versionCode;
            versionName = nfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView version = findViewById(R.id.txtVersion);
        if (version != null) {
            Resources res = getResources();
            String text = String.format(res.getString(R.string.version), versionName, versionCode);
            version.setText(text);
        }
    }

}
