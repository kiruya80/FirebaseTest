package com.ulling.firebasetest.common;

import com.ulling.lib.core.common.QcDefine;

/**
 *
 * Class: Define
 * Version 1.0.0
 * Created by P139090 on 2018-12-28. 오후 3:35
 *
 * Description:
 *
 */
public class Define extends QcDefine {
    /**
     * 상용/ 테스트용
     */
//    public static final boolean DEBUG_FLAG = true;

    public static final String YOUTUBE_API_KEY = "AIzaSyC_fxY1zxobTycOfblJ6i2wNBQzDBkxCVA";

    public static final int PAGE_SIZE = 20;

    /**
     * TimeOut
     */
    public static final int INTRO_TIMEOUT = 1500;

    public static final String dateFormatFrom = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final int JSOUP_TIMEOUT = 30000;

    public static final String KEYWORD = "KEYWORD";
    public static final String YOUTUBE = "https://www.googleapis.com/auth/youtube";

    /** See, edit, and permanently delete your YouTube videos, ratings, comments and captions. */
    public static final String YOUTUBE_FORCE_SSL = "https://www.googleapis.com/auth/youtube.force-ssl";

    /** View your YouTube account. */
    public static final String YOUTUBE_READONLY = "https://www.googleapis.com/auth/youtube.readonly";

    /** Manage your YouTube videos. */
    public static final String YOUTUBE_UPLOAD = "https://www.googleapis.com/auth/youtube.upload";

    /** View and manage your assets and associated content on YouTube. */
    public static final String YOUTUBEPARTNER = "https://www.googleapis.com/auth/youtubepartner";

    /** View private information of your YouTube channel relevant during the audit process with a YouTube partner. */
    public static final String YOUTUBEPARTNER_CHANNEL_AUDIT = "https://www.googleapis.com/auth/youtubepartner-channel-audit";

}
