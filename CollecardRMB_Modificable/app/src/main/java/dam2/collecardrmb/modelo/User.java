package dam2.collecardrmb.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    /* Atributos */

    @Expose
    @SerializedName("idUsuario")
    private String idUsuario ;

    @Expose
    @SerializedName("nombre")
    private String nombre ;

    @Expose
    @SerializedName("apellidos")
    private String apellidos ;

    @Expose
    @SerializedName("email")
    private String email ;

    @Expose
    @SerializedName("telefono")
    private String telefono ;

    @Expose
    @SerializedName("usuario")
    private String usuario ;


    /* Constructores */

    public User() { }

    public User(  String idUsuario, String nombre, String apellidos,
                String email, String telefono, String usuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.usuario = usuario;
    }


    /* Getters */

     public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getUsuario() {
        return usuario;
    }


    /* Setters */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    /* To String */

    @Override
    public String toString() {
        return "User{" +
                // "idUsuario='" + idUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
