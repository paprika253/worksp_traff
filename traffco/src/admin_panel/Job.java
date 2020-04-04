package admin_panel;


public class Job
{
	private IspectorTab	job;
	private int			time_sleep;
	private int skip;
	

	public Job(IspectorTab job, int time, int skip)
	{
		this.job = job;
		this.time_sleep = time;
		this.skip = skip;
	}

	

	@Override
	public String toString()
	{
		return "Job [job=" + job + ", time_sleep=" + time_sleep + ", skip=" + skip + "]";
	}



	public IspectorTab getJob()
	{
		return job;
	}

	public long getTimeSleep()
	{
		return time_sleep;
	}

	public int getSkip()
	{
		return skip;
	}
}