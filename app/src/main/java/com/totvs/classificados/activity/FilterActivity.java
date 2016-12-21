package com.totvs.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.totvs.classificados.R;
import com.totvs.classificados.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
