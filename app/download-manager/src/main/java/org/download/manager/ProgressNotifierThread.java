package org.download.manager;
import org.console.ui.*;

public class ProgressNotifierThread implements Runnable {
	
	private DownloadProgress downloadProgress;
	private static String DOWNLOAD_BARS_THEME_TYPE = "#";
	private static int BARS_USED = 50;
	
	public ProgressNotifierThread(DownloadProgress downloadProgress) {
		this.downloadProgress = downloadProgress;
	}
	
	public void run() {
		DownloadProgress downloadProgress = this.downloadProgress;
		long lastRead = 0;
		long newRead = 0;
		long barsTillNow = 0;
		long maxBars = 50;
		System.out.print("[");
		while (downloadProgress.getProgressComplete() == false) {
			long progress = downloadProgress.getProgress();
			if( progress % 2 == 0) { 
				// since the downloadProgress update can be random we may miss some of the updates and
				// hence need to calculate the number of even numbers tha we have jumped so far
				// once we have that value, print that number of times and keep a counter to track that
				long barsToBePrinted = (progress/2)-barsTillNow;
				for (long i=1; i<= barsToBePrinted;i++) {
					System.out.print("#");
					barsTillNow += 1;	
				}				

			}
			long barsRemaining = maxBars - barsTillNow;
			for(int i=1; i<= barsRemaining; i++){
				System.out.print(" ");
			}
			System.out.print("] ");
			System.out.print(downloadProgress.getProgress()+"%");
			for(int i=1;i<=(barsRemaining+2+1+String.valueOf(downloadProgress.getProgress()).length());i++)
				System.out.print("\b");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (progress == 100) {
				System.out.print("] ");
				System.out.print(downloadProgress.getProgress()+"%");
				downloadProgress.setEndTimeInMillisecs(System.currentTimeMillis());
				System.out.println();
				System.out.println("Finished in "+((downloadProgress.getEndTimeInMillisecs()-downloadProgress.getStartTimeInMillisecs())/1000+" secs"));			
				break;
			}
				
		}
	}
		
		
	}

