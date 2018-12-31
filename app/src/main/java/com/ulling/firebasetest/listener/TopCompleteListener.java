package com.ulling.firebasetest.listener;

import com.ulling.firebasetest.entites.KeywordRanking;

import java.util.List;

public interface TopCompleteListener {
    void Complete(List<KeywordRanking> keywordRanking);
}
