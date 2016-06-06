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
    public static final int NEW_TASK_RESULT_CODE = 1;
    private TextView txtNazwa, txtOpis;
    private Button btnDodaj, btnMapa;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDodaj:
                // Pobierz dane z formularza i zwroc je do glownej aktywnosci
                Intent intentDodaj = getIntent();
                intentDodaj.putExtra("nazwa", txtNazwa.getText().toString());
                intentDodaj.putExtra("opis", (String) txtOpis.getText().toString());
                setResult(NEW_TASK_RESULT_CODE, intentDodaj);
                finish();
                break;
            case R.id.buttonMapa:
                // Search for restaurants in San Francisco
                Intent intentMapa = new Intent(this, MapaActivity.class);
                startActivity(intentMapa);
                break;
        }
    }
}
