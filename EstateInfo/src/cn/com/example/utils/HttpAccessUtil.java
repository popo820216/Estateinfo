package cn.com.example.utils;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpAccessUtil {
	public static String ip = "http://api.besthouses.cn/api/";
	public static String end = "&imei=";
	
	public static String connServerForResult(String url){
		HttpGet httpRequest = new HttpGet(url);
		
		String result = null;
		HttpClient httpClient;
		HttpResponse httpResponse;
		try{
			httpClient = new DefaultHttpClient();
			httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpResponse = null;
		}
		return result;
	}
	
	public static String post(String url, List<NameValuePair> paramsMap) {
		HttpPost httpRequest = new HttpPost(url);
		String result = null;
		try{
			HttpEntity entity = new UrlEncodedFormEntity(paramsMap, "UTF-8");
			httpRequest.setEntity(entity);
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}
