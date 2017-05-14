package org.common.utils;

import java.net.MalformedURLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class UrlUtilsTest 
    extends TestCase
{
    public void testUrlUtilForProtocolHTTP() throws MalformedURLException{
    	UrlUtils urlUritls = new UrlUtils("https://www.diljitpr.net/test.html?q=1");
    	assertEquals("https",urlUritls.getProtocol());
    	assertEquals("www.diljitpr.net",urlUritls.getHost());
    	assertEquals("test.html",urlUritls.getReqestedFile());
    }
    
}