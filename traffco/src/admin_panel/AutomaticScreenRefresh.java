package admin_panel;

import java.io.IOException;

public class AutomaticScreenRefresh implements Runnable
{
	private MainPanel inspector;

	public AutomaticScreenRefresh(MainPanel inspector)
	{
		this.inspector = inspector;
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(inspector.jobNow.getTimeSleep());
				if (inspector.jobNow.getSkip() == 0)
					inspector.jobNow.getJob().redraw();
				else if (inspector.jobNow.getJob().isCompleted() == 0)
				{
					inspector.jobNow.getJob().redraw();
					inspector.jobNow.getJob().setCompleted(inspector.jobNow.getSkip());
				} else
					inspector.jobNow.getJob().reduceCompleted();
			} catch (InterruptedException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}