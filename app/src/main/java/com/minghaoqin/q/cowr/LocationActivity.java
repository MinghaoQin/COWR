package com.minghaoqin.q.cowr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("info", "Place: " + place.getName());
                Preference p = Preference.getInstance();
                LatLng ll = place.getLatLng();
                p.writePreferenceFloat("Latitude", (float)ll.latitude);
                p.writePreferenceFloat("Longitude", (float)ll.longitude);
                p.writePreference("Address", place.getName().toString());
                finish();
            }

            @Override
            public void onError(Status status) {

                Log.i("info", "An error occurred: " + status);
            }
        });
    }
}
