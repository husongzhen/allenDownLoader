package com.allen.code.downloader;

import java.util.List;

/**
 * 作者：husongzhen on 17/8/15 11:30
 * 邮箱：husongzhen@musikid.com
 */

public interface IDownLoader {

    List<DownTaskInfo> restoreLoading();

    List<DownTaskInfo> restoreLoaded();

    void registerDownListener(String key, DownLoaderListener listener);

    void unRegisterDownListener(String key);


    void startTask(String tag, DownTaskInfo info);

    void stopTask(String tag);

    void cancleTask(String tag, DownTaskInfo info);


    void exit();


}
