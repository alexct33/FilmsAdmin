package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by felixferrercomas on 29/12/16.
 */

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.ViewHolder> {

    private List<Film> films;

    public FilmsAdapter(List<Film> films) {
        this.films = films;
    }


    @Override
    public FilmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.cardview_film, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(FilmsAdapter.ViewHolder holder, final int position){
        holder.titol_film.setText(films.get(position).getTitle());
        holder.atrib_film.setText(films.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView filmCardView;
        TextView titol_film, atrib_film;
        Button eliminar;
        public ViewHolder(View itemView) {
            super(itemView);
            filmCardView = (CardView) itemView.findViewById(R.id.filmCardView);
            titol_film = (TextView) itemView.findViewById(R.id.titol_film);
            atrib_film = (TextView) itemView.findViewById(R.id.atribut_film);
            eliminar = (Button) itemView.findViewById(R.id.del_button);
        }
    }
}
