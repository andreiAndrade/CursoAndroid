package com.totvs.classificados.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.totvs.classificados.App;
import com.totvs.classificados.R;
import com.totvs.classificados.adapter.ItemAdapter;
import com.totvs.classificados.model.AdItem;
import com.totvs.classificados.model.Category;
import com.totvs.classificados.view.LinearRecyclerView;

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

        createToolbar("Main Activity");

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRvList = (LinearRecyclerView) findViewById(R.id.rv_list);

        List<AdItem> items = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            AdItem item = new AdItem(null, String.valueOf(i), String.format("Item description %s", i));
            items.add(item);
        }

        ItemAdapter itemAdapter = new ItemAdapter(items, this);
        mRvList.setAdapter(itemAdapter);

        App.getApp(this).setCurrentTime(0L);

        Spinner category = (Spinner) findViewById(R.id.sn_category);

        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(new Category(String.format("GUID:%s", i), String.format("Categoria %s", i)));
        }

        category.setAdapter(
                new ArrayAdapter<>(getSupportActionBar().getThemedContext(), android.R.layout.simple_spinner_dropdown_item, categories));
    }

    public void chosenFilter(View v){
        Log.d(TAG, "Chosen Filter");
        Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, REQUEST_CHOSEN_FILTER_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_toast:
                Toast.makeText(this, "Item 1", Toast.LENGTH_LONG).show();
                break;
            case R.id.item_snackbar:
                Snackbar snackbar = Snackbar.make(toolbar, "Item 2", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Continuar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.title_dialog)
                                .setMessage(R.string.message_dialog)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                }).show();
                break;
            case R.id.item_notification:
                NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.title_notification))
                        .setContentText(getString(R.string.message_notification))
                        .setAutoCancel(true);

                Intent intent = new Intent(this, FilterActivity.class);
                PendingIntent pendingIntent = PendingIntent
                        .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                notification.setContentIntent(pendingIntent);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                manager.notify(0, notification.build());
                break;
            case R.id.item_browser:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://fb.com"));
                startActivity(browserIntent);

                break;
            case R.id.item_call:
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:40042002"));
                startActivity(callIntent);

                break;

        }
        return super.onOptionsItemSelected(item);
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
