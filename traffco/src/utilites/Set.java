package utilites;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yaa запоминать и брать полезные вещи 19.06.2017
 *
 */
public class Set
{
	private Connection conn;

	public Set(Connection conn)
	{
		this.conn = conn;
	}

	@SuppressWarnings("resource") public void set(String name, String value) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("SELECT id FROM traffco_set WHERE name=? LIMIT 1");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			int id = rs.getInt("id");
			ps = conn.prepareStatement("UPDATE traffco_set SET value = ? WHERE id = ?");
			ps.setString(1, value);
			ps.setInt(2, id);
			ps.executeUpdate();
		} else
		{
			ps = conn.prepareStatement("TRUNCATE TABLE compaign_creatives");
			ps.setString(1, name);
			ps.setString(2, value);
			ps.execute();
		}
	}

	public String get(String name) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("SELECT value FROM traffco_set WHERE name=? LIMIT 1");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			return rs.getString("value");
		return null;
	}
}