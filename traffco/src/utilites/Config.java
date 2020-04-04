package utilites;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yaa
 * 
 */
public class Config
{
	private List<String[]>	paramList	= new LinkedList<String[]>();
	private String			filename;

	public Config(String filename) throws IOException
	{
		this.filename = filename;
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		while ((s = in.readLine()) != null)
		{
			s = s.trim();
			if (s == null || s.equals(""))
				continue;
			String[] ar = s.split("=");
			if (ar.length > 0)
			{
				for (int i = 0; i < ar.length; i++)
					ar[i] = ar[i].trim();
				paramList.add(ar);
			}
		}
		in.close();
	}

	public String get(String arg)
	{
		for (int i = 0; i < paramList.size(); i++)
		{
			String[] ar = paramList.get(i);
			if (ar[0].equals(arg) && ar.length > 1)
				return ar[1];
		}
		return null;
	}

	public int size()
	{
		return paramList.size();
	}

	public String getKey(int i)
	{
		return paramList.get(i)[0];
	}

	public String get(int i)
	{
		String[] p = paramList.get(i);
		if (p.length > 1)
			return p[1];
		return null;
	}

	public void add(String key, String val)
	{
		for (int i = 0; i < paramList.size(); i++)
		{
			String[] ar = paramList.get(i);
			if (ar[0].equals(key))
			{
				paramList.set(i, new String[]
				{ key, val });
				return;
			}
		}
		paramList.add(new String[]
		{ key.trim(), val.trim() });
	}

	public void save() throws IOException
	{
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
		String es = "";
		for (int i = 0; i < paramList.size(); i++)
		{
			String[] ar = paramList.get(i);
			String line = ar[0];
			if (ar.length > 1)
				for (int j = 1; j < ar.length; j++)
					line += " = " + ar[j];
			out.write(es + line);
			es = "\n";
		}
		out.close();
	}
/*
	public static void main(String[] args)
	{
		try
		{
			Config c = new Config("config.ini");
			System.out.println(c.get("host"));
			c.add("день", "среда");
			c.add("aaa", "bbbbb");
			c.save();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}
