package com.ulling.firebasetest.asyncTask;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.ulling.firebasetest.auth.GoogleSignInActivity;
import com.ulling.firebasetest.common.Define;
import com.ulling.lib.core.util.QcLog;

import java.io.IOException;

public class GoogleAuthTask extends AsyncTask<Void, Void, String> {
    private final Account account;
    private Activity activity;
    private GoogleAuthListener listener;
    private String authtoken = "";
    private GooglePlayServicesAvailabilityException playEx;
    private UserRecoverableAuthException userAuthEx;

    private String SCOPE_STRING = "oauth2:"
//                        + Scopes.PLUS_PROFILE
            + " " + Define.YOUTUBE
            + " " + Define.YOUTUBE_FORCE_SSL
            + " " + Define.YOUTUBE_READONLY;

    public interface GoogleAuthListener {
        void onComplete(String authtoken_);
        void onGooglePlayServicesAvailabilityException(GooglePlayServicesAvailabilityException playEx);
        void onUserRecoverableAuthException(UserRecoverableAuthException userAuthEx);
    }

    public GoogleAuthTask(Activity activity, Account account,
                          GoogleAuthListener listener) {
        this.activity = activity;
        this.account = account;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        QcLog.e("doInBackground ===  " );
        try {

            authtoken = GoogleAuthUtil.getToken(activity, account, SCOPE_STRING);

        } catch (GooglePlayServicesAvailabilityException playEx) {
            QcLog.e("GooglePlayServicesAvailabilityException == " + playEx.getMessage());
            this.playEx = playEx;
//                GooglePlayServicesUtil.getErrorDialog(playEx.getConnectionStatusCode(),
//                        LogInActivity.this, REQUEST_GMS_ERROR_DIALOG).show();
        } catch (UserRecoverableAuthException userAuthEx) {
            QcLog.e("UserRecoverableAuthException == " + userAuthEx.getMessage());
            this.userAuthEx = userAuthEx;
//            startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
//            startActivityForResult(userAuthEx.getIntent(), 123);

        } catch (IOException transientEx) {
            QcLog.e("IOException == " + transientEx.getMessage());

        } catch (GoogleAuthException authEx) {
            QcLog.e("GoogleAuthException == " + authEx.getMessage());
        }

        QcLog.e("authtoken === " + authtoken);

        return authtoken;
    }

    @Override
    protected void onPostExecute(String token) {
        if (listener != null) {
            if (token != null && !"".equals(token)) {
                listener.onComplete(token);
            } else {
                if (playEx != null) {
                    listener.onGooglePlayServicesAvailabilityException(playEx);
                } else {
                    if (userAuthEx != null)
                        listener.onUserRecoverableAuthException(userAuthEx);
                }
            }
        }
    }
}