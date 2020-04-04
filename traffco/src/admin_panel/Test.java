package admin_panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Test
{
	public static void main(String[] args) throws ClientProtocolException, IOException
	{
		CredentialsProvider credsProvider = new BasicCredentialsProvider ();
		credsProvider.setCredentials ( new AuthScope ( "httpbin.org", 80 ),
				new UsernamePasswordCredentials ( "user", "passwd" ) );

		CloseableHttpClient      client         = HttpClients.custom ().setDefaultCredentialsProvider ( credsProvider )
				.build ();
		HttpPost                 post           = new HttpPost ( "http://62.75.148.27/yaa/index.php" );
		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair> ();
		nameValuePairs.add ( new BasicNameValuePair ( "name", "value" ) );
		nameValuePairs.add ( new BasicNameValuePair ( "familia", "yanchuk" ) );
		post.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
		HttpResponse   response = client.execute ( post );
		BufferedReader rd       = new BufferedReader ( new InputStreamReader ( response.getEntity ().getContent () ) );
		String         line     = "";
		while ((line = rd.readLine ()) != null)
			{
				System.out.println ( line );
			}
		
	}
}