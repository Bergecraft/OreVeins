package oreClasses;

import geometryClasses.Ellipse;
import geometryClasses.Line;
import geometryClasses.ThreePoint;

import java.util.ArrayList;

import com.icloud.kevinmendoza.OreVeins.DebugLogger;
import com.icloud.kevinmendoza.OreVeins.PointMapping;

public class SedimentHostedDepositSystem extends OreSuper 
{
	private int strike;
	private int width;
	private int height;
	private int grade;
	private int levels;
	private ArrayList<ThreePoint> levelOffset;
	public SedimentHostedDepositSystem(ThreePoint start, String ore)
	{
		this.ore = ore;
		this.start = start;
		initializeDefaults();
		getPerpVector();
		if(Line.distance(start, end) > 2)
		{
			this.end = Line.getEndPoint(start, strike, rand, false);
			makeSystem();
		}
	}
	
	private void getPerpVector()
	{
		//returned a point that is perpendicular to the current point. offset both startpoint and endpoint
		//by start
		ThreePoint base = new ThreePoint(0,0,0);
		end = Line.getEndPoint(base, strike*2, rand,false);
		levelOffset = Line.bresenHamAlgo(base,end);
		if(end.x==0)
			end.x =1;
		if(end.z==0)
			end.z=1;
		if(end.y==0)
			end.y=1;
		levelOffset = Line.bresenHamAlgo(base,end);
	}
	
	private void makeSystem()
	{
		nodes = Line.bezierCurve(start, end, rand);
		int max = levelOffset.size();
		ThreePoint offset;
		Ellipse newEllipse = new Ellipse(height,width);
		newEllipse.rotateRandom(rand);
		newEllipse.alighnToPoints(start,end);
		crossSection = newEllipse.points;
		//DebugLogger.console(":"+levels+ "max:"+max +"strike:"+ strike);
		for(int randindex=0;randindex<max;randindex+=max/levels)
		{
			offset = levelOffset.get(randindex);
			//DebugLogger.console(offset.toString());
			addSection(offset);
			PointMapping.addArrayToPoints(centers, this.ore);
			drawPoints();
		}
		
	}
	
	protected void initializeDefaults()
	{
		if(ore.contains("BIF"))
		{
			strike = (int) bif.strike.getRVar(rand);
			width = (int) bif.width.getRVar(rand);
			height = (int) bif.height.getRVar(rand);
			grade = (int) (100/(bif.grade.getRVar(rand)));
			levels = (int) bif.levels.getRVar(rand);
			ore = "IRON";
		}
		else if(ore.contains("COAL"))
		{
			strike = (int) coal.strike.getRVar(rand);
			width = (int) coal.width.getRVar(rand);
			height = (int) coal.height.getRVar(rand);
			grade = (int) (100/(coal.grade.getRVar(rand)));
			levels = (int) coal.levels.getRVar(rand);
		}
		if(strike<50 && levels > 3)
		{
			levels =3;
		}
		if(strike>75 && levels > 5)
			levels=4;
	}

	@Override
	protected void addSection(ThreePoint centerPoint) 
	{
		ThreePoint tempPoint;
		int length = crossSection.length;
		int temp;
		int breaking;
		ArrayList<Integer> chosen = new ArrayList<Integer>();
		for(int i=0;i<nodes.size();i++)
		{
			//DebugLogger.console(nodes.get(i).toString());
			for(int j=0;j<length/grade;j++)
			{
				breaking=0;
				tempPoint = new ThreePoint(centerPoint);
				while(true)
				{
					temp = rand.nextInt(length);
					if(!chosen.contains(temp))
					{
						chosen.add(temp);
						break;
					}
					if(breaking>length*1.5)
						break;
					breaking++;
				}
				tempPoint.offSet(crossSection[temp]);
				tempPoint.offSet(nodes.get(i));
				if(tempPoint.y>2 && tempPoint.y<128)
					centers.add(tempPoint);
			}
			chosen.clear();
		}
	}

	@Override
	protected void makeBonanza(ThreePoint center) {
		// TODO Auto-generated method stub
		
	}


}
