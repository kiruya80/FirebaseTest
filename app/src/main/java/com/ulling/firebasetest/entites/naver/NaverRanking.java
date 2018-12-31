package com.ulling.firebasetest.entites.naver;

/**
 *
 */
public class NaverRanking {
    private int ranking;
    private String keyword;

    public NaverRanking(int ranking, String keyword) {
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
