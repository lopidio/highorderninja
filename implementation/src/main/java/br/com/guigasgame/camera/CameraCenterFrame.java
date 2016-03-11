package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;

public class CameraCenterFrame 
{
	private List<Body> bodiesToControl;
	private float biggestY;
	private float biggestX;
	private float smallestY;
	private float smallestX;

	public CameraCenterFrame() 
	{
		bodiesToControl = new ArrayList<>();
	}
	
	private void update()
	{
		if (bodiesToControl.size() <= 0)
			return;
		Vector2f first = WorldConstants.physicsToSfmlCoordinates(bodiesToControl.get(0).getWorldCenter());
		smallestX = first.x;
		smallestY = first.y;

		biggestX = smallestX;
		biggestY = smallestY;
	
		for( int i = 1; i < bodiesToControl.size(); ++i)
		{
			Vector2f point = WorldConstants.physicsToSfmlCoordinates(bodiesToControl.get(i).getWorldCenter());

			if (point.x > biggestX)
				biggestX = point.x;
			else if (point.x < smallestX)
				smallestX = point.x;

			if (point.y > biggestY)
				biggestY = point.y;
			else if (point.y < smallestY)
				smallestY = point.y;
		}
	}
	
	public void addBody(Body body)
	{
		bodiesToControl.add(body);
	}

	public void removeBody(Body body)
	{
		bodiesToControl.remove(body);
	}
	
	public Vector2f getSize()
	{
		return new Vector2f(biggestX - smallestX, biggestY - smallestY);
	}
	
	public Vector2f getOrigin()
	{
		return new Vector2f(smallestX, smallestY);
	}
	
	public Vector2f getCenter() 
	{
		update();
		return new Vector2f(smallestX + biggestX/2, smallestY + biggestY/2);
	}
	
	
}
