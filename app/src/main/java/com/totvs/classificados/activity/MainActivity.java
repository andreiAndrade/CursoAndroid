package com.totvs.classificados.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.totvs.classificados.App;
import com.totvs.classificados.R;
import com.totvs.classificados.adapter.ItemAdapter;
import com.totvs.classificados.model.AdItem;
import com.totvs.classificados.model.Category;
import com.totvs.classificados.service.ToastService;
import com.totvs.classificados.view.LinearRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Totvs on 19/12/2016.
 */

public class MainActivity extends BaseActivity {

    public static final int REQUEST_CHOSEN_FILTER_CODE = 1;
    private LinearRecyclerView mRvList;
    public static final int REQUEST_CALL_PERMISSION = 0;
    public static final int REQUEST_SMS_PERMISSION = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void chosenFilter(View v) {
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
                makeCall();
                break;
            case R.id.item_request_sms_permission:
                requestSMSPermission();
                break;
            case R.id.item_start_service:
                startToastService();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void startToastService() {
        Intent intentService = new Intent(this, ToastService.class);
        intentService.putExtra(ToastService.MSG_KEY, "Jamal God");
        startService(intentService);
    }

    private void requestSMSPermission() {
        if (nonPermissionGranted(Manifest.permission.READ_SMS)
                || nonPermissionGranted(Manifest.permission.RECEIVE_SMS)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                Toast.makeText(this, R.string.enable_permission_on_settings, Toast.LENGTH_LONG).show();
            } else {
                String[] permissions = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_SMS_PERMISSION);
            }
        }
    }

    private boolean nonPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED;
    }

    private void makeCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:40042002"));
            startActivity(callIntent);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            //TODO use snackbar to put link to config.
            Toast.makeText(this, R.string.enable_permission_on_settings, Toast.LENGTH_LONG).show();
        } else {
            String[] permissions = new String[]{Manifest.permission.CALL_PHONE};

            Collections.singletonList(Manifest.permission.CALL_PHONE);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CALL_PERMISSION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_CALL_PERMISSION:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        makeCall();
                    }
                    break;
                case REQUEST_SMS_PERMISSION:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, R.string.sms_enabled, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
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

        if (requestCode == REQUEST_CHOSEN_FILTER_CODE) {
            Category category = (Category) data.getSerializableExtra("CATEGORY_KEY");
            Snackbar.make(mRvList, category.toString(), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
