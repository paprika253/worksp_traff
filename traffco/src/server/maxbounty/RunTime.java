package server.maxbounty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunTime
{
	private StringBuffer	output;
	private StringBuffer	output_err;
	private int				exitStatus;

	public RunTime(String c) throws IOException, InterruptedException
	{
		output = new StringBuffer();
		output_err = new StringBuffer();
		Process p;
		p = Runtime.getRuntime().exec(c);
		p.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF8"));
		String line = "";
		while ((line = reader.readLine()) != null)
			output.append(line + "\n");
		reader.close();
		reader = new BufferedReader(new InputStreamReader(p.getErrorStream(), "UTF8"));
		line = "";
		while ((line = reader.readLine()) != null)
			output_err.append(line + "\n");
		reader.close();
		exitStatus = p.exitValue();
	}

	public String out()
	{
		return output.toString();
	}

	public int chek()
	{
		return exitStatus;
	}

	public String err()
	{
		return output_err.toString();
	}
	/*
	public static void main(String[] args)
	{
		try
		{
			RunTime r = new RunTime("ls");
			System.out.println(r.out());
			System.out.println(r.err());
		} catch (IOException | InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}