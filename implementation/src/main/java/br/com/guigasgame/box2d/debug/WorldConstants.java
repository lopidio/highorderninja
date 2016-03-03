package br.com.guigasgame.box2d.debug;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jsfml.system.Vector2f;


public class WorldConstants
{

	public static final float SCALE = 20;
	public static final float MOVING_TOLERANCE = 0.05f;

	public static Vector2f physicsToSfmlCoordinates(Vec2 box2dVector)
	{
		return new Vector2f(box2dVector.x * SCALE, box2dVector.y * SCALE);
	}

	public static List<Vector2f> physicsToSfmlCoordinates(List<Vec2> box2dVector)
	{
		List<Vector2f> retorno = new ArrayList<Vector2f>();
		for (Vec2 vec2 : box2dVector) 
		{
			retorno.add(physicsToSfmlCoordinates(vec2));
		}
		
		return retorno;
	}

	public static Vec2 sfmlToPhysicsCoordinates(Vector2f sfmlVector)
	{
		return new Vec2(sfmlVector.x / SCALE, sfmlVector.y / SCALE);
	}

	public static double radiansToDegrees(float radians)
	{
		return radians * 180.0 / Math.PI;
	}

	public static double degreesToRadians(float degrees)
	{
		return degrees * Math.PI / 180.0;
	}
	
	public static double getAngleBetweenVectors(Vec2 a, Vec2 b)
	{
		float dotProduct = Vec2.dot(a, b);
		return Math.acos(dotProduct / (a.length()*b.length()));
	}

}
