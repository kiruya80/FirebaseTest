/**
 * Copyright Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ulling.firebasetest.youtube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ulling.firebasetest.R;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;

public class YoutubePlayActivity extends YouTubeBaseActivity {

    private static final String TAG = "YoutubeActivity";
    private static final String DEVELOPER_KEY = "AIzaSyC_fxY1zxobTycOfblJ6i2wNBQzDBkxCVA";
    private Button btnYoutube;

    private String youtubeVideoId = "nCgQDjiotG0";
    private YouTubePlayerView youtubeView;


    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_play);

        btnYoutube = (Button) findViewById(R.id.btnYoutube);
        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);


        btnYoutube.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                // api 키 값
                youtubeView.initialize(DEVELOPER_KEY, mOnInitializedListener);
            }
        });


        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.fragmentYoutube);
        youTubePlayerFragment.initialize(DEVELOPER_KEY, mOnInitializedListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, mOnInitializedListener);
        }
    }


    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.fragmentYoutube);
    }


    YouTubePlayer.OnInitializedListener mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                            boolean wasRestored) {
            QcLog.e("onInitializationSuccess === ");
            if (!wasRestored) {
                player.cueVideo(youtubeVideoId);
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                            YouTubeInitializationResult errorReason) {
//            if (errorReason.isUserRecoverableError()) {
//                errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
//            } else {
//                String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
//                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
//            }
        }
    };

}
