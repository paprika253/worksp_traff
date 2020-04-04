package server.maxbounty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Log
{
	private Connection conn;

	public Log(Connection conn)
	{
		this.conn = conn;
	}

	public void put(int flag, String msg) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("INSERT INTO traffco_log (flag, msg) VALUES (?,?)");
		ps.setInt(1, flag);
		ps.setString(2, msg);
		ps.execute();
		Calendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
		System.out.println(date + " \t" + msg);
	}
}
