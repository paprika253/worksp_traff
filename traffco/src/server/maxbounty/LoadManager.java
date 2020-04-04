package server.maxbounty;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import utilites.DbMySql;
import utilites.Set;

public class LoadManager
{
	private DbMySql	db;
	private String	php_path;

	public LoadManager() throws IOException, InterruptedException, SQLException
	{
		PreparedStatement ps;
		PreparedStatement ps1;
		PreparedStatement ps2;
		ResultSet rs;
		int i;
		db = new DbMySql("traff", "localhost", "root", "b*bfXqAvDqVoZ0Yx");
		Set set = new Set(db.conn);
		Log log = new Log(db.conn);
		log.put(1, "Download API Information from Maxbounty - started");
		php_path = set.get("php_path");
		RunTime rt = new RunTime("php " + php_path + "load_cl.php");
		System.out.println(rt.err());
		System.out.println(rt.out());
		log.put(1, "Download Compaign Creatives - started");
		// очистка таблиц
		ps = db.conn.prepareStatement("TRUNCATE TABLE tmp_creatives");
		ps.execute();
		ps = db.conn.prepareStatement("TRUNCATE TABLE err_offerid");
		ps.execute();
		//
		System.out.println("OFFER_ID:");
		Vector<String> voferid = new Vector<String>();
		i = 0;
		ps = db.conn.prepareStatement("SELECT OFFER_ID FROM tmp_list");
		rs = ps.executeQuery();
		while (rs.next())
		{
			voferid.add(rs.getInt("OFFER_ID") + "");
			if (i > 19)
			{
				start_php_cc("load_cc.php", voferid);
				voferid.clear();
				i = 0;
			}
			i++;
		}
		start_php_cc("load_cc.php", voferid);
		log.put(1, "End of creatives upload.");
		// download Compaign Info
		log.put(1, "Download Compaign Info - started");
		ps = db.conn.prepareStatement("TRUNCATE TABLE tmp_Info");
		ps.execute();
		System.out.println("OFFER_ID:");
		voferid = new Vector<String>();
		i = 0;
		ps = db.conn.prepareStatement("SELECT OFFER_ID FROM tmp_list");
		rs = ps.executeQuery();
		while (rs.next())
		{
			voferid.add(rs.getInt("OFFER_ID") + "");
			if (i > 19)
			{
				start_php_cc("load_ci.php", voferid);
				voferid.clear();
				i = 0;
			}
			i++;
		}
		start_php_cc("load_ci.php", voferid);
		log.put(1, "End of Info upload.");
		//
		ps = db.conn.prepareStatement("SELECT OFFER_ID FROM err_offerid");
		rs = ps.executeQuery();
		i = 0;
		ps1 = db.conn.prepareStatement("DELETE FROM tmp_list WHERE OFFER_ID = ?");
		ps2 = db.conn.prepareStatement("DELETE FROM tmp_Info WHERE OFFER_ID = ?");
		while (rs.next())
		{
			ps1.setInt(1, rs.getInt("OFFER_ID"));
			ps1.execute();
			ps2.setInt(1, rs.getInt("OFFER_ID"));
			ps2.execute();
			i++;
		}
		log.put(0, i + " ad campaigns removed due to lack of creatives.");
		// перекачка изображений
		log.put(1, "Image upload and URL replacement started.");
		ps1 = db.conn.prepareStatement("DELETE FROM tmp_creatives WHERE OFFER_ID = ? AND CREATIVE_ID = ?");
		ps2 = db.conn.prepareStatement("UPDATE tmp_creatives SET CODE = ? WHERE OFFER_ID = ? AND CREATIVE_ID = ?");
		String img_path = set.get("img_path");
		String url_img = set.get("url_img");
		ps = db.conn.prepareStatement("SELECT OFFER_ID, CREATIVE_ID, CODE, CREATIVE_TYPE FROM tmp_creatives");
		rs = ps.executeQuery();
		i = 0;
		int j = 0;
		while (rs.next())
		{
			String CREATIVE_TYPE = rs.getString("CREATIVE_TYPE");
			if (!CREATIVE_TYPE.equals("Banner"))
				continue;
			int OFFER_ID = rs.getInt("OFFER_ID");
			int CREATIVE_ID = rs.getInt("CREATIVE_ID");
			String new_html_link = SaveImg.save(OFFER_ID, CREATIVE_ID, rs.getString("CODE"), img_path, url_img);
			if (new_html_link == null)
			{
				ps1.setInt(1, OFFER_ID);
				ps1.setInt(2, CREATIVE_ID);
				ps1.execute();
				i++;
				continue;
			} else if (new_html_link.equals("continue"))
				continue;
			new_html_link = new_html_link.replaceAll("&amp;", "&");
			ps2.setString(1, new_html_link);
			ps2.setInt(2, OFFER_ID);
			ps2.setInt(3, CREATIVE_ID);
			ps2.execute();
			j++;
			System.out.print(".");
			if (j % 100 == 0)
				System.out.println();
		}
		System.out.println();
		log.put(1, "Image upload has ended. Fixed " + j + " links.");
		if (i > 0)
			log.put(0, i + " creatives deleted.");
		ps = db.conn.prepareStatement("RENAME TABLE campaign_Info TO backup_table,"
				+ " tmp_Info TO campaign_Info, backup_table TO tmp_Info;");
		ps.execute();
		ps = db.conn.prepareStatement("RENAME TABLE campaign_list TO backup_table,"
				+ " tmp_list TO campaign_list, backup_table TO tmp_list;");
		ps.execute();
		ps = db.conn.prepareStatement("RENAME TABLE compaign_creatives TO backup_table,"
				+ " tmp_creatives TO compaign_creatives, backup_table TO tmp_creatives;");
		ps.execute();
		log.put(1, "Renaming tables - happened");
		log.put(1, "Download API Information from Maxbounty - ended.");
	}

	private void start_php_cc(String php__script, Vector<String> voferid) throws IOException, InterruptedException
	{
		String par = "";
		for (int i = 0; i < voferid.size(); i++)
			par += " " + voferid.get(i).toString();
		String cmd = "php " + php_path + php__script + " " + par;
		System.out.print(par);
		RunTime rt = new RunTime(cmd);
		System.out.println(rt.err());
		System.out.println(rt.out());
		Thread.sleep(100);
	}

	public static void main(String[] args)
	{
		try
		{
			new LoadManager();
		} catch (IOException | InterruptedException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}
