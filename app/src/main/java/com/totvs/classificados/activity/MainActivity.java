package com.totvs.classificados.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.totvs.classificados.adapter.ItemAdapter;
import com.totvs.classificados.model.AdItem;
import com.totvs.classificados.view.LinearRecyclerView;
import com.totvs.classificados.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Totvs on 19/12/2016.
 */

public class MainActivity extends BaseActivity {

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

    public void loadMore(View view) {
        Log.d(TAG, "LoadMore");
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
}
