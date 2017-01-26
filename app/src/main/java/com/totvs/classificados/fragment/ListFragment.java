package com.totvs.classificados.fragment;

import android.animation.FloatArrayEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.totvs.classificados.R;
import com.totvs.classificados.activity.FormActivity;
import com.totvs.classificados.adapter.ItemAdapter;
import com.totvs.classificados.database.model.AdItem;
import com.totvs.classificados.task.LoadDateTask;
import com.totvs.classificados.view.LinearRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Totvs on 26/01/2017.
 */

public class ListFragment extends Fragment {

    public static final String IS_LOCAL = "IS_LOCAL";

    private LinearRecyclerView mRvList;
    private View mContainerProgress;
    private TextView mTvProgress;
    private List<AdItem> mItems;
    private ItemAdapter mItemAdapter;

    private boolean mIsLocal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mIsLocal = getArguments().getBoolean(IS_LOCAL, true);

        mRvList = (LinearRecyclerView) view.findViewById(R.id.rv_list);
        mContainerProgress = view.findViewById(R.id.container_progress);
        mTvProgress = (TextView) view.findViewById(R.id.tv_progress);

        mItems = new ArrayList<>();
        mItemAdapter = new ItemAdapter(mItems, getActivity());
        mRvList.setAdapter(mItemAdapter);

        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();

    }

    private void loadData() {
        mItems.clear();

        if(mIsLocal) {
            LoadDateTask task =
                    new LoadDateTask(getActivity(), mItems, mItemAdapter, mContainerProgress, mTvProgress, mRvList);
            task.execute();
        } else {
            loadDataServer();
        }

    }

    private void loadDataServer() {

    }

    public void addItem() {
        Intent intent = new Intent(getActivity(), FormActivity.class);
        intent.putExtra(IS_LOCAL, mIsLocal);

        startActivity(intent);
    }

}
