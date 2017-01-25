package com.totvs.classificados.database;

/**
 * Created by Totvs on 25/01/2017.
 */

public abstract class MyStore {

    public abstract class BaseTable {

        public static final String GUID = "guid";

    }

    public abstract class AdItemTable extends BaseTable {

        public static final String TABLE_NAME = "ad_item";

        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";


        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + GUID + " TEXT PRIMARY KEY,"
                        + TITLE + " TEXT,"
                        + DESCRIPTION + " TEXT,"
                        + PRICE + " TEXT"
                        + ")";
    }

    //OTHERS TABLES HERE

}
