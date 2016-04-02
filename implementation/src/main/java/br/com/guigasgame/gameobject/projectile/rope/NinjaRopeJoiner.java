package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.math.FloatSignalChangeWatcher;
import br.com.guigasgame.math.TriangleAreaCalculator;

class NinjaRopeJoiner
{
	private boolean markedToReunite;
	private FloatSignalChangeWatcher triangleAreaSignalWatcher;
	private TriangleAreaCalculator triangleArea;
	private boolean prevSameSign;
	
	public NinjaRopeJoiner(Vec2 prevCollidableHookPosition, Vec2 hookPoint, Vec2 heroPosition)
	{
		triangleArea = new TriangleAreaCalculator(prevCollidableHookPosition.clone(), hookPoint.clone(), heroPosition.clone());
		triangleAreaSignalWatcher = new FloatSignalChangeWatcher(triangleArea.getArea());
	}
	
	public void checkReunion(Vec2 heroPosition)
	{
		checkTriangleArea(heroPosition);
	}

	private void checkTriangleArea(Vec2 heroPosition)
	{
		triangleArea.setC(heroPosition.clone());
		float area = triangleArea.getArea();
		boolean currentSameSign = triangleAreaSignalWatcher.hasTheSameSign(area);
				
		if (prevSameSign && !currentSameSign)
		{
			markedToReunite = true;
		}
		prevSameSign = currentSameSign;
	}

	public boolean isMarkedToReunite()
	{
		return markedToReunite;
	}

}
