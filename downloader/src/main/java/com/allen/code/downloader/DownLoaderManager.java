package com.allen.code.downloader;

import android.app.Application;
import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.net.Proxy;

/**
 * 作者：husongzhen on 17/8/15 12:39
 * 邮箱：husongzhen@musikid.com
 */

public class DownLoaderManager {


    private final static class HolderClass {
        private final static DownLoaderManager INSTANCE = new DownLoaderManager();
        private final static IDownLoader downLoader = new FileDownLoaderImpl();
    }


    private DownLoaderManager() {
    }


    public static DownLoaderManager news() {
        return HolderClass.INSTANCE;
    }


    public IDownLoader getDownLoader() {
        return HolderClass.downLoader;
    }

    public static void initDownLoader(Application context) {
        // just for open the log in this demo project.
        FlowManager.init(context);

        /**
         * just for cache Application's Context, and ':filedownloader' progress will NOT be launched
         * by below code, so please do not worry about performance.
         * @see FileDownloader#init(Context)
         */

        FileDownloader.setupOnApplicationOnCreate(context)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                        .proxy(Proxy.NO_PROXY) // set proxy
                ))
                .commit();
    }
}
