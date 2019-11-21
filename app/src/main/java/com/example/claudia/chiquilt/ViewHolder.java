package com.example.claudia.chiquilt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mv;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mv=itemView;
    }
    public void setDetails(Context ctx,String image){
        //vista
        ImageView imageView=mv.findViewById(R.id.rimagenview);
        Picasso.get().load(image).into(imageView);
    }
}
