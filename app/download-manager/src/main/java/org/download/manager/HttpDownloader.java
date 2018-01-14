package org.download.manager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.commons.io.FilenameUtils;

public class HttpDownloader extends DownloaderModel implements Downloader {

	private static int DEFAULT_MAX_CHUNK_SIZE = 3145728 ; // TODO : come up with a good algorithm to calculate chunk size. this could pretty interestiong.
	private DownloadProgress downloadProgress;
	private static PoolingHttpClientConnectionManager httpConnectionManager;
	private static CloseableHttpClient httpClient;

	public HttpDownloader(CommandLineArg commandLineArg) {
		super(commandLineArg);
		this.downloadProgress = new DownloadProgress();
		this.httpConnectionManager = new PoolingHttpClientConnectionManager();
		this.httpConnectionManager.setMaxTotal(100);
		this.httpClient = HttpClients.custom().setConnectionManager(httpConnectionManager).build();
		
	}

	public void download() {
		
		FileTobeDownloadedInfo fileTobeDownloadedInfo = null;
		System.out.println("Contacting host...");
		try {
			 fileTobeDownloadedInfo = gatherFileInformationFromHost(this.commandLineArg);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch(IOException io) {
			io.printStackTrace();
		}
		
		if (fileTobeDownloadedInfo != null) {
		
			// if the file can be downloaded parallely, we initilize workerthreads = number of threads set by user
			// else we initialize to 1 ... make sense right?
		
			System.out.println("Gathered resource details : ");
			long fileSize = fileTobeDownloadedInfo.getFileSize();
						
			int workerThreads = (fileTobeDownloadedInfo.getCanBeDownloadeParallel() == true) ? commandLineArg.getThreads() : 1; 			
			long eachThreadDownloadsSize = fileSize / workerThreads;
			long MAX_CHUNK_SIZE = eachThreadDownloadsSize < DEFAULT_MAX_CHUNK_SIZE ? eachThreadDownloadsSize : DEFAULT_MAX_CHUNK_SIZE;
			
			
			
			System.out.println(" Filename :  "+ fileTobeDownloadedInfo.getFileName());
			System.out.println(" Filesize :  "+ fileTobeDownloadedInfo.getFileSize());
			
			//set the download location of the file
			fileTobeDownloadedInfo.setDestination(commandLineArg.getDownloadLocation());
			fileTobeDownloadedInfo.setRemoteUrl(commandLineArg.getUrl());
			DownloadProgress downloadProgress = this.downloadProgress;
			
			downloadProgress.setTotalBytesToBeDownloaded(fileSize-1);
			
			CloseableHttpClient httpClientForThread;
			
			Thread[] downloadThreads= new Thread[workerThreads];
			
			long startSize = 0, endSize = eachThreadDownloadsSize-1;
			for (int i = 0; i < workerThreads; i++) {
				
				String workerName = (i+1)+"";
				httpClientForThread = HttpClients.custom().setConnectionManager(httpConnectionManager).build();
				DownloadThread downloadThreadRunnable = new DownloadThread(workerName, downloadProgress,fileTobeDownloadedInfo,
						httpConnectionManager, httpClientForThread, MAX_CHUNK_SIZE-1, workerThreads, startSize, endSize);
				Thread downloadThread = new Thread(downloadThreadRunnable, downloadThreadRunnable.getName());
				downloadThreads[i] = downloadThread; 
				
				//System.out.println("end size" + endSize);
				startSize = endSize + 1;
				endSize = (startSize+eachThreadDownloadsSize-1 > fileSize-1) ? fileSize-1 : (startSize + (eachThreadDownloadsSize-1));
			}
		
			System.out.println("");
			System.out.println("Downloading file...");
			downloadProgress.setStartTimeInMillisecs(System.currentTimeMillis());
			for (int i=0; i< commandLineArg.getThreads(); i++) {
				downloadThreads[i].start();
			}
			
			ProgressNotifierThread progressNotifierRunnable = new ProgressNotifierThread(downloadProgress, fileTobeDownloadedInfo, workerThreads);	
			Thread progressNotifierThread = new Thread(progressNotifierRunnable);
			progressNotifierThread.start(); 
			
			
			for (int i=0; i< commandLineArg.getThreads(); i++) {
				try {
					downloadThreads[i].join();	
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
		


		return;
	}

	private FileTobeDownloadedInfo gatherFileInformationFromHost(CommandLineArg commandLineArg) throws IOException, URISyntaxException  {
		// send a head request
		CloseableHttpClient httpClient = this.httpClient;
		String uri = commandLineArg.getUrl();
		HttpHead httpHead = null;
		CloseableHttpResponse httpResponse;
		FileTobeDownloadedInfo fileToBeDownloaded = null;
		try {
			httpHead = new HttpHead(uri);	
			}
		catch(IllegalArgumentException e) {
			System.out.println("URI is invalid");
			e.printStackTrace();
			throw e;
		}
		try {
			if (httpHead != null) {
				httpResponse = httpClient.execute(httpHead);
				System.out.println("Http Response "+httpResponse.getStatusLine().getStatusCode());
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					// everything went fine
					// extract other headers like filesize acceptranges and media type
					fileToBeDownloaded = new FileTobeDownloadedInfo();
					Header[] headers = httpResponse.getAllHeaders();
					boolean canBeDownloadedParallely = false;
					for (Header header: headers) {
						if (header.getName().equalsIgnoreCase(HttpHeaders.ACCEPT_RANGES)) {
							if (!header.getValue().contains("none")) {
								fileToBeDownloaded.setCanbeDownloadedParallel(true);
								canBeDownloadedParallely = true;	
							}
							else {
								
								canBeDownloadedParallely = false;
								fileToBeDownloaded.setCanbeDownloadedParallel(false);
							}
								
						}
						if (header.getName().equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)) {
							fileToBeDownloaded.setFileSize(Long.valueOf(header.getValue()));
							URI url;
							try {
							url = new URI(uri);
							}
							catch(URISyntaxException e) {
								throw e;
							}
							fileToBeDownloaded.setFileName(FilenameUtils.getName(url.getPath()));
						}
					}
					
					if (!canBeDownloadedParallely) {
						fileToBeDownloaded.setCanbeDownloadedParallel(false);
					}
				}
			}
		}
			
	catch (ClientProtocolException e) {
		throw e;
	}
	catch (IOException e) {
			throw e;
	}
		
		return fileToBeDownloaded;
	}

	public void displayProgress() {

		// get the file size

	}

	public static void main(String args[]) {

	}
}
