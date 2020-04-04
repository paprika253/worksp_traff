package admin_panel;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public abstract class IspectorTab extends JPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2668583222833874636L;
	private GridBagConstraints	c;
	public MainPanel			inspector;
	private Image				img;
	private int					completed			= 0;
	private boolean				rewrite_flag		= false;

	public IspectorTab(JTabbedPane tp, MainPanel inspector)
	{
		super();
		this.img = Toolkit.getDefaultToolkit().createImage(MainPanel.class.getResource("images/traff_fon.png"));
		this.inspector = inspector;
		tp.add(new JScrollPane(this), getTitle());
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 25, 1, 25);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}

	public abstract void redr(JSONObject jo, GridBagConstraints c) throws MalformedURLException, IOException;

	public abstract String getTitle();

	public abstract String[] getHeader();

	public abstract String getAction();

	public abstract void addSend(JSONObject send);

	public abstract void toolBarRedr(JToolBar tb);

	@SuppressWarnings("unchecked")
	public void redraw() throws MalformedURLException, IOException
	{
		JSONObject send = new JSONObject();
		String action = getAction();
		send.put("action", action);
		addSend(send);
		if (rewrite_flag)
		{
			send.put("last_id", 0);
			rewrite_flag = false;
		}
		String otvet = inspector.httpClient.send(send);
		//System.out.println(otvet);
		JSONObject jo = null;
		try
		{
			jo = (JSONObject) JSONValue.parseWithException(otvet);
			if (!jo.get("action").toString().equals(action))
				return;
			int status = Integer.parseInt(jo.get("status").toString());
			if (status == 0)
				return;
		} catch (ParseException | java.lang.NullPointerException e)
		{
			// e.printStackTrace();
			return;
		}
		removeAll();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = getHeader().length;
		add(new JLabel("<html><h2>" + getTitle()), c);
		c.gridwidth = 1;
		c.gridy++;
		for (String x : getHeader())
		{
			add(new JLabel("<html><b>" + x), c);
			c.gridx++;
		}
		c.gridx = 0;
		c.gridy++;
		redr(jo, c);
		revalidate();
		repaint();
	}

	public void toolBarRedraw(JToolBar tb)
	{
		toolBarRedr(tb);
		tb.revalidate();
		tb.repaint();
	}

	public int isCompleted()
	{
		return completed;
	}

	public void setCompleted(int skip)
	{
		completed = skip;
	}

	public void reduceCompleted()
	{
		completed--;
	}

	public static String transference(String text, String delimiter, int len)
	{
		String tmp = "";
		String t = "";
		String[] ar = text.split(" ");
		for (int i = 0; i < ar.length; i++)
		{
			if (t.length() + ar[i].length() < len)
				t += " " + ar[i];
			else
			{
				t += " " + ar[i];
				tmp += t + delimiter;
				t = "";
			}
		}
		tmp += t;
		return tmp;
	}

	public void rewrite()
	{
		this.rewrite_flag = true;
	}
}