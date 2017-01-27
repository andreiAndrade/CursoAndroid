package com.totvs.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.totvs.classificados.R;
import com.totvs.classificados.database.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Totvs on 21/12/2016.
 */

public class FilterActivity extends BaseActivity {

    private Spinner mSnCategory;
    private List<Category> mCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mSnCategory = (Spinner) findViewById(R.id.sn_category);

        mCategories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mCategories.add(new Category(String.format("GUID:%s", i), String.format("Categoria %s", i)));
        }

        mSnCategory.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mCategories));

        String guidCategory = getPrefs().getString("CATEGORY_KEY", null);
        if (guidCategory != null) {
            for (int i = 0; i < mCategories.size(); i++) {
                if (mCategories.get(i).getGuid().equals(guidCategory)) {
                    mSnCategory.setSelection(i);
                    break;
                }
            }
        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                LatLng latLng = new LatLng(-30.060373, -51.173824);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng).title("Porto Alegre");
                googleMap.addMarker(markerOptions);

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LatLng latLng = new LatLng(-28.126280, -48.641599);
                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(latLng).title("Praia do Rosa");
                                googleMap.addMarker(markerOptions);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();

        Category category = (Category) mSnCategory.getSelectedItem();
        intent.putExtra("CATEGORY_KEY", category);
        setResult(RESULT_OK, intent);

        getPrefs().edit().putString("CATEGORY_KEY", category.getGuid()).apply();

        super.onBackPressed();
    }


}
