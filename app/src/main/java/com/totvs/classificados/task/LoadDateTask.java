package com.totvs.classificados.task;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.totvs.classificados.App;
import com.totvs.classificados.R;
import com.totvs.classificados.adapter.ItemAdapter;
import com.totvs.classificados.database.DBHelper;
import com.totvs.classificados.database.MyStore;
import com.totvs.classificados.database.model.AdItem;

import java.util.List;

/**
 * Created by Totvs on 24/01/2017.
 */

public class LoadDateTask extends AsyncTask<Void, Integer, Boolean> {

    private Activity mActivity;
    private List<AdItem> mItems;
    private ItemAdapter mAdapter;
    private View mContainerProgress;
    private TextView mTvProgress;
    private RecyclerView mList;

    public LoadDateTask(Activity activity, List<AdItem> mItems, ItemAdapter mAdapter,
                        View mContainerProgress, TextView mTvProgress, RecyclerView mList) {
        this.mItems = mItems;
        this.mAdapter = mAdapter;
        this.mContainerProgress = mContainerProgress;
        this.mTvProgress = mTvProgress;
        this.mList = mList;
        this.mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mList.setVisibility(View.INVISIBLE);
        mContainerProgress.setVisibility(View.VISIBLE);

        mTvProgress.setText(mContainerProgress.getContext().getString(R.string.preparing_data));
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        int qtdItems = 50;
        sleep(2000);

        DBHelper dbHelper = App.getApp(mActivity).getDbHelper();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor c =
                     db.query(MyStore.AdItemTable.TABLE_NAME, null, null, null, null, null, null)) {

            int total = c.getCount();
            int i = 0;
            //c.moveToFirst() to use just one
            while (c.moveToNext()) {
                i++;

                sleep(100);
                AdItem item = new AdItem(c);
                mItems.add(item);

                int progress = i * 100 / total;
                publishProgress(progress);
            }
        }
        return null;
    }

    private void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        mTvProgress.setText(mContainerProgress.getContext().getString(R.string.progress, values[0]));

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        mAdapter.notifyDataSetChanged();
        mContainerProgress.setVisibility(View.INVISIBLE);
        mList.setVisibility(View.VISIBLE);

        Animation animOut = AnimationUtils.loadAnimation(mContainerProgress.getContext(), android.R.anim.slide_out_right);
        Animation animIn = AnimationUtils.loadAnimation(mContainerProgress.getContext(), android.R.anim.fade_in);

        mContainerProgress.startAnimation(animOut);
        mList.startAnimation(animIn);
    }
}
