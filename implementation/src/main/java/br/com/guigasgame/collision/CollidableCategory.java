package br.com.guigasgame.collision;



public class CollidableCategory 
{
	private static int categoriesUsed = 0; 
	private final static int NUM_MAX_PLAYERS = 4;

	private final IntegerMask mask;

	CollidableCategory(int maskValue) {
		this.mask = new IntegerMask(maskValue);
	}

	public CollidableCategory matching(int value) 
	{
		return new CollidableCategory(mask.and(value).value);
	}

	public int getValue() 
	{
		return mask.value;
	}
	
	public static CollidableCategory getAllPlayerCategory()
	{
		int playersMask = 1;
		for (int i = 0; i < NUM_MAX_PLAYERS ; ++i) 
		{
			playersMask = (playersMask << 1) + 1;
		}
		return new CollidableCategory(playersMask);
	}
	
	public static CollidableCategory getNextCategory()
	{
		return new CollidableCategory(++categoriesUsed);
	}
	
}
