package dam2.collecardrmb.actividad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.LoginFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dam2.collecardrmb.R;
import dam2.collecardrmb.modelo.User;

public class RegisterActivity extends AppCompatActivity {

    /* Elementos del layout */
    private EditText nom, ape, email, ph, usu, pwd, conpwd;
    private Button contbtn;

    /* Firebase: autenticación y la BBDD */
    private FirebaseAuth auten;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nom = findViewById(R.id.register_firstName);
        ape = findViewById(R.id.register_lastName);
        email = findViewById(R.id.register_email);
        ph = findViewById(R.id.register_ph);
        usu = findViewById(R.id.register_alias);
        pwd = findViewById(R.id.register_pwd);
        conpwd = findViewById(R.id.register_confirm_pwd);

        contbtn = findViewById(R.id.register_btn);

        InputFilter txtFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source,
                                       int start, int end,
                                       Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (!Character.isAlphabetic(source.charAt(i))&&!Character.toString(source.charAt(i)).equals(" ")) {
                        Toast.makeText(RegisterActivity.this,
                                R.string.register_error_alphabetic,
                                Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }

                return null;
            }
        };


        InputFilter txtnumFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source,
                                       int start, int end,
                                       Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) {
                        Toast.makeText(RegisterActivity.this,
                                R.string.register_error_alphanumeric,
                                Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }

                return null;
            }
        };

        nom.setFilters(new InputFilter[]{txtFilter});
        ape.setFilters(new InputFilter[]{txtFilter});
        ph.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
        usu.setFilters(new InputFilter[]{txtnumFilter});
        pwd.setFilters(new InputFilter[]{new LoginFilter.PasswordFilterGMail()});
        conpwd.setFilters(new InputFilter[]{new LoginFilter.PasswordFilterGMail()});

        contbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                String name = nom.getText().toString().trim();
                String apel = ape.getText().toString().trim();
                String tlf = ph.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String user = usu.getText().toString().trim();
                String pass = pwd.getText().toString().trim();
                String conpass = conpwd.getText().toString().trim();

                if (name.isEmpty() || apel.isEmpty() || tlf.isEmpty() || mail.isEmpty() || user.isEmpty() || pass.isEmpty() || conpass.isEmpty()) {
                    Snackbar.make(vista, R.string.register_error_login, Snackbar.LENGTH_LONG).show();
                } else if (!pass.equals(conpass)) {
                    Snackbar.make(vista, R.string.register_error_login_pwd, Snackbar.LENGTH_LONG).show();
                } else if (pwd.length() < 5) {
                    Snackbar.make(vista, R.string.register_error_login_pwd_length, Snackbar.LENGTH_LONG).show();
                } else {

                    // Obtenemos una instancia del objeto FirebaseAuth
                    auten = FirebaseAuth.getInstance() ;

                    auten.createUserWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        // Si, nuestro usuario tiene más información asociada,
                                        // tendremos que guardarla en la base de datos.

                                        // Obtenemos una instancia de la base de datos
                                        db = FirebaseDatabase.getInstance();

                                        // Obtenemos una referencia al documento (tabla) que contendrá
                                        // la información. Si el documento no existe, Firebase lo crea.
                                        DatabaseReference ref = db.getReference("usuario");

                                        // Obtener los datos proporcionados por Firebase sobre el usuario
                                        // registrado.
                                        FirebaseUser fbUser = auten.getCurrentUser();

                                        // Preguntamos por el UID
                                        String uid = fbUser.getUid();

                                        // Creamos nuestro objeto usuario con los datos proporcionados
                                        // a través del formulario.
                                        User newUser = new User(uid,
                                                nom.getText().toString(),
                                                ape.getText().toString(),
                                                email.getText().toString(),
                                                ph.getText().toString(),
                                                usu.getText().toString());

                                        // Guardamos la información en la base de datos de Firebase,
                                        // asociados al UID.
                                        ref.child(uid).setValue(newUser);

                                        /* Nuestra intención es la de lanzar la actividad de registro */
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        /* Esto es para pasar información de una actividad a otra */
                                        startActivity(intent);
                                        Toast.makeText(RegisterActivity.this, "Proceda a iniciar sesión con su nueva cuenta.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Se ha producido un error en el registro.", Toast.LENGTH_LONG).show();
                                    }

                                }
                            }) ;
                }
            }
        });
    }
}
