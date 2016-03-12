package br.com.guigasgame.math;


public class FloatRect
{
	public float left;
	public float top;
	public float width;
	public float height;

	public FloatRect()
	{
		left = 0;
		top = 0;
		width = 0;
		height = 0;
	}

	public FloatRect(float left, float top, float width, float height)
	{
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

}
