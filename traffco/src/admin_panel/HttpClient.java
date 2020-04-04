package admin_panel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;

public class HttpClient
{
	private HttpPost			post;
	private CloseableHttpClient	client;
	JFrame						main	= null;
	private String				server_url;

	public HttpClient(String server_url)
	{
		this.server_url = server_url;
	}

	public HttpClient(String server_url, MainPanel main)
	{
		this.main = main;
		this.server_url = server_url;
	}

	public String send(String[][] par_array)
	{
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		for (int i = 0; i < par_array.length; i++)
			pairs.add(new BasicNameValuePair(par_array[i][0], par_array[i][1]));
		return send(pairs);
	}

	@SuppressWarnings("unchecked")
	public <T, U> String send(JSONObject send)
	{
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		send.forEach(new BiConsumer<T, U>()
		{
			@Override
			public void accept(T t, U u)
			{
				pairs.add(new BasicNameValuePair(t.toString(), u.toString()));
			}
		});
		return send(pairs);
	}

	private String send(List<BasicNameValuePair> pairs)
	{
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("httpbin.org", 80),
				new UsernamePasswordCredentials("user", "passwd"));
		client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		post = new HttpPost(server_url);
		try
		{
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String rl = "";
			String line;
			while ((line = rd.readLine()) != null)
				rl += line;
			return rl;
		} catch (Exception e)
		{
			String msg = "No connection to server";
			if (main != null)
				JOptionPane.showMessageDialog(main, msg, "Alert", JOptionPane.ERROR_MESSAGE);
			else
				System.err.println(msg);
			e.printStackTrace();
		}
		return null;
	}
}
