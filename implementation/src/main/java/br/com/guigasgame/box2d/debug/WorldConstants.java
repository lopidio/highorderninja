package br.com.guigasgame.box2d.debug;

import org.jbox2d.common.Vec2;
import org.jsfml.system.Vector2f;


public class WorldConstants
{

	public static final float SCALE = 30;

	public static Vector2f physicsToSfmlCoordinates(Vec2 box2dVector)
	{
		return new Vector2f(box2dVector.x * SCALE, box2dVector.y * SCALE);
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

}
