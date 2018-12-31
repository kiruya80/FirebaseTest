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
package com.ulling.firebasetest.instagram;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ulling.firebasetest.QUllingApplication;
import com.ulling.firebasetest.R;
import com.ulling.firebasetest.common.Define;
import com.ulling.firebasetest.databinding.ActivityInstagramBinding;
import com.ulling.firebasetest.entites.instagram.Edge;
import com.ulling.firebasetest.entites.instagram.EdgeHashtagToMedia;
import com.ulling.firebasetest.entites.instagram.EdgeHashtagToTopPosts;
import com.ulling.firebasetest.entites.instagram.Edge__;
import com.ulling.firebasetest.entites.instagram.Hashtag;
import com.ulling.firebasetest.entites.instagram.Node;
import com.ulling.firebasetest.entites.instagram.SharedData;
import com.ulling.firebasetest.view.adapter.InstagramAdapter;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseLifeActivity;
import com.ulling.lib.core.util.QcGsonUtil;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcUtil;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://developers.facebook.com/docs/instagram-api/hashtag-search
 */
public class InstagramActivity extends QcBaseLifeActivity {

    private QUllingApplication qApp;
    private ActivityInstagramBinding viewBinding;
    private LinearLayoutManager mLinearLayoutManager;

    private InstagramAdapter adapter;

    private String htmlPageUrl = "http://www.yonhapnews.co.kr/"; //파싱할 홈페이지의 URL주소

    // https://www.instagram.com/explore/tags/을지로/?__a=1%22;
    private String INSTAGRAM_URL = "https://www.instagram.com/explore/tags/%EC%9D%84%EC%A7%80%EB%A1%9C/?__a=1%22;"; //파싱할 홈페이지의 URL주소

    private String htmlContentInStringFormat = "";
    private ArrayList<Node> nodeList = new ArrayList<>();
    private String keyword;

    @Override
    protected int needGetLayoutId() {
        return R.layout.activity_instagram;
    }

    @Override
    protected void needInitToOnCreate() {
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();

        mLinearLayoutManager = new LinearLayoutManager(qCon);
        adapter = new InstagramAdapter(this, null);
    }

    @Override
    protected void optGetSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void optGetIntent(Intent intent) {
        keyword = intent.getStringExtra(Define.KEYWORD);
    }

    @Override
    protected void needResetData() {
        nodeList = new ArrayList<>();
    }

    @Override
    protected void needUIBinding() {
        viewBinding = (ActivityInstagramBinding) getViewDataBinding();

        viewBinding.recyclerView.setAdapter(adapter);
        viewBinding.recyclerView.setHasFixedSize(true);
        viewBinding.recyclerView.setNestedScrollingEnabled(false);

        viewBinding.recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void needUIEventListener() {
        viewBinding.btnYoutube.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                searchKeyword();
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
        viewBinding.llProgressBar.setVisibility(View.GONE);
        if (keyword != null && !"".equals(keyword)) {
            viewBinding.editText.setText(keyword);
            searchKeyword();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
    }

    private void searchKeyword() {
        if (!"".equals(viewBinding.editText.getText().toString())) {
            QcUtil.hiddenSoftKey(qCon, viewBinding.editText);
            viewBinding.llProgressBar.setVisibility(View.VISIBLE);
            new InstagramJsoupAsyncTask(viewBinding.editText.getText().toString()).execute();
        }
    }

    /**
     * https://try.jsoup.org/
     * http://lidron.tistory.com/19
     * http://someoneofsunrin.tistory.com/42
     * <p>
     * https://stackoverflow.com/questions/33707679/how-to-get-this-script-from-html-with-jsoup-in-android-programming
     */
    private class InstagramJsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        // https://www.instagram.com/explore/tags/을지로/?__a=1%22;
        private String INSTAGRAM_URL_01 = "https://www.instagram.com/explore/tags/";
        private String INSTAGRAM_URL_02 = "/?__a=1%22;";

        private String sharedData = "";
        private String keyword = "";

        public InstagramJsoupAsyncTask(String keyword) {
            this.keyword = keyword;
            nodeList = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            QcLog.e("doInBackground ===== " + keyword);
            try {
                sharedData = "";
                Document doc = Jsoup.connect(INSTAGRAM_URL_01 + keyword + INSTAGRAM_URL_02)
                        .timeout(Define.JSOUP_TIMEOUT).get();

                QcLog.e("=================================================");

                Elements scriptElements = doc.getElementsByTag("script");
                for (Element element : scriptElements) {
                    for (DataNode node : element.dataNodes()) {
                        if (node.getWholeData().startsWith("window._sharedData = {\"config\":")) {
                            QcLog.e("DataNode == " + node.getWholeData().toString());
                            sharedData += node.getWholeData();
                            break;
                        }
                    }
                }

                sharedData = sharedData.trim();
                sharedData = sharedData.replaceAll("window._sharedData = ", "");
                sharedData = sharedData.replaceAll(";", "");
                htmlContentInStringFormat = sharedData;
                QcLog.e("sharedData == " + sharedData);

                SharedData resultStr = QcGsonUtil.getInstance(qCon).getStringToJson(sharedData, SharedData.class);
                QcLog.e("QcGsonUtil SharedData  ================ " + resultStr.toString());
                Hashtag mHashtag = resultStr.getEntryData().getTagPage().get(0).getGraphql().getHashtag();

                EdgeHashtagToMedia mEdgeHashtagToMedia = mHashtag.getEdgeHashtagToMedia();
                List<Edge> edges = mEdgeHashtagToMedia.getEdges();
                for (int i = 0; i < edges.size(); i++) {
                    Node node = edges.get(i).getNode();
                    nodeList.add(node);
                    QcLog.e("태그명으로 검색 == " + node.getId() + " == " + node.getDisplayUrl());
                }


                EdgeHashtagToTopPosts mEdgeHashtagToTopPosts = mHashtag.getEdgeHashtagToTopPosts();
                List<Edge__> edgesTopPosts = mEdgeHashtagToTopPosts.getEdges();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            adapter.addAll(nodeList);
            viewBinding.llProgressBar.setVisibility(View.GONE);
        }
    }


    /**
     * http://lidron.tistory.com/19
     * http://someoneofsunrin.tistory.com/42
     * https://jsoup.org/download
     */
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(htmlPageUrl).timeout(Define.JSOUP_TIMEOUT).get();

                //테스트1
                Elements titles = doc.select("div.news-con h1.tit-news");

                System.out.println("-------------------------------------------------------------");
                for (Element e : titles) {
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }

                //테스트2
                titles = doc.select("div.news-con h2.tit-news");

                System.out.println("-------------------------------------------------------------");
                for (Element e : titles) {
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }

                //테스트3
                titles = doc.select("li.section02 div.con h2.news-tl");

                System.out.println("-------------------------------------------------------------");
                for (Element e : titles) {
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }
                System.out.println("-------------------------------------------------------------");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            viewBinding.txtResult.setText(htmlContentInStringFormat);
        }
    }
}
