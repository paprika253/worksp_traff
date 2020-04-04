package admin_panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;

public class ActListCmdServer implements ActionListener
{
	private MainPanel	main;
	private String		cmd;

	public ActListCmdServer(MainPanel main, String cmd)
	{
		this.main = main;
		this.cmd = cmd;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e)
	{
		int i = JOptionPane.showConfirmDialog(main, "<html>" + cmd + "<br><br>Are you sure?", "Command on server",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (i == JOptionPane.YES_OPTION)
		{
			JSONObject send = new JSONObject();
			send.put("action", "cmd");
			send.put("cmd", cmd);
			// main.sendToServer(send);
			main.httpClient.send(send);
		}
	}
}
