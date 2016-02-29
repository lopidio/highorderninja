package br.com.guigasgame.side;

public enum Side
{
	LEFT(-1) {
		@Override
		public Side opposite()
		{
			return RIGHT;
		}
	},
	RIGHT(1) {
		@Override
		public Side opposite()
		{
			return LEFT;
		}
	}, 
	
	UNKNOWN(0) {
		@Override
		public Side opposite()
		{
			return Side.UNKNOWN;
		}
	};
	
	private int horizontalValue;
	Side(int horizontalValue)
	{
		this.horizontalValue = horizontalValue;
	}

	public int getHorizontalValue()
	{
		return horizontalValue;
	}
	public abstract Side opposite();
	
	public static Side fromHorizontalValue(int x)
	{
		if (x > 0)
			return RIGHT;
		return Side.LEFT;
	}
}
