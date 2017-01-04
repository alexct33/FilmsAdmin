package com.example.pr_idi.mydatabaseexample;


import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
    private Spinner actorSpinner;
    private List<String> actors = new ArrayList<String>();
    private String actorEscollit;
    private String id_vista_actual;
    private TextView cerca_actor;
    private TextView input_cerca_titol;
    private Button btn_cercar;
    private boolean title_entered;
    private String titulo_entrado;
    private LinearLayout lltitle;


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

        cerca_actor = (TextView) rootView.findViewById(R.id.text_cerca_actor);
        input_cerca_titol = (TextView) rootView.findViewById(R.id.et_cerca_titol);
        btn_cercar = (Button) rootView.findViewById(R.id.btn_Cercar);
        lltitle = (LinearLayout) rootView.findViewById(R.id.llTitle);
        title_entered = false;


        id_vista_actual = getArguments().getString("id");

        //SPINNER PER FILTRAR PER ACTOR/ACTRIU
        actorSpinner = (Spinner) rootView.findViewById(R.id.actorSpinner);
        if (id_vista_actual.equals(String.valueOf(R.id.TitleFilter))) {
            actorSpinner.setVisibility(View.GONE);
            cerca_actor.setVisibility(View.GONE);
            input_cerca_titol.setVisibility(View.VISIBLE);
            btn_cercar.setVisibility(View.VISIBLE);
        }
        else if( id_vista_actual.equals(String.valueOf(R.id.AnyFilter))) {
            actorSpinner.setVisibility(View.GONE);
            cerca_actor.setVisibility(View.GONE);
            input_cerca_titol.setVisibility(View.GONE);
            btn_cercar.setVisibility(View.VISIBLE);
        }
        else  {
            actorSpinner.setVisibility(View.VISIBLE);
            cerca_actor.setVisibility(View.VISIBLE);
            input_cerca_titol.setVisibility(View.GONE);
            btn_cercar.setVisibility(View.GONE);
        }
        actorEscollit = null;
        //----------------------
        new LoadAdapter().execute("");

        return rootView;
    }

    private class LoadAdapter extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... content) {


            filmData.open();
            //filmData.firstInserts();


            if (id_vista_actual.equals(String.valueOf(R.id.TitleFilter))) {
                lista_films = filmData.getFilmsBy(MySQLiteHelper.COLUMN_TITLE);
                 if (title_entered) lista_films = filmData.getFilmsByTitle(titulo_entrado);
            }
            else if (id_vista_actual.equals(String.valueOf(R.id.AnyFilter))) {
                lista_films = filmData.getFilmsBy(MySQLiteHelper.COLUMN_YEAR_RELEASE);
            }
            else if (actorEscollit == null || actorEscollit == "Tots") {
                lista_films = filmData.getFilmsBy(MySQLiteHelper.COLUMN_TITLE);
            }
            else {
                 lista_films = filmData.getFilmsByActor(actorEscollit);
             }

            title_entered = false;

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
            boolean igual = false;
            for (int i= 0; i<lista_films.size(); ++i ) {
                igual = false;
                for (int j = 0; j < actors.size(); ++j)
                    if (actors.get(j).equals(lista_films.get(i).getProtagonist()))
                        igual = true;
                if (!igual) actors.add(lista_films.get(i).getProtagonist());
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

            btn_cercar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    titulo_entrado = String.valueOf(input_cerca_titol.getText());
                    if (titulo_entrado != null || titulo_entrado != "Cercar per títol...") title_entered = true;
                    doInBackground();
                    recycler.setAdapter(adapter);

                }
            });


            actorSpinner.setAdapter(spinnerAdapter);
            // Usar un administrador para LinearLayout
            recycler.setAdapter(adapter);

            recycler.addOnItemTouchListener(
                    new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //Toast.makeText(view.getContext(), lista_films.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), bioFilmActivity.class);
                            intent.putExtra("title", lista_films.get(position).getTitle());
                            intent.putExtra("year", lista_films.get(position).getYear());
                            intent.putExtra("country", lista_films.get(position).getCountry());
                            intent.putExtra("director", lista_films.get(position).getDirector());
                            intent.putExtra("prota", lista_films.get(position).getProtagonist());
                            intent.putExtra("nota", lista_films.get(position).getCritics_rate());
                            startActivityForResult(intent,0);
                        }
                    })
            );
        }
    }


}
