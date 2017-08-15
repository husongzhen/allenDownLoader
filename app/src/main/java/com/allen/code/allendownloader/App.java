package com.allen.code.allendownloader;

import android.app.Application;

import com.allen.code.allendownloader.utils.ScreenAdaptiveHelper;
import com.allen.code.downloader.DownLoaderManager;
import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * 作者：husongzhen on 17/8/15 12:41
 * 邮箱：husongzhen@musikid.com
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DownLoaderManager.initDownLoader(this);
        Stetho.initializeWithDefaults(this);
        ScreenAdaptiveHelper.init(this);
        FlowManager.init(this);
    }
}
