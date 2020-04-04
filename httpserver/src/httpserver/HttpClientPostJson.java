package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientPostJson
{
	public static void main(String[] args)
	{
		try
		{
			whenPostJsonUsingHttpClient_thenCorrect();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void whenPostJsonUsingHttpClient_thenCorrect() throws ClientProtocolException, IOException
	{
		CloseableHttpClient client = HttpClients.createDefault();
		//HttpPost httpPost = new HttpPost("http://localhost:8080");
		HttpPost httpPost = new HttpPost("http://rtb.traff.co/api/25");
		// String json = "{\"id\":1,\"name\":\"John\"}";
		//
		String json = "";
		List<String> lines = Files.readAllLines(Paths.get("request.txt"), StandardCharsets.UTF_8);
		for (String line : lines)
		{
			json += line;
		}
		//
		StringEntity entity = new StringEntity(json);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		CloseableHttpResponse response = client.execute(httpPost);
		// assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		if (response.getStatusLine().getStatusCode() == 204)
		{
			System.out.println(response.getStatusLine().getReasonPhrase());
			
			//InputStreamReader input = new InputStreamReader(response.getEntity().getContent());
			if (response.getEntity() != null)
			{
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuilder stringBuilder = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null)
				{
					stringBuilder.append(line);
				}
				System.out.println(stringBuilder.toString());
			}
		}
		client.close();
	}
}
