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
}
