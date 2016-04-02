package br.com.guigasgame.raycast;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

public class RayCastCallBackWrapper
{
	public Fixture fixture;
	public Vec2 point;
	public Vec2 normal;
	public float fraction;
	public RayCastCallBackWrapper(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		this.fixture = fixture;
		this.point = point.clone();
		this.normal = normal.clone();
		this.fraction = fraction;
	}
	
}
