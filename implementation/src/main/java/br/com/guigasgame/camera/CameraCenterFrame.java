package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.math.FloatRect;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class CameraCenterFrame implements UpdatableFromTime
{
	private List<CameraFollowable> objectsToFollow;
	private Vector2f center;
	private FloatRect frame;

	public CameraCenterFrame(Vector2f center) 
	{
		objectsToFollow = new ArrayList<>();
		this.center = center;
		frame = new FloatRect();
	}
	
	public void update(float deltaTime)
	{
		if (objectsToFollow.size() <= 0)
			return;

		updateBoundaries();
		updateCenter();
	}

	private void updateBoundaries()
	{
		for( int i = 0; i < objectsToFollow.size(); ++i)
		{
			if (objectsToFollow.get(i).isTrackeable())
			{
				Vector2f point = WorldConstants.physicsToSfmlCoordinates(objectsToFollow.get(i).getPosition());
				if (i==0)
				{
					frame.left = point.x;
					frame.top = point.y;
					frame.width = 0;
					frame.height = 0;
					continue;
				}
				
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
	}

	private void updateCenter()
	{
		center = new Vector2f(frame.left + frame.width/2, frame.top + frame.height/2);
	}
	
	public void addObject(CameraFollowable followable)
	{
		objectsToFollow.add(followable);
	}

	public void removeObject(CameraFollowable followable)
	{
		objectsToFollow.remove(followable);
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
