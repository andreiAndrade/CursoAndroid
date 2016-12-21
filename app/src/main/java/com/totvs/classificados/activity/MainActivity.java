package com.totvs.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.totvs.classificados.R;
import com.totvs.classificados.adapter.ItemAdapter;
import com.totvs.classificados.model.AdItem;
import com.totvs.classificados.model.Category;
import com.totvs.classificados.view.LinearRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Totvs on 19/12/2016.
 */

public class MainActivity extends BaseActivity {

    public static final int REQUEST_CHOSEN_FILTER_CODE = 1;
    private LinearRecyclerView mRvList;
    //private String mVar; //nomenclatura para privados
    //public String var; //nomenclatura para publico
    //public static String sVar; //nomenclatura para static
    //public final String VAR = ""; //nomenclatura para final

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRvList = (LinearRecyclerView) findViewById(R.id.rv_list);

        List<AdItem> items = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            AdItem item = new AdItem(null, String.valueOf(i), String.format("Item description %s", i));
            items.add(item);
        }

        ItemAdapter itemAdapter = new ItemAdapter(items, this);
        mRvList.setAdapter(itemAdapter);
    }

    public void chosenFilter(View v){
        Log.d(TAG, "Chosen Filter");
        Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, REQUEST_CHOSEN_FILTER_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("NAME", "Batata");
        outState.putInt("AGE", 40);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String name = savedInstanceState.getString("NAME");
        int age = savedInstanceState.getInt("AGE");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHOSEN_FILTER_CODE) {
            Category category = (Category) data.getSerializableExtra("CATEGORY_KEY");
            Snackbar.make(mRvList, category.toString(), Snackbar.LENGTH_LONG).show();
        }
    }
}
