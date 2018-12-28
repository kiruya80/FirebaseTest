package com.ulling.firebasetest;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.ulling.lib.core.base.QcBaseApplication;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;

/**
 * Created by KILHO on 2016. 7. 4..
 */
public class QUllingApplication extends QcBaseApplication {
    private static QUllingApplication SINGLE_U;
    public static Context qCon;


    public static synchronized QUllingApplication getInstance() {
        return SINGLE_U;
    }

    @Override
    public void onCreate() {
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
        }
        super.onCreate();
        init();
    }

    /**
     * @MethodName : init
     * @Date : 2015. 3. 15.
     * @author : KILHO
     * @Method ㄴ 초기화
     * @변경이력
     */
    private synchronized void init() {
        SINGLE_U = this;
        qCon = getApplicationContext();
        APP_NAME = qCon.getResources().getString(R.string.app_name);
        QcPreferences.getInstance().getAPP_NAME();
        QcToast.getInstance().show("Start App : " + QcPreferences.getInstance().getAPP_NAME(), false);

    }

}