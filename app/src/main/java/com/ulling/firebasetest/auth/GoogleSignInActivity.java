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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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

import com.ulling.firebasetest.BaseActivity;
import com.ulling.firebasetest.R;
import com.ulling.firebasetest.entity.Provider;
import com.ulling.firebasetest.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
public class GoogleSignInActivity extends BaseActivity implements
    GoogleApiClient.OnConnectionFailedListener,
    View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mFirebaseAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private AuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();

        // [START initialize_auth]
        mFirebaseAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    User mUser = new User();
                    // User is signed in
                    Log.e(TAG, "onAuthStateChanged:signed_in 1111111====" + user.getUid());

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
                        Log.e(TAG, "providerId === " + providerId + " , profileUid === " + profileUid
                            + " , profileDisplayName === " + profileDisplayName + " , profileEmail === " + profileEmail + " , profilePhotoUrl === " + profilePhotoUrl);


//            :02.076 : providerId === firebase , profileUid === kRcitKd6NHYD2mtlyhrYF5v9efF2 , profileDisplayName === ulim maum , profileEmail === maumullim@gmail.com , profilePhotoUrl === https://lh5.googleusercontent.com/-EpiAYJLM_N0/AAAAAAAAAAI/AAAAAAAAAAA/AEMOYSDvn16YISEFoxOR0ulnQtFBeZ2HWQ/s96-c/photo.jpg
//            :02.076 : providerId === google.com , profileUid === 109877419644795090860 , profileDisplayName === ulim maum , profileEmail === null , profilePhotoUrl === https://lh5.googleusercontent.com/-EpiAYJLM_N0/AAAAAAAAAAI/AAAAAAAAAAA/AEMOYSDvn16YISEFoxOR0ulnQtFBeZ2HWQ/s96-c/photo.jpg
//            :02.077 : providerId === twitter.com , profileUid === 2336747221 , profileDisplayName === iullim , profileEmail === null , profilePhotoUrl === http://pbs.twimg.com/profile_images/800287966409027584/N7EEpmTc_normal.jpg
//            :02.077 : providerId === facebook.com , profileUid === 1802873343316235 ,  profileDisplayName === 아이울림 , profileEmail === null , profilePhotoUrl === https://scontent.xx.fbcdn.net/v/t1.0-1/p100x100/11150516_1582345525369019_5758766710612078674_n.jpg?oh=7d9c536bfda53abde034f3fdc83d7638&oe=58BFF902
//            :02.077 : providerId === password , profileUid === maumullim@gmail.com , profileDisplayName === ulim maum , profileEmail === null , profilePhotoUrl === https://lh5.googleusercontent.com/-EpiAYJLM_N0/AAAAAAAAAAI/AAAAAAAAAAA/AEMOYSDvn16YISEFoxOR0ulnQtFBeZ2HWQ/s96-c/photo.jpg

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

                    if (userUid != null && !userUid.isEmpty()) {
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

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult ====== " + requestCode);
        Toast.makeText(GoogleSignInActivity.this, "onActivityResult == " + requestCode,
            Toast.LENGTH_SHORT).show();
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "GoogleSignInResult ====== " + result.isSuccess());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.e(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        Log.e(TAG, "acct ==== " + acct.toString());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        if (mFirebaseAuth.getCurrentUser() != null) {
            // 다른방법으로 로그인이 되어있는 경우만 체크함
            convertToPermanentAccount();
        } else {
            // 비로그인 상태인 경우
            signFirebaseUser();
        }
    }

    private void convertToPermanentAccount() {
//        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
//    AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//    String email = "anonymoustest@test.com";
//    String password = "123456";
//    AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        mFirebaseAuth.getCurrentUser().linkWithCredential(credential)
            .addOnCompleteListener(GoogleSignInActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String message;
                    if (!task.isSuccessful()) {
                        // 인증 정보가 다른 사용자 계정에 이미 연결되어 있다면 linkWithCredential 호출이 실패합니다
                        // 이 경우에는 앱에 적절하도록 중복 계정과 관련 데이터를 병합하는 과정을 처리해야 합니다.
                        Toast.makeText(GoogleSignInActivity.this, "Authentication failed." + task.getException(),
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
        if (credential == null)
            return;

        mFirebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.e(TAG, "signInWithCredential onComplete 222222222==" + task.isSuccessful());
                    Log.e(TAG, "task ==== " + task.toString());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(GoogleSignInActivity.this, "Authentication failed." + task.getException(),
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
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mFirebaseAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
            new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    updateUI(null);
                }
            });
    }

    private void revokeAccess() {
        // Firebase sign out
        mFirebaseAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
            new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    updateUI(null);
                }
            });
    }

    private void updateUI(FirebaseUser user) {
        Log.e(TAG, "updateUI ====== ");
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.e(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            Log.e(TAG, "sign_in_button======= ");
            signIn();
        } else if (i == R.id.sign_out_button) {
            Log.e(TAG, "sign_out_button======= ");
            signOut();
        } else if (i == R.id.disconnect_button) {
            Log.e(TAG, "disconnect_button======= ");
            revokeAccess();
        }
    }
}
