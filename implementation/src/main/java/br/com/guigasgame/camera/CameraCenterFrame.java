package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.math.FloatRect;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class CameraCenterFrame implements UpdatableFromTime
{
	private List<Body> bodiesToControl;
	private Vector2f center;
	private FloatRect frame;

	public CameraCenterFrame() 
	{
		bodiesToControl = new ArrayList<>();
		center = new Vector2f(0, 0);
		frame = new FloatRect();
	}
	
	public void update(float deltaTime)
	{
		if (bodiesToControl.size() <= 0)
			return;

		updateBoundaries();
		updateCenter();
	}

	private void updateBoundaries()
	{
		Vector2f first = WorldConstants.physicsToSfmlCoordinates(bodiesToControl.get(0).getWorldCenter());
		frame.left = first.x;
		frame.top = first.y;
		frame.width = 0;
		frame.height = 0;
	
		for( int i = 1; i < bodiesToControl.size(); ++i)
		{
			Vector2f point = WorldConstants.physicsToSfmlCoordinates(bodiesToControl.get(i).getWorldCenter());

			if (point.x > frame.width + frame.left)
			{
				frame.width = point.x - frame.left;
			}
			else if (point.x < frame.left)
			{
				frame.width += frame.left - point.x;
				frame.left = point.x;
			}

			if (point.y > frame.height + frame.top)
			{
				frame.height = point.y - frame.top;
			}
			else if (point.y < frame.top)
			{
				frame.height += frame.top - point.y;
				frame.top = point.y;
			}
		}
	}

	private void updateCenter()
	{
		center = new Vector2f(frame.left + frame.width/2, frame.top + frame.height/2);
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
		return new Vector2f(frame.width, frame.height);
	}
	
	public Vector2f getOrigin()
	{
		return new Vector2f(frame.left, frame.top);
	}
	
	public Vector2f getCenter() 
	{
		return center;
	}
	
}
