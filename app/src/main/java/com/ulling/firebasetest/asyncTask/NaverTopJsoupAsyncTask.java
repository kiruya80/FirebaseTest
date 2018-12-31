package com.ulling.firebasetest.asyncTask;

import android.os.AsyncTask;

import com.ulling.firebasetest.common.Define;
import com.ulling.firebasetest.entites.KeywordRanking;
import com.ulling.firebasetest.listener.TopCompleteListener;
import com.ulling.lib.core.util.QcLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 네이버 실시간 순위 파싱
 * <p>
 * http://someoneofsunrin.tistory.com/42
 */
public class NaverTopJsoupAsyncTask extends AsyncTask<Void, Void, Void> {
    private String URL = "https://www.naver.com/";
    private String SELECT = "span.ah_k";

    private Document doc;
    private Elements contents;

    private TopCompleteListener listener;

    private List<String> keywordList = new ArrayList<String>();
    private List<KeywordRanking> keywordRanking = new ArrayList<KeywordRanking>();
    private int number = 10;

    public NaverTopJsoupAsyncTask(int number, TopCompleteListener listener) {
        this.keywordList = new ArrayList<String>();
        this.keywordRanking = new ArrayList<KeywordRanking>();
        this.number = number;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            doc = Jsoup.connect(URL).timeout(Define.JSOUP_TIMEOUT).get();
            contents = doc.select(SELECT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Element element : contents) {
            String keyword = element.text();

            if (keywordList.size() == 0) {
                keywordList.add(keyword);

            } else {
                if (!keywordList.contains(keyword)) {
                    keywordList.add(keyword);
                }
            }

            if (keywordList.size() == number)
                break;
        }

        if (keywordList != null && keywordList.size() > 0) {
            for (int i = 0; i < keywordList.size(); i++) {
                KeywordRanking mKeywordRanking = new KeywordRanking(i + 1, keywordList.get(i));
                keywordRanking.add(mKeywordRanking);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        QcLog.e("keywordRanking ==== " + keywordRanking.toString());
        if (listener != null)
            listener.Complete(keywordRanking);
    }
}