package com.allen.code.allendownloader;

import android.app.Application;

import com.allen.code.allendownloader.utils.ScreenAdaptiveHelper;
import com.allen.code.downloader.DownLoaderManager;
import com.facebook.stetho.Stetho;
import com.github.anrwatchdog.ANRWatchDog;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

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
        setAnalyzer();
    }

    protected void setAnalyzer() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        new ANRWatchDog().start();
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

}
