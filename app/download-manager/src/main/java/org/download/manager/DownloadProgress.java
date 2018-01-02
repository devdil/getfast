package org.download.manager;

public class DownloadProgress {
private boolean isComplete = false;
private boolean terminateNotifer = false;
private volatile long bytesDownloadedTillNow;
private volatile long totalBytesToBeDownloaded;


public void setTotalBytesToBeDownloaded(long bytes) {
	this.totalBytesToBeDownloaded = bytes;
}

public long getTotalBytesToBeDownloaded(long totalSizeInBytes) {
	return this.totalBytesToBeDownloaded;
}

public long getProgress() {
	//sSystem.out.println("Till now" + bytesDownloadedTillNow+" Total "+totalBytesToBeDownloaded);
	double value = (bytesDownloadedTillNow * 100.0d/totalBytesToBeDownloaded);
	return (long)value;
}

public void setProgress(long bytesDownloaded) {
	this.bytesDownloadedTillNow += bytesDownloaded;
}

public boolean getProgressComplete() {
	return this.isComplete;
}

public void setProgressComplete(boolean isComplete) {
	this.isComplete = isComplete;
}

public boolean getTerminateNotifier() {
	return this.terminateNotifer;
}

public void setTerminateNotifier() {
	this.terminateNotifer = terminateNotifer;
}

}
