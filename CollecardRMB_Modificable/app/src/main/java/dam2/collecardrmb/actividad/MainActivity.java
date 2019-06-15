package dam2.collecardrmb.actividad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import dam2.collecardrmb.R;
import dam2.collecardrmb.adaptador.cartaAdapter;
import dam2.collecardrmb.modelo.Card;
import dam2.collecardrmb.modelo.User;

public class MainActivity extends AppCompatActivity {

    RecyclerView mrecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mdatabaseReference;
    List<Card> mcartas;
    cartaAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.mipmap.ic_collecard);
        getSupportActionBar().setSubtitle("@string/Menú_aplicación");

        mrecyclerView = findViewById(R.id.mainActivityList);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mcartas = new ArrayList<>();

        mfirebaseDatabase = FirebaseDatabase.getInstance();

        madapter = new cartaAdapter(this, R.layout.row_recyclerview, mcartas, new cartaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Card carta, int position) {
                Toast.makeText(getApplicationContext(), carta.toString(), Toast.LENGTH_LONG).show();
            }
        });

        mrecyclerView.setAdapter(madapter);

        registerForContextMenu(mrecyclerView);

        mfirebaseDatabase.getReference().child("carta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mcartas.removeAll(mcartas);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Card carta = snapshot.getValue(Card.class);
                    carta.setIdCarta(snapshot.getKey());
                    mcartas.add(carta);

                }

                madapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.list_card_item_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listItemInfo:
                Toast.makeText(this,"Esta es la opción de información de" +
                        " la carta. ", Toast.LENGTH_LONG).show();
                break;
            case R.id.listItemDelete:
                Toast.makeText(this,"Esta es la opción de borrar la carta", Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}

