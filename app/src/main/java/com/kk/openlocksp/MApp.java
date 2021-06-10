package com.kk.openlocksp;

import android.app.Application;


/**
 * Application
 */
public class MApp extends Application {

    public static MApp mApp;
    public KSerialPor COM1;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

    }

    @Override
    public void onTerminate() {
        COM1.closeSerialPort();
        super.onTerminate();
    }

    public void initCOM(KCallBack.CallBackInterface aCBI)
    {
        COM1 = new KSerialPor(aCBI);
    }
}

