package com.totvs.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.totvs.classificados.R;

/**
 * Created by Totvs on 20/12/2016.
 */

public class DetailActivity extends BaseActivity {

    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        Intent intent = getIntent();
        if (intent != null) {
            mTvTitle.setText(intent.getStringExtra("TITLE_KEY"));
        }
    }
}
