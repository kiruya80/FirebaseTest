package com.ulling.firebasetest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ulling.firebasetest.common.Define;
import com.ulling.firebasetest.databinding.ActivityIntroBinding;
import com.ulling.firebasetest.entites.naver.NaverRanking;
import com.ulling.firebasetest.utils.MoveActivityUtils;
import com.ulling.firebasetest.view.adapter.NaverRankingAdapter;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseLifeActivity;
import com.ulling.lib.core.util.QcLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class IntroActivity extends QcBaseLifeActivity {
    private QUllingApplication qApp;
    private ActivityIntroBinding viewBinding;
    private LinearLayoutManager mLinearLayoutManager;

    private NaverRankingAdapter adapter;

    private ArrayList<NaverRanking> naverRanking = new ArrayList<NaverRanking>();

    @Override
    protected int needGetLayoutId() {
        return R.layout.activity_intro;
    }

    @Override
    protected void needInitToOnCreate() {
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();

        mLinearLayoutManager = new LinearLayoutManager(qCon);
        adapter = new NaverRankingAdapter(this, null);
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
        viewBinding = (ActivityIntroBinding) getViewDataBinding();

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
                MoveActivityUtils.moveYoutubeActivity(IntroActivity.this);
            }
        });
        viewBinding.btnInstagram.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                MoveActivityUtils.moveInstagramActivity(IntroActivity.this);
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
        naverRanking = new ArrayList<NaverRanking>();
        adapter.addAll(naverRanking);
        new NaverTopJsoupAsyncTask(10).execute();
    }


    /**
     * 네이버 실시간 순위 파싱
     * <p>
     * http://someoneofsunrin.tistory.com/42
     */
    private class NaverTopJsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        private String htmlPageUrl = "https://www.naver.com/";
        private Document doc;
        private Elements contents;
        private String Top10;
        private int number = 10;

        public NaverTopJsoupAsyncTask(int number) {
            this.number = number;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                doc = Jsoup.connect(htmlPageUrl).timeout(Define.JSOUP_TIMEOUT).get(); //naver페이지를 불러옴
                contents = doc.select("span.ah_k");//셀렉터로 span태그중 class값이 ah_k인 내용을 가져옴
            } catch (IOException e) {
                e.printStackTrace();
            }
            Top10 = "";
            int cnt = 0;//숫자를 세기위한 변수
            for (Element element : contents) {
                cnt++;
                Top10 += cnt + ". " + element.text() + "\n";
                NaverRanking mNaverRanking = new NaverRanking(cnt, element.text());
                naverRanking.add(mNaverRanking);
                if (cnt == number)//10위까지 파싱하므로
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            QcLog.e("");
//            viewBinding.txtNaverTop10.setText(naverRanking.toString());
            adapter.addAll(naverRanking);
        }
    }

}
