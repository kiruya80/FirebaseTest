package com.ulling.firebasetest.entites.instagram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SharedData {

//    @SerializedName("config")
//    @Expose
//    private Config config;
    @SerializedName("supports_es6")
    @Expose
    private Boolean supportsEs6;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("language_code")
    @Expose
    private String languageCode;
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("entry_data")
    @Expose
    private EntryData entryData;
//    @SerializedName("gatekeepers")
//    @Expose
//    private Gatekeepers gatekeepers;
//    @SerializedName("knobs")
//    @Expose
//    private Knobs knobs;
//    @SerializedName("qe")
//    @Expose
//    private Qe qe;
//    @SerializedName("hostname")
//    @Expose
//    private String hostname;
//    @SerializedName("deployment_stage")
//    @Expose
//    private String deploymentStage;
//    @SerializedName("platform")
//    @Expose
//    private String platform;
//    @SerializedName("rhx_gis")
//    @Expose
//    private String rhxGis;
//    @SerializedName("nonce")
//    @Expose
//    private String nonce;
//    @SerializedName("mid_pct")
//    @Expose
//    private Double midPct;
//    @SerializedName("server_checks")
//    @Expose
//    private ServerChecks serverChecks;
//    @SerializedName("zero_data")
//    @Expose
//    private ZeroData zeroData;
//    @SerializedName("rollout_hash")
//    @Expose
//    private String rolloutHash;
//    @SerializedName("bundle_variant")
//    @Expose
//    private String bundleVariant;
//    @SerializedName("probably_has_app")
//    @Expose
//    private Boolean probablyHasApp;

    public class EntryData {
        @SerializedName("TagPage")
        @Expose
        private List<TagPage> tagPage = null;

        public List<TagPage> getTagPage() {
            return tagPage;
        }

        public void setTagPage(List<TagPage> tagPage) {
            this.tagPage = tagPage;
        }

    }

    public Boolean getSupportsEs6() {
        return supportsEs6;
    }

    public void setSupportsEs6(Boolean supportsEs6) {
        this.supportsEs6 = supportsEs6;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public EntryData getEntryData() {
        return entryData;
    }

    public void setEntryData(EntryData entryData) {
        this.entryData = entryData;
    }

    @Override
    public String toString() {
        return "SharedData{" +
                "supportsEs6=" + supportsEs6 +
                ", countryCode='" + countryCode + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", locale='" + locale + '\'' +
                ", entryData=" + entryData +
                '}';
    }
}
