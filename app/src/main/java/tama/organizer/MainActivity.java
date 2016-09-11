package tama.organizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import tama.organizer.Baza.BazaZadan;
import tama.organizer.Mapy.MapaActivity;
import tama.organizer.Zadania.NoweZadanieActivity;
import tama.organizer.Zadania.Zadanie;
import tama.organizer.Zadania.ZadanieAdapter;

public class MainActivity extends AppCompatActivity{
    private static ArrayList<Zadanie> listaZadan;
    private ListView zadanieView;
    private BazaZadan bazaZadan;
    private ZadanieAdapter zadAdp;

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
        registerForContextMenu(zadanieView);
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
                // Po kilkneciu na dodanie nowego zadania wyswietla formularz
                Intent intent = new Intent(this, NoweZadanieActivity.class);
                startActivityForResult(intent, NoweZadanieActivity.NEW_TASK_REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        // Pobiera dane z formularza i dodaje zadanie do bazy, a nastepnie odswieza liste zadan
        if (resultCode == NoweZadanieActivity.NEW_TASK_RESULT_CODE) {
            String nazwa = data.getStringExtra("nazwa");
            String opis = data.getStringExtra("opis");
            String szerokosc = data.getStringExtra("latitude");
            String dlugosc = data.getStringExtra("longitude");
            if (requestCode == NoweZadanieActivity.NEW_TASK_REQUEST_CODE) {
                Zadanie zad = bazaZadan.dodajZadanie(nazwa, opis, szerokosc, dlugosc);
                zadAdp.add(zad);
            }
            if(requestCode==NoweZadanieActivity.EDIT_TASK_REQUEST_CODE){
                int id = Integer.parseInt(data.getStringExtra("id"));
                bazaZadan.usunZadanie(listaZadan.get(id));
                Zadanie zad = bazaZadan.dodajZadanie(nazwa, opis, szerokosc, dlugosc);
                zadAdp.edit(id, zad);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.listView){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            //menu.setHeaderTitle();
            menu.add(Menu.NONE, 1, 1, "Zobacz szczegóły");
            menu.add(Menu.NONE, 2, 2, "Edytuj");
            menu.add(Menu.NONE, 3, 3, "Usuń zadanie");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case 1:
                Intent mapa = new Intent(this, MapaActivity.class);
                String lat=listaZadan.get(info.position).getSzerokosc();
                String lng= listaZadan.get(info.position).getDlugosc();
                if(lat==null ||lng==null) {
                    lat = "0";
                    lng = "0";
                }
                mapa.putExtra("latitude", lat);
                mapa.putExtra("longitude", lng);
                mapa.putExtra("title", listaZadan.get(info.position).getNazwa());
                mapa.putExtra("description", listaZadan.get(info.position).getOpis());
                mapa.putExtra("requestCode", MapaActivity.MAP_LOCATION_DISPLAY_CODE);
                startActivity(mapa);
                break;
            case 2:
                Intent intEdit = new Intent(this, NoweZadanieActivity.class);
                intEdit.putExtra("latitude", listaZadan.get(info.position).getSzerokosc());
                intEdit.putExtra("longitude", listaZadan.get(info.position).getDlugosc());
                intEdit.putExtra("title", listaZadan.get(info.position).getNazwa());
                intEdit.putExtra("description", listaZadan.get(info.position).getOpis());
                intEdit.putExtra("id", Integer.toString(info.position));
                intEdit.putExtra("requestCode", NoweZadanieActivity.EDIT_TASK_REQUEST_CODE);
                startActivityForResult(intEdit, NoweZadanieActivity.EDIT_TASK_REQUEST_CODE);
                break;
            case 3:
                bazaZadan.usunZadanie(listaZadan.get(info.position));
                zadAdp.delete(info.position);
                break;
        }
        return true;
    }
}
