package dam2.collecardrmb.actividad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

import dam2.collecardrmb.R;
import dam2.collecardrmb.helper.CollecardBBDD;
import dam2.collecardrmb.modelo.User;

import static android.widget.Toast.LENGTH_LONG;

public class LoginActivity extends AppCompatActivity {

    /* Elementos del layout */
    private EditText email, pwd ;
    private Button btnLogin, btnRegister ;

    /* Firebase: autenticación y la BBDD */
    private FirebaseAuth auten;
    private FirebaseDatabase db;

    private CollecardBBDD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /* Instanciamos los elementos del layout */
        email = findViewById(R.id.loginUser);
        pwd = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.loginSignIn);
        btnRegister = findViewById(R.id.loginRegister);

        /* Instancia de Firebase */
        auten = FirebaseAuth.getInstance() ;



        /* Definimos la acción "onClick" para el botón de registro */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Creamos la intención para poder abrir una nueva actividad */
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) ;
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View vista) {

                // Capturamos información de los campos de texto
                final String usr = email.getText().toString().trim();
                final String clv = pwd.getText().toString().trim();

                // Comprobar que han introducido el usuario y la contraseña
                if (usr.isEmpty() || clv.isEmpty()) {
                    Snackbar.make(vista, R.string.login_error_login_void, Snackbar.LENGTH_LONG).show();
                } else {
                    /* Intentamos iniciar sesión mediante el email y la contraseña en Firebase */
                    auten.signInWithEmailAndPassword(usr, clv).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                /* Obtenemos el id del usuario */
                                String uid = auten.getCurrentUser().getUid();

                                // Accedemos a la BBDD para obtener información sobre
                                // el usuario, lo serializamos y enviamos a la siguiente actividad.
                                db = FirebaseDatabase.getInstance();

                                // Obtenemos una referencia a la tabla usuario
                                DatabaseReference ref = db.getReference("usuario");

                                // Buscamos la información sobre el usuario
                                ref.child(uid)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                // Creamos la intención
                                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);

                                                    // Iniciamos la actividad
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Snackbar.make(vista, R.string.login_error_login_incorrect, Snackbar.LENGTH_LONG).show();
                                            }

                                        });
                            }
                        }  // login completo

                    });
                }
            }
        }) ;
    }
}
