package com.totvs.classificados.model;

import java.io.Serializable;

/**
 * Created by Totvs on 20/12/2016.
 */

public class AdItem implements Serializable{

    private String mUrl;
    private String mTitle;
    private String mDetail;

    public AdItem(String url, String title, String detail) {
        this.mUrl = mUrl;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
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
}
