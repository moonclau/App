package com.example.claudia.chiquilt;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PersonaViewHolder {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView nombre,edad,genero;
        Button eliminar,editar;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            nombre=(TextView) itemView.findViewById(R.id.obj_nombre);
            //edad=(TextView) itemView.findViewById(R.id.obj_edad);
            //genero=(TextView) itemView.findViewById(R.id.obj_genero);
            eliminar=(Button) itemView.findViewById(R.id.obj_eliminar);
            //editar=(Button) itemView.findViewById(R.id.obj_editar);
        }
    }
}
