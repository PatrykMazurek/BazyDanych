package com.example.patryyyk21.bazydanych;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView)findViewById(R.id.listPerson);
        TextView tvpol = (TextView)findViewById(R.id.tvpol);

        // utworzenie nowergo pomocnika baz danych
        DBHelper dBHelper = new DBHelper(this);
        // wstawienie rekordu do bazy danych
        dBHelper.insertPerson("Damian", "Kowalski", "Poznań");
        // pobanie wybranego rekordu z bazy danych
        Person p = dBHelper.getPerson(2);

        // pobieranie wszystkich rekordów z bazy danych i wyświetlenie ich w ListView
        List<String> listPerson = dBHelper.getAllPerson();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listPerson);
        list.setAdapter(adapter);
    }
}
