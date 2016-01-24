package br.com.guigasgame.side;

public enum Side
{
	LEFT,
	RIGHT;

	public Side opposite()
	{
		if (this == LEFT)
			return RIGHT;
		else // if (this == RIGHT)
			return LEFT;
	}
}
