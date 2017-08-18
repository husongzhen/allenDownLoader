package com.allen.code.downloader;

/**
 * 作者：husongzhen on 17/8/15 12:24
 * 邮箱：husongzhen@musikid.com
 */

public interface DownLoaderListener {

    void onWait(DownTaskInfo tag, int downsize, int totalSize);

    void onProgress(DownTaskInfo tag, int soFarBytes, int totalBytes);

    void onPause(DownTaskInfo tag, int soFarBytes, int totalBytes);

    void onError(DownTaskInfo tag, Throwable e);

    void onFinish(DownTaskInfo tag);
}
