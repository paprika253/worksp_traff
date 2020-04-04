package utilites;

/***********************************************
 * 
 * @author yaa 24.03.2017
 * Соединение с базой данных
 * 
 ***********************************************/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbMySql
{
	public Connection conn;

	public DbMySql(String db_name, String host, String user_name, String password)
	{
		conn = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db_name + "?" + "user=" + user_name
					+ "&password=" + password + "&useSSL=false&useUnicode=true&characterEncoding=utf-8");
		} catch (SQLException ex)
		{
			// handle any errors
			
			  System.err.println("SQLException: " + ex.getMessage());
			  System.err.println("SQLState: " + ex.getSQLState());
			  System.err.println("VendorError: " + ex.getErrorCode());
			 
			  System.err.println("No connection to the database");
			 
		}
	}
}
