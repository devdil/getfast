package org.download.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class DownloadThread implements  Runnable{
	
	private DownloadProgress downloadProgress;
	private String threadName;
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
	private long MAX_CHUNK_SIZE;
	private CloseableHttpClient httpClient;
	private HttpGet httpGet;
	private FileTobeDownloadedInfo fileTobeDownloadedInfo;
	private int workerThreads;
	private long startSegment;
	private long endSegment;
	
	DownloadThread(String threadNumber, DownloadProgress donwloadProgress,FileTobeDownloadedInfo fileTobeDownloadedInfo, PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,CloseableHttpClient httpClient,long maxChunkSize, int workerThreads, long startSegment, long endSegment){
		this.threadName = String.valueOf(threadNumber);
		this.downloadProgress = donwloadProgress;	
		this.fileTobeDownloadedInfo = fileTobeDownloadedInfo;
		this.poolingHttpClientConnectionManager = poolingHttpClientConnectionManager;
		this.httpClient = httpClient;
		this.MAX_CHUNK_SIZE = maxChunkSize;
		this.workerThreads = workerThreads;
		this.startSegment = startSegment;
		this.endSegment = endSegment;
		
	}
	
	public void run(){
	
		long eachThreadDownloads = this.fileTobeDownloadedInfo.getFileSize()/this.workerThreads;
		long threadNumber = Long.valueOf(this.threadName);
		int  offset = threadNumber > 1 ? 1: 0;
		long howMuchIDownloadStart = this.startSegment;
		long howMuchIDownloadEnd = this.endSegment;
		DownloadProgress downloadProgress = this.downloadProgress;
		
		String fileExtension = FilenameUtils.getExtension(this.fileTobeDownloadedInfo.getFileName());
		String fileName = FilenameUtils.getBaseName(this.fileTobeDownloadedInfo.getFileName());
		String partialDownloadedFileName =  fileName+".temp"+threadNumber;
		
		//System.out.println("Each thread "+ threadNumber+" downloads "+eachThreadDownloads);
		//System.out.println("From thread"+ threadNumber+" start "+ howMuchIDownloadStart);
		//System.out.println("From thread"+ threadNumber+" end "+ howMuchIDownloadEnd);
		File partialDownloadedFile = new File (partialDownloadedFileName);
		FileOutputStream partialDownloadedFileOutputStream = null;
		try {
			partialDownloadedFileOutputStream = new FileOutputStream(partialDownloadedFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long maxChunkSize = (howMuchIDownloadStart+this.MAX_CHUNK_SIZE) > howMuchIDownloadEnd ? (howMuchIDownloadEnd-howMuchIDownloadStart) : this.MAX_CHUNK_SIZE;
		
		while (howMuchIDownloadEnd - howMuchIDownloadStart > 0) {
			
			// do the range request
			String bytesRequestForRangeHeader = "bytes="+howMuchIDownloadStart+"-"+(howMuchIDownloadStart+maxChunkSize);
			//System.out.println("Requesting from thread"+threadNumber+" for "+bytesRequestForRangeHeader);
			HttpUriRequest uriRequest = RequestBuilder.get().
					setUri(fileTobeDownloadedInfo.getRemotUrl())
					.setHeader(HttpHeaders.RANGE, bytesRequestForRangeHeader)
					.build();
			InputStream responseStream;
			long contentLengthInBytesReceived;
			try {
			CloseableHttpResponse httpResponse = this.httpClient.execute(uriRequest);
			responseStream = httpResponse.getEntity().getContent();
			//System.out.println("Content Length"+ httpResponse.getEntity().getContentLength());
			contentLengthInBytesReceived = httpResponse.getEntity().getContentLength(); 
			}
			catch(ClientProtocolException clientProtocolException) {
				clientProtocolException.printStackTrace();
				return;
			}
			catch (IOException ioexception) {
				ioexception.printStackTrace();
				return;
			}
			
			try {
				  IOUtils.copy(responseStream, partialDownloadedFileOutputStream);
				  //System.out.println("From thread "+threadNumber+"writing complete");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			try {
				responseStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			//set the progress on downloadProgress object
			//System.out.println("Updating progress");
			//System.out.println("Bytes received   from thread "+threadNumber+" : "+contentLengthInBytesReceived);
			downloadProgress.setProgress(contentLengthInBytesReceived);
			//System.out.println("bytes till now from thread "+threadNumber+ " : "+downloadProgress.getProgress());
			//System.out.println("From thread "+threadNumber+" downloaded till now"+downloadProgress.getProgress());
			// we've have finished writing
			// lets close the outputStream
			howMuchIDownloadStart = howMuchIDownloadEnd+1;
	
			if (howMuchIDownloadStart+maxChunkSize > howMuchIDownloadEnd) {
				//calculate the new chunk size and update howMuchDownloadStart
				maxChunkSize = howMuchIDownloadEnd - (howMuchIDownloadStart);
				//System.out.println("New chunk size "+maxChunkSize);
			}
		
		
		//System.out.println("new start "+howMuchIDownloadStart);	
		}
		
		try {
			partialDownloadedFileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		
		
	}

	
	public String getName() {
		return this.threadName;
	}
}
