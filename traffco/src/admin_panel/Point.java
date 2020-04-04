package admin_panel;

public class Point
{

	private int i;
	private int j;

	public Point(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public static void main(String[] args)
	{
		Point  p1   = new Point ( 110, 200 );
		Point  p2   = new Point ( 10, 20 );
		double dist = p2.distance ( p1 );
		System.out.println ( "d = " + dist );

	}

	private double distance(Point p1)
	{
		double dist = Math.sqrt ( (i - p1.i) * (i - p1.i) + (j - p1.j) * (j - p1.j) );
		return dist;
	}

}
