package admin_panel;

import java.awt.Color;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import drawchrt.DrawChart;

public class Draw extends DrawChart
{
	JSONObject					send;
	private MainPanel			main;
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@SuppressWarnings("unchecked")
	public Draw(MainPanel main, int lines, int height)
	{
		super(main, lines, height);
		this.main = main;
		send = new JSONObject();
		send.put("action", "graph");
		// System.out.println("send = " + send);
	}

	@Override
	protected Color getColor(int i)
	{
		return (new Color[]
		{ Color.BLUE, Color.RED })[i];
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Integer getData(int i)
	{
		try
		{
			JSONObject curent = send;
			curent.put("line", i);
			String otvet = main.httpClient.send(curent);
			JSONObject jo = (JSONObject) JSONValue.parseWithException(otvet);
			String action = jo.get("action").toString();
			if (!action.equals("graph"))
				return 0;
			int status = Integer.parseInt(jo.get("status").toString());
			if (status == 0)
				return 0;
			return Integer.parseInt(jo.get("val").toString());
		} catch (Exception e)
		{
		}
		return 0;
	}

	@Override
	protected String getTitle(int i)
	{
		return (new String[]
		{ "open", "click" })[i];
	}

	@Override
	protected String getUnit()
	{
		return "clicks";
	}
}
