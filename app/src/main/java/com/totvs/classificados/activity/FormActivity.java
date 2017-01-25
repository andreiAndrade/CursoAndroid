package com.totvs.classificados.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.totvs.classificados.App;
import com.totvs.classificados.R;
import com.totvs.classificados.database.DBHelper;
import com.totvs.classificados.database.MyStore;
import com.totvs.classificados.database.model.AdItem;

import java.util.UUID;

/**
 * Created by Totvs on 25/01/2017.
 */

public class FormActivity extends BaseActivity {
    public static final String ITEM_GUID = "ITEM_GUID";

    private EditText mEtTitle;
    private EditText mEtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form);
        createToolbar("");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtDescription = (EditText) findViewById(R.id.et_description);


    }

    public void save(View view) {

        String title = mEtTitle.getText().toString();
        String description = mEtDescription.getText().toString();

        ContentValues values = new ContentValues();
        values.put(MyStore.AdItemTable.GUID, UUID.randomUUID().toString());
        values.put(MyStore.AdItemTable.TITLE, title);
        values.put(MyStore.AdItemTable.DESCRIPTION, description);

        DBHelper dbHelper = App.getApp(this).getDbHelper();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(MyStore.AdItemTable.TABLE_NAME, null, values);

        startActivity(new Intent(this, this.getClass()));
        finish();
    }
}
