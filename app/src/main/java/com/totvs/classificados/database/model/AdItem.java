package com.totvs.classificados.database.model;

import android.database.Cursor;

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
}
