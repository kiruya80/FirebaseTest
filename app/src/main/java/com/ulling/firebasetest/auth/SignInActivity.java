/**
 * Copyright Google Inc. All Rights Reserved.
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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ulling.firebasetest.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private Button AnonymousAuthBtn;
    private Button CustomAuthBtn;
    private Button EmailPasswordBtn;
    private Button googleLogInBtn;
    private Button facebookLogInBtn;
    private Button TwitterLoginBtn;


    // Firebase instance variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        AnonymousAuthBtn = (Button) findViewById(R.id.AnonymousAuthBtn);
        CustomAuthBtn = (Button) findViewById(R.id.CustomAuthBtn);
        EmailPasswordBtn = (Button) findViewById(R.id.EmailPasswordBtn);
        // Assign fields
        googleLogInBtn = (Button) findViewById(R.id.googleLogInBtn);
        facebookLogInBtn = (Button) findViewById(R.id.facebookLogInBtn);
        TwitterLoginBtn = (Button) findViewById(R.id.TwitterLoginBtn);

        // Set click listeners
        AnonymousAuthBtn.setOnClickListener(this);
        CustomAuthBtn.setOnClickListener(this);
        EmailPasswordBtn.setOnClickListener(this);
        googleLogInBtn.setOnClickListener(this);
        facebookLogInBtn.setOnClickListener(this);
        TwitterLoginBtn.setOnClickListener(this);

        // Configure Google
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.AnonymousAuthBtn:
                intent = new Intent(this, AnonymousAuthActivity.class);
                startActivity(intent);
                break;
            case R.id.CustomAuthBtn:
                intent = new Intent(this, CustomAuthActivity.class);
                startActivity(intent);
                break;
            case R.id.EmailPasswordBtn:
                intent = new Intent(this, EmailPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.googleLogInBtn:
                intent = new Intent(this, GoogleSignInActivity.class);
                startActivity(intent);
                break;
            case R.id.facebookLogInBtn:
                intent = new Intent(this, FacebookLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.TwitterLoginBtn:
                intent = new Intent(this, TwitterLoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
