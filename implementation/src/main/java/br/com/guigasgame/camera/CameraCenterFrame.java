package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class CameraCenterFrame implements UpdatableFromTime
{
	private List<Body> bodiesToControl;
	private Vector2f center;
	private float biggestY;
	private float biggestX;
	private float smallestY;
	private float smallestX;

	public CameraCenterFrame() 
	{
		bodiesToControl = new ArrayList<>();
		center = new Vector2f(0, 0);
	}
	
	public void update(float deltaTime)
	{
		if (bodiesToControl.size() <= 0)
			return;

		updateBoundaries();
		calculateNewCenter();
	}

	private void updateBoundaries()
	{
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

	private void calculateNewCenter()
	{
		final Vector2f newCenter = new Vector2f(smallestX + biggestX/2, smallestY + biggestY/2);
		final Vector2f difference = Vector2f.sub(newCenter, center);
		final Vector2f interpolizer = Vector2f.mul(difference, 0.5f);
		center = Vector2f.add(center, interpolizer);
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
		return center;
	}
	
}
