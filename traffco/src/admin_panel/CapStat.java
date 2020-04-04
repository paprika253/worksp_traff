package admin_panel;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CapStat extends IspectorTab
{
	private int		last_id;
	private boolean	rew_flag	= false;

	public CapStat(JTabbedPane tp, MainPanel inspector)
	{
		super(tp, inspector);
		last_id = 0;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -526246469398303197L;

	@Override
	public String getTitle()
	{
		return "Event log";
	}

	@Override
	public String[] getHeader()
	{
		return new String[]
		{ "OFFER_ID", "CREATIVE_ID", "time", "action", "ip", "country", "country<br>flag", "region", "city", "org",
				"User-Agent", "Referer" };
	}

	@Override
	public String getAction()
	{
		return "pack_stat";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addSend(JSONObject send)
	{
		send.put("last_id", last_id);
	}

	@Override
	public void redr(JSONObject jo, GridBagConstraints c)
	{
		c.ipadx = 0;
		c.ipady = 0;
		c.weightx = 0.0;
		c.weighty = 0.0;
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
					last_id = Integer.parseInt(row.get(12).toString());
			}
			add(new JLabel(row.get(0).toString()), c);
			c.gridx++;
			add(new JLabel(row.get(1).toString()), c);
			c.gridx++;
			String tmp = transference(row.get(2).toString(), "<br>", 9);
			add(new JLabel("<html>" + tmp), c);
			c.gridx++;
			add(new JLabel(row.get(3).toString()), c);
			c.gridx++;
			add(new JLabel(row.get(4).toString()), c);
			c.gridx++;
			add(new JLabel(row.get(5).toString()), c);
			c.gridx++;
			try
			{
				add(new JLabel("<html><img src=\"https://creative.traff.co/flags/" + row.get(6).toString().toLowerCase()
						+ ".png\" width=\"31\" height=\"20\">"), c);
			} catch (NullPointerException e1)
			{
			}
			c.gridx++;
			tmp = transference(row.get(7).toString(), "<br>", 6);
			add(new JLabel("<html>" + tmp), c);
			c.gridx++;
			add(new JLabel(row.get(8).toString()), c);
			c.gridx++;
			tmp = transference(row.get(9).toString(), "<br>", 6);
			add(new JLabel("<html>" + tmp), c);
			c.gridx++;
			tmp = transference(row.get(10).toString(), "<br>", 30);
			add(new JLabel("<html>" + tmp), c);
			c.gridx++;
			add(new JLabel(row.get(11).toString()), c);
			c.gridx++;
			c.gridx = 0;
			c.gridy++;
			// System.out.println();
		}
	}

	@Override
	public void toolBarRedr(JToolBar tb)
	{
		// TODO Auto-generated method stub
	}

	
}