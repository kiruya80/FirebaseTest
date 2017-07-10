package com.ulling.firebasetest.auth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.ulling.firebasetest.BaseActivity;
import com.ulling.firebasetest.R;

import io.fabric.sdk.android.Fabric;

public class TwitterLoginActivity extends BaseActivity
    implements View.OnClickListener {

    private static final String TAG = "TwitterLogin";

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    // [START declare_auth]
    private FirebaseAuth mFirebaseAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private TwitterLoginButton mLoginButton;
    private AuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure Twitter SDK
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
            getString(R.string.twitter_consumer_key),
            getString(R.string.twitter_consumer_secret));
        Fabric.with(this, new Twitter(authConfig));

        // Inflate layout (must be done after Twitter is configured)
        setContentView(R.layout.activity_twitter);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);
        findViewById(R.id.button_twitter_signout).setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]

        // [START initialize_twitter_login]
        mLoginButton = (TwitterLoginButton) findViewById(R.id.button_twitter_login);
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin:success" + result);
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);
                updateUI(null);
            }
        });
        // [END initialize_twitter_login]
    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.
        mLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    // [START auth_with_twitter]
    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        credential = TwitterAuthProvider.getCredential(
            session.getAuthToken().token,
            session.getAuthToken().secret);

        if (mFirebaseAuth.getCurrentUser() != null) {
            // 다른방법으로 로그인이 되어있는 경우만 체크함
            convertToPermanentAccount();
        } else {
            // 비로그인 상태인 경우
            signFirebaseUser();
        }
    }
    // [END auth_with_twitter]

    private void convertToPermanentAccount( ) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
//        AuthCredential credential = FacebookAuthProvider.getCredential(token);
//    String email = "anonymoustest@test.com";
//    String password = "123456";
//    AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        mFirebaseAuth.getCurrentUser().linkWithCredential(credential)
            .addOnCompleteListener(TwitterLoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String message;
                    if (!task.isSuccessful()) {
                        // 인증 정보가 다른 사용자 계정에 이미 연결되어 있다면 linkWithCredential 호출이 실패합니다
                        // 이 경우에는 앱에 적절하도록 중복 계정과 관련 데이터를 병합하는 과정을 처리해야 합니다.
                        Toast.makeText(TwitterLoginActivity.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                        message = "fail : " + task.getException();
                    } else {
                        message = "success";
                    }
                    Log.e(TAG, "linkWithCredential message ====" + message);
                    hideProgressDialog();
                }
            });
    }

    private void signFirebaseUser() {

        mFirebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(TwitterLoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    }

                    // [START_EXCLUDE]
                    hideProgressDialog();
                    // [END_EXCLUDE]
                }
            });

    }


    private void signOut() {
        mFirebaseAuth.signOut();
        Twitter.logOut();

        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.twitter_status_fmt, user.getDisplayName()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_twitter_signout) {
            signOut();
        }
    }
}
