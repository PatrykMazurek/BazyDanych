package com.example.patryyyk21.bazydanych;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        dBHelper.insertPerson("Monika", "Rak", "Gdańsk");
        dBHelper.insertPerson("Aretur", "Jankowski", "Kielce");
        dBHelper.insertPerson("Marcelina", "Nowak", "Warszawa");
        // pobanie wybranego rekordu z bazy danych
        //Person p = dBHelper.getPerson(2);

        //dBHelper.deletePerson(10);

        //dBHelper.updatePerson(new Person(7, "Roman", "Nowak", "Warszawa"));
        // pobieranie wszystkich rekordów z bazy danych i wyświetlenie ich w ListView
        List<String> listPerson = dBHelper.getAllPerson();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listPerson);
        list.setAdapter(adapter);

        tvpol.setText("Liczba rekordów " + dBHelper.getPersonCount());

    }
}
