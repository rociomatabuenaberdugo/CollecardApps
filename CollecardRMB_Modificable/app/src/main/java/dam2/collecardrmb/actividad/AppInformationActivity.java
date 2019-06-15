package dam2.collecardrmb.actividad;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dam2.collecardrmb.R;
import dam2.collecardrmb.modelo.versionDialogAlert;

public class AppInformationActivity extends AppCompatActivity {

    FloatingActionButton inf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_information);
        inf=(FloatingActionButton)findViewById(R.id.info_version_btn);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.mipmap.ic_collecard);
        getSupportActionBar().setSubtitle("Información de la aplicación");

        inf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVersion();
            }
        });
    }

    public void openVersion() {
        versionDialogAlert infoVersion = new versionDialogAlert();
        infoVersion.show(getSupportFragmentManager(), "versión del programa");
    }


}
