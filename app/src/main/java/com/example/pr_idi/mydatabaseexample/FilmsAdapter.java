package com.example.pr_idi.mydatabaseexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;


public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.NoticiasViewHolder> {
    private List<Film> items;

    public static class NoticiasViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView titulo;
        public TextView prota;
        public TextView any;

        public NoticiasViewHolder(View v) {

            super(v);
            //imagen = (ImageView) v.findViewById(R.id.imagen);
            titulo = (TextView) v.findViewById(R.id.titulo);
            prota = (TextView) v.findViewById(R.id.protagonista);
            any = (TextView) v.findViewById(R.id.any);
        }
    }

    public FilmsAdapter(List<Film> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public NoticiasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_film, viewGroup, false);
        return new NoticiasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoticiasViewHolder viewHolder, int i) {
        //viewHolder.imagen.setImageResource(items.get(i).getPhotoId());
        viewHolder.titulo.setText(items.get(i).getTitle());
        viewHolder.prota.setText(items.get(i).getProtagonist());
        viewHolder.any.setText(String.valueOf(items.get(i).getYear()));


    }
}