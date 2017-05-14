package org.common.utils;
import java.net.MalformedURLException;
import java.net.URL;
public class UrlUtils {

	private String urlString;
	private URL urlObject;
	
	public UrlUtils(String url) throws MalformedURLException{
		this.urlString = url;
		this.urlObject = new URL(url);
		
	}
	
	public String getProtocol(){
		return this.urlObject.getProtocol();
	}
	
	public String getHost(){
		return this.urlObject.getHost();
	}
	
	public String getPath(){
		return this.urlObject.getPath();
	}
	
	public String getReqestedFile(){
		return this.urlObject.getPath().substring(this.urlObject.getPath().lastIndexOf("/")+1);
	}
}
