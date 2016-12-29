package com.example.pr_idi.mydatabaseexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFilterActivity extends Fragment {

    private RecyclerView filmRecycler;
    private List<Film> lista_films;
    private FilmData filmData;

    public TitleFilterActivity() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_title_filter, container, false);

        filmRecycler = (RecyclerView) v.findViewById(R.id.rvTitle);
        filmRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));
        filmRecycler.setHasFixedSize(true);
        filmData = new FilmData(v.getContext());
        filmData.open();
        filmData.firstInserts();

        lista_films = filmData.getFilmsBy(MySQLiteHelper.COLUMN_TITLE);

        FilmsAdapter adapter = new FilmsAdapter(lista_films);
        filmRecycler.setAdapter(adapter);

        return v;
    }

}
