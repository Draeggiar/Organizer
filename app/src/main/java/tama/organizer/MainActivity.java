package tama.organizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ArrayList<Zadanie> listaZadan;
    private ListView zadanieView;
    private BazaZadan bazaZadan;
    private ZadanieAdapter zadAdp;
    private final int NEW_TASK_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pobiera zadania z bazy
        bazaZadan = new BazaZadan(this);
        bazaZadan.open();
        listaZadan = bazaZadan.zwrocZadania();

        // Wyswietla liste zadan w aktywnosci
        zadanieView = (ListView) findViewById(R.id.listView);
        zadAdp = new ZadanieAdapter(this, listaZadan);
        zadanieView.setAdapter(zadAdp);
    }

    @Override
    protected void onDestroy(){
        bazaZadan.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_dodaj:
                // Po kilkneciu na dodanie noweog zadania wyswietla formularz
                Intent intent = new Intent(this, DodajZadanie.class);
                startActivityForResult(intent, NEW_TASK_REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Pobiera dane z formularza i dodaje zadanie do bazy, a nastepnie odswieza liste zadan
        String nazwa, opis;
        if (requestCode == NEW_TASK_REQUEST_CODE){
            //
            if (resultCode == DodajZadanie.NEW_TASK_RESULT_CODE){
                nazwa = data.getStringExtra("nazwa");
                opis = data.getStringExtra("opis");

                Zadanie zad = bazaZadan.dodajZadanie(nazwa, opis);
                zadAdp.add(zad);
            }
        }
    }
}
