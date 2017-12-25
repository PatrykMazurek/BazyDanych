package com.example.patryyyk21.bazydanych;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patryyyk21 on 2017-12-05.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "person_db.db";
    private static final String TABLE_CONTACT = "person";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LASTNAME = "last_name";
    private static final String KEY_CITY = "city";
    private static final String KEY_POST_CODE = "post_code";
    private Context con;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        con = context;
    }

    // metoda wywoływana kiedy SQLiteOpenHelper nie znajdzie bazy danych
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACT + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_LASTNAME + " TEXT," +
                KEY_CITY + " TEXT )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // metoda wywoływana kiedy wymagana jest aktualizacja struktury bazy danych
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(con.getApplicationContext(),
                "Aktualizacja bazy danych z wersji " + oldVersion + " do "+ newVersion,
                Toast.LENGTH_LONG).show();
    }

    // metoda wywoływana kiedy wymangana jest przywrucenie w strukturze bazy danych do wcześniejszej
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Toast.makeText(con.getApplicationContext(),
                "Aktualizacja bazy danych z wersji " + oldVersion + " do "+ newVersion,
                Toast.LENGTH_LONG).show();
    }

    // metoda wywoływana kiedy chcemy wstawić rekord do bazy danych
    public void insertPerson (String name, String lastName, String city) {
        // otwarcie bazy danych do zapisu
        SQLiteDatabase db = this.getWritableDatabase();
        // obiekt obiekt przechowywujący pary klucz - wartość
        ContentValues contentValues = new ContentValues();
        // wstawianie par klucz - wartosć do obiektu
        contentValues.put("name", name);
        contentValues.put("last_name", lastName);
        contentValues.put("city", city);
        // metoda wstawiająca rekord do bazy danych przyjmuje następujące parametry (Nazwa tabeli, null, wstawiane wartości)
        db.insert(TABLE_CONTACT, null, contentValues);
        // zamykanie bazy danych
        db.close();
    }

    public Person getPerson(int id) {
        // otwarcie bazy danych do oczytu
        SQLiteDatabase db = this.getReadableDatabase();
        // zapytanie wyszukujące konkretny rekord i zapisanie do obiektu Cursor
        Cursor cursor = db.query(TABLE_CONTACT, // nazwa tabeli
                    new String[] { KEY_ID, KEY_NAME, KEY_LASTNAME, KEY_CITY },  // kolumny które chcemy wyświetlić
                    KEY_ID + "=?", // warunek po którym wybieramy konkretny rekord
                    new String[] { String.valueOf(id) }, // wartość warunku po którym szukamy
                    null,   // grupowanie rekordu argument groupBy
                    null,   // grupowanie rekordu argument having
                    null);  // uporządkowanie argumentów argument orderBy
        // zapytanie tradycyjne wybierajace konkretny rekord z bazy danych
        //Cursor cursor = db.rawQuery("select * from person where id ='"+id+"'", null);
        // kolejna wersja zapytanie tradycyjne wybierajace konkretny rekord z bazy danych
        //Cursor cursor = db.rawQuery("select * from person where id = ? ",
        //        new String[]{Integer.toString(id)});
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Person dbPerson = new Person(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return dbPerson;
    }

    // pobranie wszystkich osób z bazy danych
    public List<String> getAllPerson() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> pList = new ArrayList<>();
        // pobieranie wszystkich rekordów z bazy danych tradycyjnym zapytaniem i zapisanie ich do obiektu Cursor
        Cursor cursor = db.rawQuery("select * from person", null);
        // pobieranie wszystkich rekordów z bazy danych i zapisanie ich do obiektu Cursor
        //Cursor cursor = db.query(TABLE_CONTACT,
        //            new String[] { KEY_ID, KEY_NAME, KEY_LASTNAME, KEY_CITY },
        //            null, null, null, null,
        //            null);
        // sprawdzanie czy zostały pobrane rekordy


        if(cursor.moveToLast()) {
            do {
                pList.add(cursor.getString(0) + " " + cursor.getString(1) + " " +
                        cursor.getString(2) + " " + cursor.getString(3));
            } while (cursor.moveToPrevious());  // poruszanie się po obiekcie Cursor
        }

        // zamykanie obiektu Cursor i bazy danych
        cursor.close();
        db.close();
        return pList;
    }

    // Pobranie liczby rekordów z bazy danych
    public int getPersonCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACT,new String[]{"COUNT(" + KEY_ID + ") AS count"}, null,null,null,null,null);

        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        return 0;
    }

    // Aktualizowanie osoby w bazie danych
    public void updatePerson(Person contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.name);
        values.put("last_name", contact.lastName);
        values.put("city", contact.city);
        db.update(TABLE_CONTACT, values, KEY_ID+"=?", new String[]{Integer.toString(contact.id)});
        db.close();
    }

    // Usunięcie osoby z bazy danych
    public void deletePerson(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT, KEY_ID +"=?", new String[]{Integer.toString(id)});
        db.close();
    }
}
