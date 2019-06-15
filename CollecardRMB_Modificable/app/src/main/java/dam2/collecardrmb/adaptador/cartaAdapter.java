package dam2.collecardrmb.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dam2.collecardrmb.R;
import dam2.collecardrmb.actividad.MainActivity;
import dam2.collecardrmb.modelo.Card;

public class cartaAdapter extends RecyclerView.Adapter<cartaAdapter.cardHolder> {

    private int layout ;
    private List<Card> data ;
    private OnItemClickListener listener ;
    private int posicion;


    private static Context contexto ;

    public cartaAdapter(Context contexto, int layout, List<Card> data, OnItemClickListener listener) {
        this.listener = listener ;
        this.contexto = contexto ;
        this.layout   = layout;
        this.data     = data;
    }

    @NonNull
    @Override
    public cardHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext()) ;

        View vista = inflater.inflate(this.layout, null) ;

        cardHolder holder = new cardHolder(vista) ;

        return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull cardHolder cardHolder, int pos) {

        cardHolder.bindItem(data.get(pos)) ;
    }


    @Override
    public int getItemCount() {

        return this.data.size() ;
    }

    public int getPosicion() { return posicion; }


    public class cardHolder extends RecyclerView.ViewHolder {

        private ImageView poster ;
        private TextView titulo ;
        private TextView descripcion;
        private View mView;


        public cardHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.cardImage) ;
            titulo = itemView.findViewById(R.id.cardName) ;
            // titulo = itemView.findViewById(R.id.cardDescripcion) ;

            mView = itemView;



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    posicion = getAdapterPosition();
                    return false;
                }
            });
        }

        public void setDetails(Context ctx, String name, String description, String image) {
            ImageView mImageView = mView.findViewById(R.id.cardImage);
            TextView mTitleView = mView.findViewById(R.id.cardName);


            mTitleView.setText(name);
            Picasso.with(ctx).load(image).into(mImageView);
        }

        public void bindItem(final Card card) {


            titulo.setText(card.getNombre()) ;


            Picasso.with(contexto)
                    .load(card.getImagenCarta())
                    .fit()
                    .centerCrop()
                    .into(poster) ;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Card film, int position) ;
    }
}
