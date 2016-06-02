package tama.organizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DodajZadanie extends AppCompatActivity implements View.OnClickListener{
    public static final int NEW_TASK_RESULT_CODE = 1;
    private TextView txtNazwa, txtOpis;
    private Button btnDodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_zadanie);

        txtNazwa = (TextView) findViewById(R.id.nazwaZadania);
        txtOpis = (TextView) findViewById(R.id.opisZadania);
        btnDodaj = (Button) findViewById(R.id.buttonDodaj);
        btnDodaj.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Pobierz dane z formularza i zwroc je do glownej aktywnosci
        Intent intent = getIntent();
        intent.putExtra("nazwa", txtNazwa.getText().toString());
        intent.putExtra("opis", (String)txtOpis.getText().toString());
        setResult(NEW_TASK_RESULT_CODE, intent);
        finish();
    }
}
