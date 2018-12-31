package com.ulling.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.ulling.firebasetest.asyncTask.DaumTopJsoupAsyncTask;
import com.ulling.firebasetest.databinding.ActivityIntroBinding;
import com.ulling.firebasetest.entites.KeywordRanking;
import com.ulling.firebasetest.listener.TopCompleteListener;
import com.ulling.firebasetest.utils.MoveActivityUtils;
import com.ulling.firebasetest.view.adapter.KeywordRankingAdapter;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseLifeActivity;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends QcBaseLifeActivity {
    private QUllingApplication qApp;
    private ActivityIntroBinding viewBinding;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private int spanCount = 2;

    private KeywordRankingAdapter adapter;

    private List<KeywordRanking> keywordRanking = new ArrayList<KeywordRanking>();
    private int naverRankingCnt = 20;

    @Override
    protected int needGetLayoutId() {
        return R.layout.activity_intro;
    }

    @Override
    protected void needInitToOnCreate() {
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);

        adapter = new KeywordRankingAdapter(this, null);
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

        viewBinding.recyclerView.setLayoutManager(mStaggeredGridLayoutManager);
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
        keywordRanking = new ArrayList<KeywordRanking>();
        adapter.addAll(keywordRanking);


        new DaumTopJsoupAsyncTask(naverRankingCnt, new TopCompleteListener() {
            @Override
            public void Complete(List<KeywordRanking> keywordRanking) {

                adapter.addAll(keywordRanking);
            }
        }).execute();

//        new NaverTopJsoupAsyncTask(naverRankingCnt, new TopCompleteListener() {
//            @Override
//            public void Complete(List<KeywordRanking> keywordRanking) {
//
//                adapter.addAll(keywordRanking);
//            }
//        }).execute();
    }

}
