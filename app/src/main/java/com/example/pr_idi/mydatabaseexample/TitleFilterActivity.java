package com.example.pr_idi.mydatabaseexample;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFilterActivity extends Fragment {

    private static RecyclerView recycler;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.LayoutManager lManager;
    private View rootView;
    private List<Film> lista_films;
    private FilmData filmData;

    public TitleFilterActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_title, container, false);
        lManager = new LinearLayoutManager(rootView.getContext());
        lManager.canScrollVertically();
        recycler = new RecyclerView(rootView.getContext());
        recycler = (RecyclerView) rootView.findViewById(R.id.reciclador);
        recycler.setLayoutManager(lManager);
        filmData = new FilmData(rootView.getContext());

        //----------------------
        new LoadAdapter().execute("");

        return rootView;
    }

    private class LoadAdapter extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... content) {


            filmData.open();
            filmData.firstInserts();

            lista_films = filmData.getFilmsBy(MySQLiteHelper.COLUMN_TITLE);
            //----------------------
            // Crear un nuevo adaptador
            filmData.close();
            adapter = new FilmsAdapter(lista_films);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            recycler.setHasFixedSize(true);
            filmData = new FilmData(rootView.getContext());
            // Usar un administrador para LinearLayout
            recycler.setAdapter(adapter);

            recycler.addOnItemTouchListener(
                    new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(view.getContext(), lista_films.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        }
                    })
            );
        }
    }
}
