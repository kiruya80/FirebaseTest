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

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ulling.firebasetest.R;
import com.ulling.firebasetest.common.Define;
import com.ulling.firebasetest.entites.SearchResponse;
import com.ulling.firebasetest.entites.YoutubeItem;
import com.ulling.firebasetest.network.RetrofitService;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeActivity extends AppCompatActivity {

    private static final String TAG = "YoutubeActivity";
    private static final String DEVELOPER_KEY = "AIzaSyC_fxY1zxobTycOfblJ6i2wNBQzDBkxCVA";

    private EditText editText;
    private TextView txtResult;
    private RecyclerView recyclerView;
    private Button btnYoutube;

//    private Location location;
    private double latitude = 37.566120; // 위도
    private double longitude = 126.982540; // 경도

    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        editText = (EditText) findViewById(R.id.editText);
        txtResult = (TextView) findViewById(R.id.txtResult);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnYoutube = (Button) findViewById(R.id.btnYoutube);

        // 37.566120, 126.982540 - 을지로역
        btnYoutube.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                result = "";
                txtResult.setText(result);

                getSearchList("snippet",
                        latitude + "," + longitude,
                        "5km",
                        "25",
                        "date",
                        "video,list",
                        editText.getText().toString());
            }
        });
    }


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
                        List<YoutubeItem> items = mSearchResponse.getItems();
                        for (int i = 0; i < items.size(); i++) {
                            QcLog.e("items = " + items.get(i).getSnippet().getTitle());
                            result = result + "\n\n" + items.get(i).getSnippet().getTitle();
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtResult.setText(result);
                        }
                    });
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
