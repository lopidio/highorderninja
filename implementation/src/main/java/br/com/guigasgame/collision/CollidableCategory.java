package br.com.guigasgame.collision;



public class CollidableCategory 
{
	///What I am
	public final static CollidableCategory herosCategory		= CollidableCategory.getAllPlayerCategory();
	public final static CollidableCategory sceneryCategory 		= CollidableCategory.getNextCategory();
	public final static CollidableCategory projectileCategory	= CollidableCategory.getNextCategory();
	
	public static CollidableCategory getPlayerCategory(int playerID)
	{
		return herosCategory.matching(1 << (playerID - 1));
	}
	
	public static CollidableCategory getOtherPlayersCategory(int playerID)
	{
		return herosCategory.matching(~(1 << (playerID - 1)));
	}

	private static int categoriesUsed = 0; 
	private final static int NUM_MAX_PLAYERS = 4;

	private final IntegerMask mask;

	CollidableCategory(int maskValue) 
	{
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
		for (int i = 0; i < NUM_MAX_PLAYERS - 1; ++i) 
		{
			playersMask = (playersMask << 1) + 1;
		}
		return new CollidableCategory(playersMask);
	}
	
	public static CollidableCategory getNextCategory()
	{
		return new CollidableCategory((++categoriesUsed) << NUM_MAX_PLAYERS );
	}
	
}
