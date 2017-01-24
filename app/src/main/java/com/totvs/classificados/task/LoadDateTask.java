package com.totvs.classificados.task;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.totvs.classificados.R;
import com.totvs.classificados.adapter.ItemAdapter;
import com.totvs.classificados.model.AdItem;

import java.util.List;

/**
 * Created by Totvs on 24/01/2017.
 */

public class LoadDateTask extends AsyncTask<Void, Integer, Boolean> {

    private List<AdItem> mItems;
    private ItemAdapter mAdapter;
    private View mContainerProgress;
    private TextView mTvProgress;
    private RecyclerView mList;

    public LoadDateTask(List<AdItem> mItems, ItemAdapter mAdapter, View mContainerProgress, TextView mTvProgress, RecyclerView mList) {
        this.mItems = mItems;
        this.mAdapter = mAdapter;
        this.mContainerProgress = mContainerProgress;
        this.mTvProgress = mTvProgress;
        this.mList = mList;
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
        for (int i = 0; i < qtdItems; i++) {
            sleep(100);

            AdItem item = new AdItem(null, String.valueOf(i), String.format("Item description %s", i));
            mItems.add(item);

            int progress = i * 100 / qtdItems;
            publishProgress(progress);
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
