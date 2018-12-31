package com.ulling.firebasetest.entites;

/**
 *
 */
public class KeywordRanking {
    private int ranking;
    private String keyword;

    public KeywordRanking(int ranking, String keyword) {
        this.ranking = ranking;
        this.keyword = keyword;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "NaverRanking{" +
                "ranking=" + ranking +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
