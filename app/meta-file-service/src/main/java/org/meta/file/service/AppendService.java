package org.meta.file.service;
import org.download.manager.FileTobeDownloadedInfo;
import org.download.manager.DownloadProgress;
public interface AppendService {

	public boolean append(FileTobeDownloadedInfo fileTobeDownloadeInfo, DownloadProgress downloadProgress, long workerThreads);
}
