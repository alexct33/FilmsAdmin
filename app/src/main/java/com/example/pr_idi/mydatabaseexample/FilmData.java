package com.example.pr_idi.mydatabaseexample;

/**
 * FilmData
 * Created by pr_idi on 10/11/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FilmData {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    // Here we only select Title and Director, must select the appropriate columns
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_COUNTRY, MySQLiteHelper.COLUMN_YEAR_RELEASE, MySQLiteHelper.COLUMN_DIRECTOR, MySQLiteHelper.COLUMN_PROTAGONIST, MySQLiteHelper.COLUMN_CRITICS_RATE};

    public FilmData(Context context) {
        dbHelper = new MySQLiteHelper(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void firstInserts() {
        //database.delete(MySQLiteHelper.TABLE_FILMS, null, null);
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, "La vida de Brian");
        boolean exists = existsFilm(values);
        values.put(MySQLiteHelper.COLUMN_COUNTRY, "U.K.");
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, 1979);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, "Terry Jones");
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, "Brian Cohen");
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, 7);


        //database.delete("films", "title= 'La vida de Brian'",null);
        //database.delete("films", "title= 'El padrino'",null);

        long insertID;
        if (!exists) insertID = database.insert(MySQLiteHelper.TABLE_FILMS, null, values);

        values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, "El padrino");
        exists = existsFilm(values);
        values.put(MySQLiteHelper.COLUMN_COUNTRY, "E.E.U.U.");
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, 1972);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, "Francis Ford Coppola");
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, "Vito Corleone");
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, 9);


        //database.delete("films", "title= 'La vida de Brian'",null);

        if (!exists) insertID = database.insert(MySQLiteHelper.TABLE_FILMS, null, values);


        values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, "Pulp Fiction");
        exists = existsFilm(values);
        values.put(MySQLiteHelper.COLUMN_COUNTRY, "E.E.U.U.");
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, 1994);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, "Quentin Tarantino");
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, "Vincent Vega");
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, 9);


        //database.delete("films", "title= 'La vida de Brian'",null);

        if (!exists) insertID = database.insert(MySQLiteHelper.TABLE_FILMS, null, values);


        values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, "Titanic");
        exists = existsFilm(values);
        values.put(MySQLiteHelper.COLUMN_COUNTRY, "E.E.U.U.");
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, 1997);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, "James Cameron");
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, "Jack Dawson");
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, 10);


        //database.delete("films", "title= 'La vida de Brian'",null);

        if (!exists) insertID = database.insert(MySQLiteHelper.TABLE_FILMS, null, values);



    }

    public boolean existsFilm(ContentValues values){
        List<Film> films = new ArrayList<>();
        films = getAllFilms();
        boolean peliExistent = false;
        for (int i = 0; i < films.size(); ++i) {
            String title = "title=" + films.get(i).getTitle();
            if (title.equals(values.toString()))
                peliExistent = true;
        }
        return peliExistent;
    }

    public Film createFilm(String title, String pais, int any, String director, String prota, int nota) {
        ContentValues values = new ContentValues();

        // Add data: Note that this method only provides title and director
        // Must modify the method to add the full data
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, director);
        values.put(MySQLiteHelper.COLUMN_COUNTRY, pais);
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, any);
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, prota);
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, nota);

        // Actual insertion of the data using the values variable
        long insertId = database.insert(MySQLiteHelper.TABLE_FILMS, null,
                values);

        // Main activity calls this procedure to create a new film
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the database
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Film newFilm = cursorToFilm(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newFilm;
    }

    public void deleteFilm(Film film) {
        long id = film.getId();
        System.out.println("Film deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Film> getAllFilms() {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public List<Film> getFilmsByActor(String actor) {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_PROTAGONIST+" = ?", new String[]{actor}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public List<Film> getFilmsBy(String filtro) {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, null, null, null, null, filtro);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Film cursorToFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(0));
        film.setTitle(cursor.getString(1));
        film.setCountry(cursor.getString(2));
        film.setYear(cursor.getInt(3));
        film.setDirector(cursor.getString(4));
        film.setProtagonist(cursor.getString(5));
        film.setCritics_rate(cursor.getInt(6));
        return film;
    }

    public List<Film> getFilmsByTitle(String text) {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_TITLE + " = ?", new String[]{text}, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }
}