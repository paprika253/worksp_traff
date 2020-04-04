package utilits.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClient1
{
	public static void main(String[] args) throws ClientProtocolException, IOException 
	{
		 String url = "https://api.flexoffers.com/categories?apiKey=b510d50c-62dc-45f6-86b8-c707fa492b1b";

		    CloseableHttpClient client = HttpClientBuilder.create().build();
		    HttpGet request = new HttpGet(url);

		   //add request header
		    request.addHeader("User-Agent", "application/json");
		    HttpResponse response = client.execute(request);

		    System.out.println("Response Code : "
		                + response.getStatusLine().getStatusCode());

		    BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		    StringBuffer result = new StringBuffer();
		    String line = "";
		    while ((line = rd.readLine()) != null) {
		        result.append(line);
		    }
	}
}
