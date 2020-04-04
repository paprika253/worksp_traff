package server.flexoffers;

import java.util.Scanner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpGetExample
{
	public static void main(String args[]) throws Exception
	{
		// Creating a HttpClient object
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// Creating a HttpGet object
		HttpGet httpget = new HttpGet("https://www.mail.ru/ ");
		// Printing the method used
		System.out.println("Request Type: " + httpget.getMethod());
		// Executing the Get request
		HttpResponse httpresponse = httpclient.execute(httpget);
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(httpresponse.getEntity().getContent());
		// Printing the status line
		System.out.println(httpresponse.getStatusLine());
		while (sc.hasNext())
		{
			System.out.println(sc.nextLine());
		}
	}
}