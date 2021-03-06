package geometryClasses;

import java.util.ArrayList;
import java.util.Random;

public class Geode extends Shape
{
	public ArrayList<ThreePoint> theShell;
	public Geode(double a, double b, double c, double h)
	{
		int d = (int)(a);
		int e = (int) (b);
		int f = (int) (c);
		int g = (int)(h);
		if(d<e && d<f)
		{
			if(d<g)
				g=1;
		}
		else if(e<d && e<f)
		{
			if(e<g)
				g=1;
		}
		else
		{
			if(f<g)
				g=1;
		}
		int dx = d-g;
		int dy = e-g;
		int dz = f-g;
		
		Ellipsoid shell = new Ellipsoid(d,e,f);
		Ellipsoid pocket = new Ellipsoid(dx,dy,dz);
		Random rand = new Random();
		int x = rand.nextInt(360);
		int y = rand.nextInt(360);
		int z = rand.nextInt(360);
		shell.rotateX(x);shell.rotateY(y); shell.rotateZ(z);
		pocket.rotateX(x);shell.rotateY(y); shell.rotateZ(z);
		theShell = new ArrayList<ThreePoint>();
		createPointList(shell,pocket);
		this.points = pocket.points;
	}
	
	private void createPointList(Ellipsoid shell, Ellipsoid pocket) 
	{
		Boolean bool = true;
		for(int i=0;i<shell.points.length;i++)
		{
			for(int j=0;j<pocket.points.length;j++)
			{
				if(shell.points[i].equals(pocket.points[j]))
				{
					bool = false;
				}
			}
			if(bool)
			{
				this.theShell.add(shell.points[i]);
			}
			else
			{
				bool = true;
			}
		}
	}
}
