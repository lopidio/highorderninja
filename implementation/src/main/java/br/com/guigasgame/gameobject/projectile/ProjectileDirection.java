package br.com.guigasgame.gameobject.projectile;


public enum ProjectileDirection
{
	NONE(0, 0),
	UP(0, -1),
	UP_LEFT(-1, -1),
	UP_RIGHT(1, -1),
	LEFT(-1, 0),
	RIGHT(1, 0),
	DOWN(0, 1),
	DOWN_LEFT(-1, 1),
	DOWN_RIGHT(1, 1);
	
	private int x;
	private int y;
	private ProjectileDirection(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
}