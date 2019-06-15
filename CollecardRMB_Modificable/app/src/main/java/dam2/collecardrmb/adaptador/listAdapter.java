package dam2.collecardrmb.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dam2.collecardrmb.R;
import dam2.collecardrmb.modelo.Lista;

public class listAdapter extends RecyclerView {

    private int layout ;
    private Context contexto ;
    private List<Lista> data = null ;

    public listAdapter(@NonNull Context context, int layout, Context contexto, List<Lista> data) {
        super(context);
        this.layout = layout;
        this.contexto = contexto;
        this.data = data;
    }

    /**
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    public listHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Inflamos el layout y obtenemos la vista
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(this.layout, null) ;

        // Creamos el holder
        listHolder holder = new listHolder(vista) ;

        //
        return holder ;
    }

    /**
     * @param listHolder
     * @param position
     */

    public void onBindViewHolder(@NonNull listHolder listHolder, int position) {
        listHolder.bindItem(this.data.get(position), position);
    }

    /**
     * @return
     */

    public int getItemCount() {
        return this.data.size() ;
    }

    /**
     * Para crear un menú contextual, necesitamos implementar la interfaz OnCreateContextMenuListener
     * y sobrecargar dicho método para crear dicho menú.
     */
    public class listHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {

        private TextView nombre ;

        /**
         * @param itemView
         */
        public listHolder(@NonNull View itemView) {
            super(itemView);

            // Instanciamos el elemento del layout para el ítem
            // nombre = itemView.findViewById(R.id.nombreLista) ;

            // Asociamos al ítem el menú contextual creado
            itemView.setOnCreateContextMenuListener(this) ;
        }

        /**
         * Asociamos el valor del ítem a la vista correspondiente del layout
         * @param item
         * @param position
         */
        public void bindItem(Lista item, int position) {
            //
            nombre.setText(item.getTitulo()) ;
        }

        /**
         * Crea un menú contextual asociado a cada uno de los ítems
         * @param menu
         * @param v
         * @param menuInfo
         */
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //
            menu.add(getAdapterPosition(), 1, 0, "borrar");
            menu.add(getAdapterPosition(),2,1,"info");
        }
    }
}
