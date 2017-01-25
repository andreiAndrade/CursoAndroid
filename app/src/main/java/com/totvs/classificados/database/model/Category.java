package com.totvs.classificados.database.model;

import java.io.Serializable;

/**
 * Created by Totvs on 21/12/2016.
 */

public class Category implements Serializable {
    private String mGuid;
    private String mName;

    public Category(String guid, String name) {
        this.mGuid = guid;
        this.mName = name;
    }

    public String getGuid() {
        return mGuid;
    }

    public void setGuid(String guid) {
        this.mGuid = guid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public String toString() {
        return this.mName;
    }
}
