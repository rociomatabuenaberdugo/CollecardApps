package dam2.collecardrmb.modelo;

public class Lista {

    private int idLista;
    /* private int idUsuario; */
    private String titulo;
    /* private boolean esPublica */

    public Lista(int idLista) {
        this.idLista = idLista;
    }

    public Lista(int idLista, String titulo) {
        this.idLista = idLista;
        this.titulo = titulo;
    }

    public int getIdLista() {
        return idLista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
