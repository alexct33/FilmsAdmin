package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class bioFilmActivity extends AppCompatActivity {

    private TextView Titol;
    private TextView Pais;
    private TextView Any;
    private TextView Director;
    private TextView Prota;
    private EditText Nota;
    private FilmData database;
    private Button btnPuntuar;
    private ImageButton btnEliminar;

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
        btnEliminar = (ImageButton) findViewById(R.id.btn_eliminar);

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
                int nota = Integer.valueOf(String.valueOf(Nota.getText()));
                boolean modificat = false;
                if ( nota > 10) Toast.makeText(v.getContext(), "La nota ha de ser un número entre 0 i 10", Toast.LENGTH_SHORT).show();
                else {
                    modificat = database.modificar_nota(Integer.valueOf(String.valueOf(Nota.getText())), Titol.getText().toString());
                }
                database.close();

                if (!modificat) Toast.makeText(v.getContext(), "La nota NO s'ha modificat correctament", Toast.LENGTH_SHORT).show();
                else Toast.makeText(v.getContext(), "La nota s'ha modificat correctament", Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Eliminar pel·lícula?")
                        .setMessage("Segur que vols eliminar la pel·lícula?")
                        .setPositiveButton("Sí",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        database.open();
                                        List<Film> pelis = database.getFilmsByTitle(Titol.getText().toString());
                                        database.deleteFilm(pelis.get(0));
                                        database.close();
                                        /*items.remove(i);
                                        notifyDataSetChanged();
                                        notifyItemRemoved(i);
                                        notifyItemRangeChanged(i, items.size());*/
                                        Toast.makeText(v.getContext(), "La pelicula s'ha eliminat correctament", Toast.LENGTH_SHORT).show();
                                        finish();
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
        });

    }



}
