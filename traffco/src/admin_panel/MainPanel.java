package admin_panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utilites.Config;

public class MainPanel extends JFrame
{
	/**
	 * 
	 */
	private static final long		serialVersionUID	= -7134209143503104609L;
	private static final int		DEFAULT_HEIGHT		= 600;
	private static final int		DEFAULT_WIDTH		= 1024;
	String							server_url;
	private Config					cnf;
	private Vector<Job>				tabJob;
	private int						x					= 0;
	protected Job					jobNow;
	private AutomaticScreenRefresh	asr;
	String							path_server;
	HttpClient						httpClient;

	public MainPanel(String title) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, IOException
	{
		super(title);
		// config
		cnf = new Config("config.ini");
		server_url = cnf.get("url_server");
		path_server = cnf.get("path_server");
		httpClient = new HttpClient(server_url, this);
		// new Thread(httpClient).start();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setLocation((screenSize.width - DEFAULT_WIDTH) / 2, (screenSize.height - DEFAULT_HEIGHT) / 2);
		Image image = Toolkit.getDefaultToolkit().createImage(MainPanel.class.getResource("images/traff.png"));
		setIconImage(image);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		// Toolbar
		JToolBar tb = new JToolBar("Still draggable");
		tb.setRollover(true);
		JButton icl_batton = new JButton(new ImageIcon(MainPanel.class.getResource("images/smoke.png")));
		icl_batton.setToolTipText("Loading creatives");
		icl_batton.addActionListener(new ActListCmdServer(this, "java -jar " + path_server + "loadmng.jar"));
		tb.add(icl_batton);
		JButton rewrite_batton = new JButton(new ImageIcon(MainPanel.class.getResource("images/rewrite.png")));
		rewrite_batton.setToolTipText("Rewrite");
		tb.add(rewrite_batton);
		JButton exit_batton = new JButton(new ImageIcon(MainPanel.class.getResource("images/exit.png")));
		exit_batton.setToolTipText("Exit");
		exit_batton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(NORMAL);
			}
		});
		tb.add(exit_batton);
		contentPane.add(tb, BorderLayout.NORTH);
		// Center panel
		tabJob = new Vector<Job>();
		JTabbedPane tp = new JTabbedPane();
		addPanel(new Work_log(tp, this), 1000, 10);
		addPanel(new CapStat(tp, this), 800, 8);
		contentPane.add(tp, BorderLayout.CENTER);
		tp.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				x = tp.getSelectedIndex();
				jobNow = tabJob.get(x);
				// tb.removeAll();
				/*
				 * tb.add(exit_batton); tb.add(restart_batton); if
				 * (jobNow.getSkip() > 0) tb.add(rewrite_batton);
				 */
				try
				{
					// jobNow.getJob().toolBarRedraw(tb);
					jobNow.getJob().redraw();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		/*
		 * Запуск автоматического обновления экрана
		 */
		jobNow = tabJob.get(0);
		asr = new AutomaticScreenRefresh(this);
		new Thread(asr).start();
		// графика;
		Draw dg = new Draw(this, 2, 120);
		new Thread(dg).start();
		
		rewrite_batton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(Job j:tabJob)
				{
					j.getJob().rewrite();
				}
				
			}
		});
		// south panel
		JPanel s_panel = new JPanel();
		s_panel.setLayout(new BorderLayout());
		s_panel.add((Component) dg, BorderLayout.NORTH);
		s_panel.add(new JLabel(
				"<html><br> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; Copyright © 2020. Limited Liability "
						+ "Company <strong>\"Traff.co\"</strong>. All rights reserved.<br> &nbsp;"),
				BorderLayout.SOUTH);
		contentPane.add(s_panel, BorderLayout.SOUTH);
		// Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menu_file = new JMenu("File");
		JMenuItem m_exit = new JMenuItem("Exit");
		menu_file.add(m_exit);
		m_exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(NORMAL);
			}
		});
		menuBar.add(menu_file);
		JMenu menu_import = new JMenu("Import");
		JMenuItem m_import_campaignList = new JMenuItem("Loading creatives");
		m_import_campaignList.addActionListener(new ActListCmdServer(this, "java -jar " + path_server + "loadmng.jar"));
		menu_import.add(m_import_campaignList);
		menuBar.add(menu_import);
		setJMenuBar(menuBar);
		setVisible(true);
	}

	private void addPanel(IspectorTab job, int time, int skip)
	{
		tabJob.addElement(new Job(job, time, skip));
	}

	public static void main(String[] args)
	{
		try
		{
			new MainPanel("TraffCo Server Control Panel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException | IOException e)
		{
			e.printStackTrace();
		}
	}
}
