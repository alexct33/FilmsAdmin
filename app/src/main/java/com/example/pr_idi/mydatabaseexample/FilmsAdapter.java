package com.example.pr_idi.mydatabaseexample;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.List;


public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder> {
    private List<Film> items;

    public static class FilmsViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView titulo;
        public TextView prota;
        public TextView any;
        public TextView director;
        public TextView nota;
        public TextView pais;

        public FilmsViewHolder(View v) {

            super(v);
            //imagen = (ImageView) v.findViewById(R.id.imagen);
            titulo = (TextView) v.findViewById(R.id.titulo);
            prota = (TextView) v.findViewById(R.id.protagonista);
            any = (TextView) v.findViewById(R.id.any);
            director = (TextView) v.findViewById(R.id.director);
            nota = (TextView) v.findViewById(R.id.nota);
            pais = (TextView) v.findViewById(R.id.pais);
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
    public FilmsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_film, viewGroup, false);


        return new FilmsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FilmsViewHolder viewHolder, final int i) {
        //viewHolder.imagen.setImageResource(items.get(i).getPhotoId());
        viewHolder.titulo.setText(items.get(i).getTitle());
        viewHolder.prota.setText(items.get(i).getProtagonist());
        viewHolder.any.setText(String.valueOf(items.get(i).getYear()));
        viewHolder.director.setText("Directed by: " + items.get(i).getDirector());
        viewHolder.nota.setText(items.get(i).getCritics_rate() + "/10");
        viewHolder.pais.setText(items.get(i).getCountry());

    }
}