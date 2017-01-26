package com.totvs.classificados.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.totvs.classificados.App;
import com.totvs.classificados.R;
import com.totvs.classificados.database.DBHelper;
import com.totvs.classificados.database.MyStore;
import com.totvs.classificados.database.model.AdItem;
import com.totvs.classificados.fragment.ListFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Totvs on 25/01/2017.
 */

public class FormActivity extends BaseActivity {
    public static final String ITEM_GUID = "ITEM_GUID";
    private static final int IMAGE_REQUEST = 0;

    private ImageView mImageView;
    private EditText mEtTitle;
    private EditText mEtDescription;

    private String mImagePath;

    private AdItem mItem;

    private boolean mIsLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form);
        createToolbar("");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtDescription = (EditText) findViewById(R.id.et_description);
        mImageView = (ImageView) findViewById(R.id.iv_image);

        Intent intent = getIntent();
        if (intent != null) {
            mIsLocal = intent.getBooleanExtra(ListFragment.IS_LOCAL, true);

            String itemGuid = intent.getStringExtra(ITEM_GUID);

            if (itemGuid != null) {
                mItem = AdItem.getByGuid(this, itemGuid);
            }
        }

        if (mItem != null) {
            getSupportActionBar().setTitle(mItem.getTitle());

            mEtTitle.setText("");
            mEtTitle.append(mItem.getTitle());
            mEtDescription.setText(mItem.getDetail());
        } else {
            getSupportActionBar().setTitle(R.string.new_item);
        }

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intentCapture.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intentCapture, IMAGE_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bitmap btmImage = (Bitmap) data.getExtras().get("data");

            if (btmImage != null) {
                String fileName = UUID.randomUUID().toString() + ".jpg";
                File file = new File(getFilesDir().getAbsolutePath(), fileName);

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    btmImage.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mImagePath = file.getAbsolutePath();
                Bitmap newBtmImage = BitmapFactory.decodeFile(mImagePath);
                mImageView.setImageBitmap(newBtmImage);
            }
        }
    }

    public void save(View view) {

        String title = mEtTitle.getText().toString();
        String description = mEtDescription.getText().toString();

        if(mIsLocal) {
            ContentValues values = new ContentValues();
            values.put(MyStore.AdItemTable.TITLE, title);
            values.put(MyStore.AdItemTable.DESCRIPTION, description);
            values.put(MyStore.AdItemTable.IMAGE, mImagePath);

            DBHelper dbHelper = App.getApp(this).getDbHelper();
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            if (mItem == null) {
                values.put(MyStore.AdItemTable.GUID, UUID.randomUUID().toString());
                db.insert(MyStore.AdItemTable.TABLE_NAME, null, values);
                startActivity(new Intent(this, this.getClass()));
            } else {
                db.update(MyStore.AdItemTable.TABLE_NAME, values,
                        MyStore.AdItemTable.GUID + " = ?", new String[]{mItem.getGuid()});
            }

            finish();
        } else {
            saveServerData(title, description);
        }
    }

    private void saveServerData(String title, String description) {
        ProgressDialog progress = ProgressDialog.show(this, null, getString(R.string.saving));
        progress.setCancelable(true);
    }
}
