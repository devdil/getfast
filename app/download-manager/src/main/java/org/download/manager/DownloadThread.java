package org.download.manager;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class DownloadThread implements  Runnable{
	
	private DownloadProgress downloadProgress;
	private String threadName;
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
	private int MAX_CHUNK_SIZE;
	private CloseableHttpClient httpClient;
	
	
	DownloadThread(String threadNumber, DownloadProgress donwloadProgress,PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,CloseableHttpClient httpClient, int maxChunkSize){
		this.threadName = String.valueOf(threadNumber);
		this.poolingHttpClientConnectionManager = poolingHttpClientConnectionManager;
		this.httpClient = httpClient;
		this.downloadProgress = donwloadProgress;
		this.MAX_CHUNK_SIZE = maxChunkSize;
	}
	
	public void run() {
		
		for(int i=1; i<=5;i++) {
			this.downloadProgress.setProgress(i);
			System.out.println(this.threadName+": progress "+this.downloadProgress.getProgress());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public String getName() {
		return this.threadName;
	}
}
