package dam2.collecardrmb.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Card implements Serializable {

    @Expose
    @SerializedName("idCarta")
    private String idCarta ;
    @Expose
    @SerializedName("nombre")
    private String nombre ;
    @Expose
    @SerializedName("descripcion")
    private String descripcion ;
    @Expose
    @SerializedName("anyo")
    private String anyo ;
    @Expose
    @SerializedName("rareza")
    private String rareza ;
    @Expose
    @SerializedName("puntuacion")
    private float puntuacion ;
    @Expose
    @SerializedName("valor")
    private float valor ;
    @Expose
    @SerializedName("imagenCarta")
    private String imagenCarta;

    public Card() {
    }

    public Card(String nombre, String imagenCarta) {
        this.nombre = nombre;
        this.imagenCarta = imagenCarta;
    }

    public Card(String nombre, String descripcion, String anyo, String rareza, int puntuacion, float valor, String imagenCarta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.anyo = anyo;
        this.rareza = rareza;
        this.puntuacion = puntuacion;
        this.valor = valor;
        this.imagenCarta = imagenCarta;
    }

    public String getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(String idCarta) {
        this.idCarta = idCarta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = String.valueOf(anyo);
    }

    public String getRareza() {
        return rareza;
    }

    public void setRareza(String rareza) {
        this.rareza = rareza;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getImagenCarta() {
        return imagenCarta;
    }

    public void setImagenCarta(String imagenCarta) {
        this.imagenCarta = imagenCarta;
    }

    @Override
    public String toString() {
        return "Card{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", anyo='" + anyo + '\'' +
                ", rareza='" + rareza + '\'' +
                ", puntuacion=" + puntuacion +
                ", valor=" + valor +
                '}';
    }
}
