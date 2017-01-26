package com.totvs.classificados.database.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.totvs.classificados.App;
import com.totvs.classificados.activity.FormActivity;
import com.totvs.classificados.database.DBHelper;
import com.totvs.classificados.database.MyStore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Totvs on 20/12/2016.
 */

public class AdItem implements Serializable{

    private String mUrl;
    private String mTitle;
    private String mDetail;
    private String mImage;
    private BigDecimal mPrice;

    private String mGuid;

    public AdItem(String url, String title, String detail) {
        this.mUrl = url;
        this.mTitle = title;
        this.mDetail = detail;

    }

    public AdItem(Cursor cursor) {
        this.mGuid = cursor.getString(cursor.getColumnIndex(MyStore.AdItemTable.GUID));
        this.mTitle = cursor.getString(cursor.getColumnIndex(MyStore.AdItemTable.TITLE));
        this.mDetail = cursor.getString(cursor.getColumnIndex(MyStore.AdItemTable.DESCRIPTION));
        this.mImage = cursor.getString(cursor.getColumnIndex(MyStore.AdItemTable.IMAGE));

        String price = cursor.getString(cursor.getColumnIndex(MyStore.AdItemTable.PRICE));
        mPrice = price != null ? new BigDecimal(price) : BigDecimal.ZERO;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public String getGuid() {
        return mGuid;
    }

    public BigDecimal getPrice() {
        return mPrice;
    }

    public void setPrice(BigDecimal mPrice) {
        this.mPrice = mPrice;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public static AdItem getByGuid(FormActivity formActivity, String itemGuid) {
        DBHelper db = App.getApp(formActivity).getDbHelper();
        SQLiteDatabase readableDatabase = db.getReadableDatabase();

        try (Cursor cursor = readableDatabase.query(MyStore.AdItemTable.TABLE_NAME, null,
                MyStore.AdItemTable.GUID + "= ?", new String[]{itemGuid}, null, null, null, "1")) {
            if (cursor.moveToFirst()) {
                return new AdItem(cursor);
            }
        }

        return null;
    }
}
