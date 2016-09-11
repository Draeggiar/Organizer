package tama.organizer.Mapy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import tama.organizer.R;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener{
    public static final int MAP_LOCATION_REQUEST_CODE = 100;
    public static final int MAP_LOCATION_DISPLAY_CODE = 101;
    public static final int MAP_LOCATION_RESULT_CODE = 102;
    private GoogleMap map;
    private Button btnPotwierdz;
    private double latitude, longitude;
    private Marker location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        btnPotwierdz = (Button) findViewById(R.id.mButton);

        map = mapFragment.getMap();
        map.getUiSettings().setZoomControlsEnabled(true);
        btnPotwierdz.setOnClickListener(this);
        try {
            latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
            longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
        }catch (Exception e){
            latitude=0;
            longitude=0;
        }
        if (latitude!=0 && longitude!=0)
            location = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));

        if(getIntent().getIntExtra("requestCode", 101)==101){
            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");

            TextView txtTytul = (TextView) findViewById(R.id.txtTytul);
            txtTytul.setText(title);
            txtTytul.setVisibility(View.VISIBLE);
            TextView txtOpis = (TextView) findViewById(R.id.txtOpis);
            txtOpis.setText(description);
            txtOpis.setVisibility(View.VISIBLE);
        }
        else
            map.setOnMapClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {}

    @Override
    public void onMapClick(LatLng latLng) {
        if(location!=null)
            location.setPosition(latLng);
        else
            location = map.addMarker(new MarkerOptions().position(latLng).title("Wybrana lokacja"));
        latitude = location.getPosition().latitude;
        longitude = location.getPosition().longitude;
    }

    @Override
    public void onClick(View v) {
        Intent cords = getIntent();
        cords.putExtra("latitude", Double.toString(latitude));
        cords.putExtra("longitude", Double.toString(longitude));
        setResult(MAP_LOCATION_RESULT_CODE, cords);
        finish();
    }
}
