package com.example.pr_idi.mydatabaseexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class crearFilmActivity extends AppCompatActivity {
    private EditText Titol;
    private EditText Pais;
    private EditText Any;
    private EditText Director;
    private EditText Prota;
    private EditText Nota;
    private Button Afegir;
    private FilmData filmData;

    private String titol;
    private String pais;
    private String any;
    private String director;
    private String prota;
    private String nota;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_film);

        Titol = (EditText) findViewById(R.id.etTitol);
        Pais = (EditText) findViewById(R.id.etPais);
        Any = (EditText) findViewById(R.id.etAny);
        Director = (EditText) findViewById(R.id.etDirector);
        Prota = (EditText) findViewById(R.id.etProtagonista);
        Nota = (EditText) findViewById(R.id.etNota);
        Afegir = (Button) findViewById(R.id.btn_afegirFilm);

        actualitzar_et();

        filmData = new FilmData(getApplicationContext());
        Afegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadAdapter().execute("");

            }
        });
    }

    private class LoadAdapter extends AsyncTask<String, Integer, String> {
        private boolean creatOK;
        private boolean camps_buits;
        private boolean nota_erronea;
        @Override
        protected String doInBackground(String... content) {
            creatOK = camps_buits = nota_erronea = false;
            actualitzar_et();
            if (titol.isEmpty() || pais.isEmpty() || any.isEmpty()
                    || director.isEmpty() || prota.isEmpty() || nota.isEmpty()) {
                camps_buits = true;
            }
            else if (Integer.parseInt(nota) > 10) nota_erronea = true;
            else {
                filmData.open();
                Film peli = filmData.createFilm(titol, pais, Integer.parseInt(any), director, prota, Integer.parseInt(nota));
                if (peli.getTitle().equals(titol)) creatOK = true;
                //----------------------
                filmData.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast toast;
            if (camps_buits) {
                toast = Toast.makeText(getApplicationContext(), "Falta algun camp per omplir", Toast.LENGTH_LONG);
            }
            else if (creatOK) {
                toast = Toast.makeText(getApplicationContext(), "La pel·lícula s'ha afegit correctament", Toast.LENGTH_LONG);
                finish();
            }
            else if (nota_erronea)
                toast = Toast.makeText(getApplicationContext(), "La nota ha de ser un número entre 0 i 10", Toast.LENGTH_SHORT);

            else toast = Toast.makeText(getApplicationContext(), "La pel·lícula NO s'ha afegit correctament", Toast.LENGTH_LONG);

            toast.show();
        }
    }

    private void actualitzar_et() {
        titol = Titol.getText().toString();
        pais = Pais.getText().toString();
        any = Any.getText().toString();
        director = Director.getText().toString();
        prota = Prota.getText().toString();
        nota = Nota.getText().toString();
    }
}
