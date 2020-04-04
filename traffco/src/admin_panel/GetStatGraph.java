package admin_panel;

public class GetStatGraph implements Runnable
{
	private DGraph dg;

	public GetStatGraph(DGraph dg)
	{
		this.dg = dg;
	}

	@Override
	public void run()
	{
		while (true)
		{
			dg.addTrack(0, (int) (Math.random() * 10));
			dg.addTrack(1, (int) (Math.random() * 10));
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
