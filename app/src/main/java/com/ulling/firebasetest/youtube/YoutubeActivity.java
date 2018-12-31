/**
 * Copyright Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ulling.firebasetest.youtube;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ulling.firebasetest.databinding.ActivityYoutubeBinding;
import com.ulling.firebasetest.QUllingApplication;
import com.ulling.firebasetest.R;
import com.ulling.firebasetest.common.Define;
import com.ulling.firebasetest.entites.youtube.SearchResponse;
import com.ulling.firebasetest.entites.youtube.YoutubeItem;
import com.ulling.firebasetest.network.RetrofitService;
import com.ulling.firebasetest.view.adapter.YoutubeAdapter;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseLifeActivity;
import com.ulling.lib.core.util.QcLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeActivity extends QcBaseLifeActivity {
    private static final String DEVELOPER_KEY = "AIzaSyC_fxY1zxobTycOfblJ6i2wNBQzDBkxCVA";

    private QUllingApplication qApp;
    private ActivityYoutubeBinding viewBinding;
    private LinearLayoutManager mLinearLayoutManager;
    private YoutubeAdapter adapter;

    //    private Location location;
    private double latitude = 37.566120; // 위도
    private double longitude = 126.982540; // 경도

    private List<YoutubeItem> items;

    private String result = "";

    @Override
    protected int needGetLayoutId() {
        return R.layout.activity_youtube;
    }

    @Override
    protected void needInitToOnCreate() {
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();

        mLinearLayoutManager = new LinearLayoutManager(qCon);
        adapter = new YoutubeAdapter(this, null);
    }

    @Override
    protected void optGetSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void optGetIntent(Intent intent) {

    }

    @Override
    protected void needResetData() {

    }

    @Override
    protected void needUIBinding() {
        viewBinding = (ActivityYoutubeBinding) getViewDataBinding();

        viewBinding.recyclerView.setAdapter(adapter);
        viewBinding.recyclerView.setHasFixedSize(true);
        viewBinding.recyclerView.setNestedScrollingEnabled(false);

//        int valueInPixels = (int) getResources().getDimension(R.dimen.p20);
//        CustomRecyclerDecoration mCustomRecyclerDecoration = new CustomRecyclerDecoration(valueInPixels);
//        viewBinding.includeDetailDoodle.recyclerView.addItemDecoration(mCustomRecyclerDecoration);

        viewBinding.recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void needUIEventListener() {

        // 37.566120, 126.982540 - 을지로역
        viewBinding.btnYoutube.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                result = "";
                viewBinding.txtResult.setText(result);

                getSearchList("snippet",
                        latitude + "," + longitude,
                        "5km",
                        "25",
                        "date",
                        "video,list",
                        viewBinding.editText.getText().toString());
            }
        });

    }

    @Override
    protected void needSubscribeUiFromViewModel() {

    }

    @Override
    protected void needSubscribeUiClear() {

    }

    @Override
    protected void needOnShowToUser() {

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_youtube);
//
//    }


    public void getSearchList(String part,
                              String location,
                              String locationRadius,
                              String maxResults,
                              String order,
                              String type,
                              String q) {
        QcLog.e("getSearchList ===  ");
        Call<SearchResponse> call = RetrofitService.getInstance().getSearchList(Define.YOUTUBE_API_KEY,
                part,
                location,
                locationRadius,
                maxResults,
                order,
                type,
                q);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                QcLog.e("onResponse === " + response.toString());
                if (response.isSuccessful()) {
                    QcLog.e("onResponse === isSuccessful ");
                    SearchResponse mSearchResponse = response.body();

                    if (mSearchResponse != null) {
                        items = mSearchResponse.getItems();
                        for (int i = 0; i < items.size(); i++) {
                            QcLog.e("items = " + items.get(i).getSnippet().getTitle());
                            result = result + "\n\n" + items.get(i).getSnippet().getTitle();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                            viewBinding.txtResult.setText(result);
                                adapter.addAll(items);
                            }
                        });
                    }

                } else {
                    QcLog.e("onResponse === false");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                QcLog.e("onFailure error loading from API == " + t.toString() + " , " + t.getMessage());
            }
        });
    }
}
