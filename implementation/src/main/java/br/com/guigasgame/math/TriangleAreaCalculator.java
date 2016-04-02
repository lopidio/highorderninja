package br.com.guigasgame.math;

import org.jbox2d.common.Vec2;

public class TriangleAreaCalculator
{
	private Vec2 a;
	private Vec2 b;
	private Vec2 c;
	public TriangleAreaCalculator(Vec2 a, Vec2 b, Vec2 c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public TriangleAreaCalculator()
	{
	}
	
	public Vec2 getA()
	{
		return a;
	}
	
	public void setA(Vec2 a)
	{
		this.a = a;
	}
	
	public Vec2 getB()
	{
		return b;
	}
	
	public void setB(Vec2 b)
	{
		this.b = b;
	}
	
	public Vec2 getC()
	{
		return c;
	}
	
	public void setC(Vec2 c)
	{
		this.c = c;
	}
	
	public float getArea()
	{
		final float first	= a.x*(b.y-c.y);
		final float second	= b.x*(c.y-a.y);
		final float third	= c.x*(a.y-b.y);
		return 0.5f*(first + second + third);
	}
	
	public float getAbsoluteArea()
	{
		return Math.abs(getArea());
	}
}
