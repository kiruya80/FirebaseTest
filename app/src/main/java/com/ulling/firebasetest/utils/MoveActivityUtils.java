package com.ulling.firebasetest.utils;

import android.app.Activity;
import android.content.Intent;

import com.ulling.firebasetest.MainActivity;
import com.ulling.firebasetest.R;
import com.ulling.firebasetest.instagram.InstagramActivity;
import com.ulling.firebasetest.youtube.YoutubeActivity;

public class MoveActivityUtils {

    /**
     * 메인 액티비티 이동
     *
     * @param activity
     */
    public static void moveMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
    }


    public static void moveYoutubeActivity(Activity activity) {
        Intent intent = new Intent(activity, YoutubeActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
    }

    public static void moveInstagramActivity(Activity activity) {
        Intent intent = new Intent(activity, InstagramActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
    }
}
