package admin_panel;

/***********************************************
 * 
 * @author yaa 24.03.2017
 * вывод графика на JPanel
 * 
 ***********************************************/
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class DGraph extends JPanel implements Runnable
{
	private static final int TIME_ARRAY_SIZE = 100;

	class Line
	{
		private Color color;
		private Integer[] time_line;
		private String title;

		public Line()
		{
			this.color = Color.BLACK;
			this.time_line = new Integer[TIME_ARRAY_SIZE];
			for (int i = 0; i < TIME_ARRAY_SIZE; i++)
				this.time_line[i] = 0;
			this.setTitle("");
		}

		public String getTitle()
		{
			return title;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Line[] data;
	private String[] mm_ss;

	public DGraph(JFrame mainTest, Integer lines)
	{
		super();
		Dimension size = mainTest.getSize();
		setPreferredSize(new Dimension(size.width, size.width / 10));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.darkGray));
		data = new Line[lines];
		for (int i = 0; i < lines; i++)
			data[i] = new Line();
		this.mm_ss = new String[TIME_ARRAY_SIZE];
		for (int i = 0; i < this.mm_ss.length; i++)
			this.mm_ss[i] = new String();
	}

	protected void paintComponent(Graphics gr)
	{
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		Dimension size = getSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		int otstup_y = 20; // отступ снизу
		int otstup_x = 100;
		// Рисуем сетку
		g.setColor(Color.lightGray);
		for (int i = otstup_x; i < width; i = i + 10)
			g.drawLine(i, 0, i, height - otstup_y);
		for (int i = 10; i <= height - otstup_y; i = i + 10)
			g.drawLine(100, i, width, i);
		// находим максимальный траффик
		long max = getMax();
		// делаем надписи
		int x = 5;
		int y = 15;
		int dy = 10;
		g.setColor(Color.BLACK);
		Font font = new Font("Verdana", Font.CENTER_BASELINE, 10);
		g.setFont(font);
		g.drawString("max: "+max,x,y);// + Bytes.format((double) max) + "/s", x, y);
		for (int i = 0; i < data.length; i++) {
			y += dy;
			g.setColor(data[i].color);
			g.drawString(data[i].title + ": " +data[i].time_line[1],x,y);//+ Bytes.format((double) data[i].time_line[1]) + "/s", x, y);
		}
		// рисуем график
		g.setStroke(new BasicStroke(2));
		int dx = (int) ((width - otstup_x) / TIME_ARRAY_SIZE + 1);
		int x1, x2;
		int y2;
		int y1;
		for (int i = 0; i < data.length; i++) {
			g.setColor(data[i].color);
			x1 = 100;
			for (int j = 0; j < data[i].time_line.length - 1; j++) {
				y1 = (int) (height - data[i].time_line[j] * (height - otstup_y) / max - otstup_y);
				x2 = x1 + dx;
				y2 = (int) (height - data[i].time_line[j + 1] * (height - otstup_y) / max - otstup_y);
				g.drawLine(x1, y1, x2, y2);
				if (i == 0) {
					g.setColor(Color.BLACK);
					Font font1 = new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 9);
					g.setFont(font1);
					String t = mm_ss[j];
					g.drawString(t, x1 + 7, height - otstup_y + 12);
					g.setFont(font);
					g.setColor(data[i].color);
				}
				x1 = x2;
			}

		}
	}

	public long getMax()
	{
		long max = 1;
		for (int i = 0; i < data.length; i++)
			for (int j = 0; j < data[i].time_line.length - 1; j++)
				if (data[i].time_line[j] > max)
					max = data[i].time_line[j];
		return max;
	}

	@SuppressWarnings("deprecation")
	public void run()
	{
		try {
			while (true) {
				for (int i = 0; i < data.length; i++) {
					for (int j = data[i].time_line.length - 1; j > 0; j--)
						data[i].time_line[j] = data[i].time_line[j - 1];
					data[i].time_line[0] = 0;

				}
				for (int i = mm_ss.length - 1; i > 0; i--) {
					mm_ss[i] = mm_ss[i - 1];
					SimpleDateFormat sdfDate = new SimpleDateFormat("hh:mm:ss");
					Date dt = new Date();
					int ss = dt.getSeconds();
					if (ss % 10 == 0)
						mm_ss[0] = sdfDate.format(dt);
					else
						mm_ss[0] = "";
				}
				Thread.sleep(1000);
				repaint();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void addTrack(int i, Integer t1)
	{
		this.data[i].time_line[0] += t1;
	}

	public void setColor(int i, Color color, String title)
	{
		this.data[i].color = color;
		this.data[i].setTitle(title);
	}

	public String getSpeed()
	{
		String speed = "";
		for (int i = 0; i < data.length; i++) {
			speed += Bytes.format((double) data[i].time_line[0]) + "/sec;";
		}
		return speed;
	}
}