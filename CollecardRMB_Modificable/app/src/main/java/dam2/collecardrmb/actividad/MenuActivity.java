package dam2.collecardrmb.actividad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dam2.collecardrmb.R;
import dam2.collecardrmb.adaptador.cartaAdapter;
import dam2.collecardrmb.modelo.Card;

public class MenuActivity extends AppCompatActivity {

    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mdatabaseReference;

    private Button btnACards;
        // , btnUCards ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnACards = findViewById(R.id.btn_all_cards);
       // btnUCards = findViewById(R.id.btn_user_cards);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.mipmap.ic_collecard);
        getSupportActionBar().setSubtitle("Menú de la aplicación");

        btnACards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) ;
                startActivity(intent);
            }
        });

      /*  btnUCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_card_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listSettings:
                // Creamos la intención
                Intent intent1 = new Intent(MenuActivity.this, SettingsActivity.class);
                // Iniciamos la actividad
                startActivity(intent1);
                break;
            case R.id.listInfo:
                // Creamos la intención
                Intent intent2 = new Intent(MenuActivity.this, AppInformationActivity.class);
                // Iniciamos la actividad
                startActivity(intent2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


}
