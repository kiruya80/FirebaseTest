/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ulling.firebasetest.auth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ulling.firebasetest.BaseActivity;
import com.ulling.firebasetest.R;
import com.ulling.firebasetest.entity.Provider;
import com.ulling.firebasetest.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
public class FacebookLoginActivity extends BaseActivity implements
    View.OnClickListener {
    private static final String TAG = "FacebookLogin";

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    // [START declare_auth]
    private FirebaseAuth mFirebaseAuth;
    // [END declare_auth]
    private FirebaseUser mFirebaseUser;

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private CallbackManager mCallbackManager;
    private DatabaseReference mDatabase;
    private AuthCredential credential;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);
        findViewById(R.id.button_facebook_signout).setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    User mUser = new User();
                    // User is signed in
                    Log.e(TAG, "onAuthStateChanged:signed_in 1111111====" + user.getUid());
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();

                    if (name != null && !name.toString().isEmpty())
                        Log.e(TAG, "user === " + name.toString());
                    if (email != null && !email.toString().isEmpty())
                        Log.e(TAG, " , email === " + email.toString());
                    if (photoUrl != null && !photoUrl.toString().isEmpty())
                        Log.e(TAG, " , photoUrl === " + photoUrl.toString());

                    String userUid = null;
                    Map<String, Provider> providersMap = new HashMap<String, Provider>();
                    // Even a user's provider-specific profile information
                    // only reveals basic information
                    for (UserInfo profile : user.getProviderData()) {
                        // Id of the provider (ex: google.com)
                        String providerId = profile.getProviderId();
                        // UID specific to the provider
                        String profileUid = profile.getUid();
                        // Name, email address, and profile photo Url
                        String profileDisplayName = profile.getDisplayName();
                        String profileEmail = profile.getEmail();
                        Uri profilePhotoUrl = profile.getPhotoUrl();
                        Log.e(TAG, "providerId === " + providerId + " , profileUid === " + profileUid + " , profileUid === " + profileUid
                            + " , profileDisplayName === " + profileDisplayName + " , profileEmail === " + profileEmail + " , profilePhotoUrl === " + profilePhotoUrl);


                        if (profile.getProviderId() != null && profile.getProviderId().equals("firebase")) {
                            // 기본계정
//              map.put("provider", profile.getProviderId());
//              if (!profile.getDisplayName().isEmpty()) {
//                map.put("displayName", profile.getDisplayName().toString());
//              }
//              mDatabase.child("users").child(profile.getUid()).setValue(map);
                            if (profile.getDisplayName() != null && !profile.getDisplayName().isEmpty()) {
                                mUser.displayName = profile.getDisplayName();
                            }
                            if (profile.getUid() != null && !profile.getUid().isEmpty()) {
                                userUid = profile.getUid();
                            }

                        } else if (profile.getProviderId() != null && profile.getProviderId().equals("google.com")) {
                            if (!profile.getProviderId().isEmpty()) {
                                Provider provider = new Provider();
                                provider.provider = profile.getProviderId();
                                provider.providerId = profile.getUid();
                                provider.photoUrl = profile.getPhotoUrl().toString();
                                providersMap.put("google", provider);

                                if (mUser.photoUrl.isEmpty() && !provider.photoUrl.isEmpty()) {
                                    mUser.photoUrl = provider.photoUrl;
                                }
                            }

                        } else if (profile.getProviderId() != null && profile.getProviderId().equals("facebook.com")) {
                            if (!profile.getProviderId().isEmpty()) {
                                Provider provider = new Provider();
                                provider.provider = profile.getProviderId();
                                provider.providerId = profile.getUid();
                                provider.photoUrl = profile.getPhotoUrl().toString();
                                providersMap.put("facebook", provider);

                                if (mUser.photoUrl.isEmpty() && !provider.photoUrl.isEmpty()) {
                                    mUser.photoUrl = provider.photoUrl;
                                }
                            }
                        } else if (profile.getProviderId() != null && profile.getProviderId().equals("twitter.com")) {
                            if (!profile.getProviderId().isEmpty()) {
                                Provider provider = new Provider();
                                provider.provider = profile.getProviderId();
                                provider.providerId = profile.getUid();
                                provider.photoUrl = profile.getPhotoUrl().toString();
                                providersMap.put("twitter", provider);

                                if (mUser.photoUrl.isEmpty() && !provider.photoUrl.isEmpty()) {
                                    mUser.photoUrl = provider.photoUrl;
                                }
                            }
                        } else if (profile.getProviderId() != null && profile.getProviderId().equals("password")) {
                            if (!profile.getProviderId().isEmpty()) {
                                Provider provider = new Provider();
                                provider.provider = profile.getProviderId();
                                provider.providerId = profile.getUid();
                                provider.photoUrl = profile.getPhotoUrl().toString();
                                providersMap.put("password", provider);

                                if (mUser.photoUrl.isEmpty() && !provider.photoUrl.isEmpty()) {
                                    mUser.photoUrl = provider.photoUrl;
                                }
                            }

                        } else if (profile.getProviderId() != null && profile.getProviderId().equals("instagram.com")) {

                        }
                    }

                    mUser.providers = providersMap;

                    if (!userUid.isEmpty()) {
                        Log.d(TAG, "mUser === " + mUser.toString());
                        mDatabase.child("users").child(userUid).setValue(mUser);
                    }


                    // Authentication just completed successfully :)
