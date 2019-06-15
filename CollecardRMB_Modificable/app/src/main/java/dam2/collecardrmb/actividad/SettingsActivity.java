package dam2.collecardrmb.actividad;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import dam2.collecardrmb.R;

public class SettingsActivity extends AppCompatActivity {

    Spinner spinnerctrl;
    Button btn;
    Locale myLocale;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spinnerctrl = (Spinner) findViewById(R.id.spinner1);
        spinnerctrl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (pos == 1) {

                    Toast.makeText(parent.getContext(),
                            "Has escogido español", Toast.LENGTH_SHORT)
                            .show();
                    setLocale("es");

                    Intent intent1 = new Intent(SettingsActivity.this, MenuActivity.class);
                    startActivity(intent1);
                    finish();
                } else if (pos == 2) {

                    Toast.makeText(parent.getContext(),
                            "You have selected english", Toast.LENGTH_SHORT)
                            .show();
                    setLocale("en");


                    Intent intent1 = new Intent(SettingsActivity.this, MenuActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SettingsActivity.class);
        startActivity(refresh);
    }
}
