package tama.organizer.Zadania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import tama.organizer.Mapy.MapaActivity;
import tama.organizer.R;

public class NoweZadanieActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int NEW_TASK_REQUEST_CODE = 1;
    public static final int EDIT_TASK_REQUEST_CODE =2;
    public static final int NEW_TASK_RESULT_CODE = 11;
    public static final int EDIT_TASK_RESULT_CODE = 12;
    private TextView txtNazwa, txtOpis;
    private Button btnDodaj, btnMapa;
    private String latitude, longitude, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_zadanie);

        txtNazwa = (TextView) findViewById(R.id.nazwaZadania);
        txtOpis = (TextView) findViewById(R.id.opisZadania);
        btnDodaj = (Button) findViewById(R.id.buttonDodaj);
        btnMapa = (Button) findViewById(R.id.buttonMapa);
        btnDodaj.setOnClickListener(this);
        btnMapa.setOnClickListener(this);
        if (getIntent().getIntExtra("requestCode", 1) == EDIT_TASK_REQUEST_CODE){
            txtNazwa.setText(getIntent().getStringExtra("title"));
            txtOpis.setText(getIntent().getStringExtra("description"));
            latitude = getIntent().getStringExtra("latitude");
            longitude = getIntent().getStringExtra("longitude");
            id = getIntent().getStringExtra("id");
        }
        else {
            latitude = null;
            longitude = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDodaj:
                // Pobierz dane z formularza i zwroc je do glownej aktywnosci
                Intent intentDodaj = getIntent();
                intentDodaj.putExtra("nazwa", txtNazwa.getText().toString());
                intentDodaj.putExtra("opis", txtOpis.getText().toString());
                intentDodaj.putExtra("latitude", latitude);
                intentDodaj.putExtra("longitude", longitude);
                if(getIntent().getIntExtra("requestCode", 1) == EDIT_TASK_REQUEST_CODE)
                    intentDodaj.putExtra("id", id);
                setResult(NEW_TASK_RESULT_CODE, intentDodaj);
                finish();
                break;
            case R.id.buttonMapa:
                Intent intentMapa = new Intent(this, MapaActivity.class);
                intentMapa.putExtra("latitude", latitude);
                intentMapa.putExtra("longitude", longitude);
                intentMapa.putExtra("requestCode", MapaActivity.MAP_LOCATION_REQUEST_CODE);
                startActivityForResult(intentMapa, MapaActivity.MAP_LOCATION_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MapaActivity.MAP_LOCATION_REQUEST_CODE){
            if(resultCode == MapaActivity.MAP_LOCATION_RESULT_CODE){
                latitude = data.getStringExtra("latitude");
                longitude = data.getStringExtra("longitude");
            }
        }
    }
}
