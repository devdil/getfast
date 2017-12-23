package org.download.manager;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class HttpDownloader  extends DownloaderModel implements Downloader {

	private static int MAX_CHUNK_SIZE = 1024;
	private DownloadProgress downloadProgress;
	
	public HttpDownloader(CommandLineArg commandLineArg) {
		super(commandLineArg);
		this.downloadProgress = new DownloadProgress();
	}
	
	public void download() {
		DownloadProgress downloadProgress = this.downloadProgress;
		PoolingHttpClientConnectionManager httpConnectionManager = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().build();
		
		for (int i=1; i<=this.commandLineArg.getThreads(); i++) {
			String workerName = "WORKER"+i;
			DownloadThread downloadThreadRunnable = new DownloadThread(workerName, downloadProgress,httpConnectionManager, httpClient, HttpDownloader.MAX_CHUNK_SIZE);
			Thread downloadThread= new Thread(downloadThreadRunnable, downloadThreadRunnable.getName());
			downloadThread.start();
		}
		
		return ;
	}
	
	public void gatherFileInformationFromHost(CommandLineArg commandLineArg) {
		// send a head request
	}
	
	public void displayProgress() {
		
		// get the file size	
		
		
	}
	
	
	public static void main(String args[]) {
	
	}
}
