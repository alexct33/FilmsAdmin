package com.example.pr_idi.mydatabaseexample;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
    private Spinner actorSpinner;
    private List<String> actors = new ArrayList<String>();
    private String actorEscollit;

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

        //SPINNER PER FILTRAR PER ACTOR/ACTRIU
        actorSpinner = (Spinner) rootView.findViewById(R.id.actorSpinner);

        actorEscollit = null;
        //----------------------
        new LoadAdapter().execute("");

        return rootView;
    }

    private class LoadAdapter extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... content) {


            filmData.open();
            filmData.firstInserts();

             if (actorEscollit == null || actorEscollit == "Tots") {
                 lista_films = filmData.getFilmsBy(MySQLiteHelper.COLUMN_TITLE);
             }
            else {
                 lista_films = filmData.getFilmsByActor(actorEscollit);
             }


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
            actors.add("Tots");
            for (int i= 0; i<lista_films.size(); ++i ) {
                actors.add(lista_films.get(i).getProtagonist());
            }

            final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, actors);

            actorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int pos, long id) {
                    actorEscollit = (String) actorSpinner.getItemAtPosition(pos);
                    spinnerAdapter.notifyDataSetChanged();
                    doInBackground();
                    recycler.setAdapter(adapter);


                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    actorEscollit = null;

                }
            });


            actorSpinner.setAdapter(spinnerAdapter);
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
