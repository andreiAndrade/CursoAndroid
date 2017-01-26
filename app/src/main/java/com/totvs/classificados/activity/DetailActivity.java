package com.totvs.classificados.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.totvs.classificados.App;
import com.totvs.classificados.R;
import com.totvs.classificados.database.DBHelper;
import com.totvs.classificados.database.MyStore;
import com.totvs.classificados.database.model.AdItem;

/**
 * Created by Totvs on 20/12/2016.
 */

public class DetailActivity extends BaseActivity {

    private TextView mTvTitle;
    private TextView mTvDetail;
    private ImageView mIvImage;
    private AdItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvDetail = (TextView) findViewById(R.id.tv_detail);
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        createToolbar("Activity Detail");


        Intent intent = getIntent();
        if (intent != null) {
            item = (AdItem) intent.getSerializableExtra("AD_KEY");

            mTvTitle.setText(item.getTitle());
            mTvDetail.setText(item.getDetail());

            if (item.getImage() != null && !item.getImage().isEmpty()) {
                mIvImage.setImageBitmap(BitmapFactory.decodeFile(item.getImage()));
            } else {
                mIvImage.setImageResource(R.mipmap.ic_launcher);
            }

            this.toolbar.setTitle(item.getTitle());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, FormActivity.class);
                intent.putExtra(FormActivity.ITEM_GUID, this.item.getGuid());
                startActivity(intent);
                break;
            case R.id.action_delete:
                DBHelper dbHelper = App.getApp(this).getDbHelper();
                SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();

                writableDatabase.delete(MyStore.AdItemTable.TABLE_NAME,
                        MyStore.AdItemTable.GUID + " = ?", new String[]{this.item.getGuid()});

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
