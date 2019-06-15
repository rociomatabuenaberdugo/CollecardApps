package dam2.collecardrmb.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dam2.collecardrmb.R;
import dam2.collecardrmb.modelo.Lista;

public class CollecardBBDD extends SQLiteOpenHelper {

    /* Instanciamos el objeto y el contexto */
    private static CollecardBBDD instance = null;
    private static SQLiteDatabase database;
    private static String uid;

    private Context BBDDContext;

    private FirebaseDatabase BBDDfb;

    private CollecardBBDD(@Nullable Context context, @Nullable String name,
                      @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        /* Guardamos el contexto */
        BBDDContext = context ;
    }

    /**
     * Utilizamos el patrón de diseño Singleton para crear una única instancia del
     * objeto CollecardBBDD, teniendo también de esta manera un único punto de entrada
     * a la BBDD. NOTA: hacemos que el método seá además synchronized
     * para evitar que puedan crearse diferentes instancias al mismo tiempo.
     **/
    public static synchronized CollecardBBDD getInstance(Context context, String userID) {

        /**
         *  Si aún no hemos creado la instancia, lo hacemos y llamamos al constructor con los
         *  datos necesarios.
         **/
        if (instance==null) {
            instance = new CollecardBBDD(context, "collecard", null, 2);
        }

        /* Guardamos la información sobre el identificador de usuario */
        uid = userID ;

        /* Creamos la BBDD */
        database = instance.getWritableDatabase() ;

        /* Devolvemos la instancia de la BBDD */
        return instance ;
    }

    public List<Lista> getListas() {

        List<Lista> listas = null ;

        /* Consultamos y obtenemos todas las listas de la BBDD */
        Cursor cursor = database.rawQuery("SELECT * FROM lista ; ",null) ;

        if (cursor.moveToFirst()) {

            listas = new ArrayList<Lista>();

            do {
                int id = cursor.getInt(cursor.getColumnIndex("idLista")) ;
                String titulo = cursor.getString(cursor.getColumnIndex("titulo")) ;

                listas.add(new Lista(id, titulo)) ;

            } while (cursor.moveToNext()) ;

            cursor.close() ;
        }

        return listas ;
    }

    public void meterLista(String titulo) {

        ContentValues values = new ContentValues() ;
        values.put("idUsuario", uid) ;
        values.put("titulo", titulo) ;

        database.insertOrThrow("lista",null,values) ;
    }

    /**
     * Se lanzará cuando se cree el objeto CollecardBBDD. Hemos de tener en cuenta
     * que este método se lanzará automáticamente si la BBDD no existe.
     * Sin embargo, si la BBDD ya existe y las versiones coinciden, se
     * establecerá únicamente una conexión.
     **/
    @Override
    public void onCreate(SQLiteDatabase sqliteDB) {

        //screarBBDD(sqliteDB) ;
        poblarBBDD(sqliteDB) ;
    }

    /* Se lanzará automáticamente si la BBDD existe y las versiones no coinciden. */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        /* Destruimos tablas */
        sqLiteDatabase.execSQL("DROP TABLE lista_carta ;");
        sqLiteDatabase.execSQL("DROP TABLE carta ;");
        sqLiteDatabase.execSQL("DROP TABLE lista ;");

        /* Recreamos la BBDD */
        onCreate(sqLiteDatabase) ;
    }

    /**
     * Crea la BBDD local, a partir del script SQL que hemos añadido como recurso a nuestro proyecto.
     **/
//    private void crearBBDD(SQLiteDatabase db) {
//
//        try {
//
//            InputStream is = BBDDContext.getResources().openRawResource(R.raw.collecard_local);
//
//            /* Creamos un buffer de lectura */
//            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
//
//            /* Leemos el contenido y lanzamos las instrucciones */
//            String line = buf.readLine();
//
//            while (line != null) {
//                db.execSQL(line) ;
//                line = buf.readLine();
//            }
//
//            /* Cerramos el buffer de lectura */
//            buf.close();
//
//            /* Cerramos el recurso */
//            is.close();
//
//        } catch (IOException e) {
//            Log.e("FLIXNETDB", "** Se ha producido un error en la creación de la base de datos.") ;
//            e.printStackTrace();
//        }
//    }

    /**
     * Firebase: almacenará cartas, colecciones, tiendas y listas. Esto es,
     * básicamente toda la información de nuestra aplicación.
     *
     * Localmente: guardaremos en el dispositivo nuestras listas e información tempo-
     * ral de las cartas contenidas en las mismas.
     **/
    private void poblarBBDD(SQLiteDatabase db) {

        /* Obtenemos una instancia de BBDD de Firebase Realtime Database */
        BBDDfb = FirebaseDatabase.getInstance() ;

        /* Obtener una referencia a las listas del usuario logueado */
        BBDDfb.getReference("usuarios/" + uid + "/listas")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {

                        /* Recorrer el resultado obteniendo los id de cada lista */
                        for(DataSnapshot item : ds.getChildren()) {

                            /* Localizar y obtener información sobre la lista */
                            addLista(item.getValue().toString()) ;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("COLLECARD:DB", "Se ha producido un error en la comunicación con la base de datos [" + databaseError.getCode() + "]") ;
                    }
                });

    }

    private void addLista(final String idLista) {

        BBDDfb.getReference("listas/" + idLista)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {

                        /* Guardar en la tabla LISTA */
                        ContentValues values = new ContentValues() ;
                        values.put("idLista",   idLista);
                        values.put("idUsuario", uid);
                        values.put("titulo",    ds.child("titulo").getValue().toString()) ;

                        database.insertOrThrow("lista", null, values) ;

                        /* Obtenemos las películas */
                        DataSnapshot films = ds.child("carta") ;

                        /* Iteramos sobre las cartas y obtenemos información */
                        for (DataSnapshot item : films.getChildren()) {

                            String idCarta = item.getValue().toString() ;

                            /* Obtener información y guardar la carta en la tabla carta */
                            addCarta(idCarta) ;

                            /* Relacionar la carta con la lista en la tabla lista_carta */
                            values.clear() ;
                            values.put("idLista",    idLista) ;
                            values.put("idCarta", idCarta) ;
                            database.insertOrThrow("lista_carta", null, values) ;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("COLLECARD:DB", "Se ha producido un error en la comunicación con la base de datos [" + databaseError.getCode() + "]") ;
                    }
                });
    }

    private void addCarta(final String idCarta) {

        BBDDfb.getReference("carta/" + idCarta)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {

                        /* Guardar en la tabla carta */
                        ContentValues values = new ContentValues() ;
                        values.put("idCarta",   idCarta);
                        values.put("anyo", ds.child("anyo").getValue().toString());
                        values.put("descripcion", ds.child("descripcion").getValue().toString()) ;
                        values.put("imagen", ds.child("imagen").getValue().toString()) ;
                        values.put("nombre", ds.child("nombre").getValue().toString()) ;
                        values.put("perteneceA", ds.child("perteneceA").getValue().toString()) ;
                        values.put("puntuacion", ds.child("puntuacion").getValue().toString()) ;
                        values.put("rareza", ds.child("rareza").getValue().toString()) ;
                        values.put("valor", ds.child("valor").getValue().toString()) ;

                        try {
                            database.insertOrThrow("carta", null, values);
                        } catch (SQLiteException e) {
                            /* */
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("COLLECARD:DB", "Se ha producido un error en la comunicación con la base de datos [" + databaseError.getCode() + "]") ;
                    }
                });

    }


}
