package com.totvs.classificados.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.totvs.classificados.R;
import com.totvs.classificados.activity.FormActivity;
import com.totvs.classificados.adapter.ItemAdapter;
import com.totvs.classificados.database.model.AdItem;
import com.totvs.classificados.task.LoadDateTask;
import com.totvs.classificados.view.LinearRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private RequestQueue mRequestQueue;

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

        mRequestQueue = Volley.newRequestQueue(getActivity());

        loadData();

    }

    public void loadData() {
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

        final StringRequest request = new StringRequest(Request.Method.POST, "http://orbisxp.com/api/list_items", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("VOLLEY", response);

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String title = (String) object.get("title");
                        String description = (String) object.get("description");
                        AdItem item = new AdItem(null, title, description);
                        mItems.add(item);
                    }

                    mContainerProgress.setVisibility(View.INVISIBLE);
                    mRvList.setVisibility(View.VISIBLE);

                    //Only Anim
                    Animation animOut = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                    Animation animIn = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);

                    mItemAdapter.notifyDataSetChanged();

                    mContainerProgress.startAnimation(animOut);
                    mRvList.startAnimation(animIn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY_ERROR", error.getMessage(), error.getCause());
            }
        });

        mRequestQueue.add(request);

    }

    public void addItem() {
        Intent intent = new Intent(getActivity(), FormActivity.class);
        intent.putExtra(IS_LOCAL, mIsLocal);

        startActivity(intent);
    }

}