//          Map<String, String> map = new HashMap<String, String>();
//          map.put("provider", authData.getProvider());
//          if (firebaseAuth.getProviderData().containsKey("displayName")) {
//            map.put("displayName", authData.getProviderData().get("displayName").toString());
//          }
//          mDatabase.child("users").child(authData.getUid()).setValue(map);
                } else {
                    // User is signed out
                    Log.e(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]

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
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.e(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        credential = FacebookAuthProvider.getCredential(token.getToken());


//    signFirebaseUser();
        if (mFirebaseAuth.getCurrentUser() != null) {
            // 다른방법으로 로그인이 되어있는 경우만 체크함
            convertToPermanentAccount();
        } else {
            // 비로그인 상태인 경우
            signFirebaseUser();
        }
    }

    private void convertToPermanentAccount() {
//        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
//    AuthCredential credential = FacebookAuthProvider.getCredential(token);
//    String email = "anonymoustest@test.com";
//    String password = "123456";
//    AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        mFirebaseAuth.getCurrentUser().linkWithCredential(credential)
            .addOnCompleteListener(FacebookLoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String message;
                    if (!task.isSuccessful()) {
                        // 인증 정보가 다른 사용자 계정에 이미 연결되어 있다면 linkWithCredential 호출이 실패합니다
                        // 이 경우에는 앱에 적절하도록 중복 계정과 관련 데이터를 병합하는 과정을 처리해야 합니다.
                        Toast.makeText(FacebookLoginActivity.this, "Authentication failed." + task.getException(),
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
                    Log.e(TAG, "signInWithCredential onComplete 222222222==" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(FacebookLoginActivity.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                    } else {

                        mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        if (mFirebaseUser == null) {
                            // Not signed in, launch the Sign In activity

                        } else {
                            Log.e(TAG, "mFirebaseUser:" + mFirebaseUser.toString());

                            mFirebaseUser = mFirebaseAuth.getCurrentUser();
                            if (mFirebaseUser != null) {
                                Log.e(TAG, "mFirebaseUser:" + mFirebaseUser.toString());
                                if (mFirebaseUser.getUid() != null)
                                    Log.e(TAG, "getUid == " + mFirebaseUser.getUid().toString());

                                if (mFirebaseUser.getDisplayName() != null)
                                    Log.e(TAG, "getDisplayName == " + mFirebaseUser.getDisplayName().toString());

                                if (mFirebaseUser.getEmail() != null)
                                    Log.e(TAG, "getEmail == " + mFirebaseUser.getEmail().toString());
                                if (mFirebaseUser.getProviderId() != null)
                                    Log.e(TAG, "getProviderId == " + mFirebaseUser.getProviderId().toString());


                                if (mFirebaseUser.getPhotoUrl() != null)
                                    Log.e(TAG, "getPhotoUrl == " + mFirebaseUser.getPhotoUrl().toString());


                                if (mFirebaseUser.getProviders() != null) {
                                    // 페이스북 구글등 로그인 방법
                                    //  [facebook.com]
                                    Log.e(TAG, "getProviders == " + mFirebaseUser.getProviders().toString());
                                }

                                if (mFirebaseUser.getProviderData() != null) {
                                    for (int i = 0; i < mFirebaseUser.getProviderData().size(); i++) {
                                        Log.e(TAG, i + " = getUid == " + mFirebaseUser.getProviderData().get(i).getUid());
                                        Log.e(TAG, i + " = getProviderId == " + mFirebaseUser.getProviderData().get(i).getProviderId());
                                        Log.e(TAG, i + " = getDisplayName == " + mFirebaseUser.getProviderData().get(i).getDisplayName());

                                    }
                                }
                            }
                        }
                    }
                    // [START_EXCLUDE]
                    hideProgressDialog();
                    // [END_EXCLUDE]
                }
            });
    }
    // [END auth_with_facebook]

    public void signOut() {
        mFirebaseAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.facebook_status_fmt, user.getDisplayName()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_facebook_signout) {
            signOut();
        }
    }
}
