package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class bioFilmActivity extends AppCompatActivity {

    private TextView Titol;
    private TextView Pais;
    private TextView Any;
    private TextView Director;
    private TextView Prota;
    private EditText Nota;
    private FilmData database;
    private Button btnPuntuar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_film);

        Titol = (TextView) findViewById(R.id.tvBioTitol);
        Pais = (TextView) findViewById(R.id.tvBioPais);
        Any = (TextView) findViewById(R.id.tvBioAny);
        Director = (TextView) findViewById(R.id.tvBioDirector);
        Prota = (TextView) findViewById(R.id.tvBioProta);
        Nota = (EditText) findViewById(R.id.tvNota);
        btnPuntuar = (Button) findViewById(R.id.btn_puntuar);

        database = new FilmData(getApplicationContext());

        Titol.setText(getIntent().getExtras().getString("title"));
        Pais.setText(getIntent().getExtras().getString("country"));
        Any.setText(String.valueOf(getIntent().getExtras().getInt("year")));
        Director.setText(getIntent().getExtras().getString("director"));
        Prota.setText(getIntent().getExtras().getString("prota"));
        Nota.setText(String.valueOf(getIntent().getExtras().getInt("nota")));

        btnPuntuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                database.open();
                boolean modificat = database.modificar_nota(Integer.valueOf(String.valueOf(Nota.getText())), Titol.getText().toString());
                database.close();
                if (!modificat) Toast.makeText(v.getContext(), "La nota NO s'ha modificat correctament", Toast.LENGTH_SHORT).show();
                else Toast.makeText(v.getContext(), "La nota s'ha modificat correctament", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
