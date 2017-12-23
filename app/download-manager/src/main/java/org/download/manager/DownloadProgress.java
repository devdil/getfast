package org.download.manager;

public class DownloadProgress {
private int progress = 0;
private boolean isComplete = false;

public void setProgress(int progress) {
	this.progress += progress;
}

public int getProgress() {
	return this.progress;
}

public boolean getProgressComplete() {
	return this.isComplete;
}

}
