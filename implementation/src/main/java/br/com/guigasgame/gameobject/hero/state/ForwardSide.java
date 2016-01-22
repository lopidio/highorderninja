package br.com.guigasgame.gameobject.hero.state;

public class ForwardSide
{

	public enum Side
	{
		LEFT, RIGHT;
	}

	private Side side;

	public ForwardSide(Side value)
	{
		this.side = value;
	}

	public Side getSide()
	{
		return side;
	}

	public void flip()
	{
		switch (side)
		{
		case LEFT:
			side = Side.RIGHT;
			break;
		case RIGHT:
			side = Side.LEFT;
			break;
		default:
		}
	}

}
