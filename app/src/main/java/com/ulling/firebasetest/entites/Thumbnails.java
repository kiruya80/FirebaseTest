package com.ulling.firebasetest.entites;

import com.google.gson.annotations.SerializedName;

public class Thumbnails {

    @SerializedName("default")
    private ThumbnailData _default;

    @SerializedName("medium")
    private ThumbnailData medium;

    @SerializedName("high")
    private ThumbnailData high;

    public ThumbnailData get_default() {
        return _default;
    }

    public void set_default(ThumbnailData _default) {
        this._default = _default;
    }

    public ThumbnailData getMedium() {
        return medium;
    }

    public void setMedium(ThumbnailData medium) {
        this.medium = medium;
    }

    public ThumbnailData getHigh() {
        return high;
    }

    public void setHigh(ThumbnailData high) {
        this.high = high;
    }

    @Override
    public String toString() {
        return "Thumbnails{" +
                "_default=" + _default +
                ", medium=" + medium +
                ", high=" + high +
                '}';
    }
}
