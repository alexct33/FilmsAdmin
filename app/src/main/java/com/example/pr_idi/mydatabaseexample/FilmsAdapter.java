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
        public ImageButton eliminar;
        private FilmData database;

        public FilmsViewHolder(View v) {

            super(v);
            //imagen = (ImageView) v.findViewById(R.id.imagen);
            titulo = (TextView) v.findViewById(R.id.titulo);
            prota = (TextView) v.findViewById(R.id.protagonista);
            any = (TextView) v.findViewById(R.id.any);
            //eliminar = (ImageButton) v.findViewById(R.id.btn_eliminar);
            database = new FilmData(v.getContext());
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
        /*viewHolder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Eliminar pel·lícula?")
                        .setMessage("Segur que vols eliminar la pel·lícula?")
                        .setPositiveButton("Sí",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        viewHolder.database.open();
                                        List<Film> pelis = viewHolder.database.getFilmsByTitle(items.get(i).getTitle());
                                        viewHolder.database.deleteFilm(pelis.get(0));
                                        viewHolder.database.close();
                                        items.remove(i);
                                        notifyDataSetChanged();
                                        notifyItemRemoved(i);
                                        notifyItemRangeChanged(i, items.size());
                                    }
                                })
                        .setNegativeButton("Cancel·la",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
            }
        });*/

    }
}