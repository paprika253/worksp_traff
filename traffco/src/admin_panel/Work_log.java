package admin_panel;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Work_log extends IspectorTab
{
	private int		last_id;
	private boolean	rew_flag	= false;

	public Work_log(JTabbedPane tp, MainPanel inspector)
	{
		super(tp, inspector);
		last_id = 0;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2432896296684835737L;

	@Override
	public void redr(JSONObject jo, GridBagConstraints c)
	{
		JSONArray rows = (JSONArray) jo.get("row");
		for (int i = 0; i < rows.size(); i++)
		{
			JSONArray row = (JSONArray) rows.get(i);
			if (i == 0)
			{
				if (rew_flag)
				{
					rew_flag = false;
					last_id = 0;
				} else
					last_id = Integer.parseInt(row.get(3).toString());
			}
			add(new JLabel(row.get(0).toString()), c);
			c.gridx++;
			int flag = Integer.parseInt(row.get(1).toString());
			String html = "<html><p>";
			if (flag == 0)
				html += "<font color=\"red\">";
			add(new JLabel(html + row.get(2).toString()), c);
			c.gridy++;
			c.gridx = 0;
			// System.out.println();
		}
	}

	@Override
	public String getTitle()
	{
		return "Work log";
	}

	@Override
	public String[] getHeader()
	{
		return new String[]
		{ "date_time", "msg" };
	}

	@Override
	public String getAction()
	{
		return "get_log";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addSend(JSONObject send)
	{
		send.put("last_id", last_id);
	}

	@Override
	public void toolBarRedr(JToolBar tb)
	{
		// TODO Auto-generated method stub
	}

	
}
