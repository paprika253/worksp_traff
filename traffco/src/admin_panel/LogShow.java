package admin_panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

public class LogShow implements Runnable
{
	private MainPanel	main;
	@SuppressWarnings("unused")
	private int			last_id;
	@SuppressWarnings("unused")
	private List<BasicNameValuePair>	pairs;

	public LogShow(MainPanel mainPanel)
	{
		this.main = mainPanel;
		this.last_id = 0;
		
	}

	@Override
	public void run()
	{
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("action", "log"));
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("httpbin.org", 80),
				new UsernamePasswordCredentials("user", "passwd"));
		CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		HttpPost post = new HttpPost(main.server_url);
		try
		{
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			if ((line = rd.readLine()) != null)
			{
				System.out.println(line);
			}
		}
		 catch (IOException e)
		{
			
			e.printStackTrace();
		}
	}
}