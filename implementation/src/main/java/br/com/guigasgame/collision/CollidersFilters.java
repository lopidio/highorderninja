package br.com.guigasgame.collision;


public class CollidersFilters
{
	///WHO I AM
	public final static short CATEGORY_PLAYER_ONE 	= 0x0001;
	public final static short CATEGORY_PLAYER_TWO 	= 0x0002;
	public final static short CATEGORY_PLAYER_THREE	= 0x0004;
	public final static short CATEGORY_PLAYER_FOUR 	= 0x0008;
	public final static short CATEGORY_PLAYER_MASK 	= CATEGORY_PLAYER_ONE | CATEGORY_PLAYER_TWO | CATEGORY_PLAYER_THREE | CATEGORY_PLAYER_FOUR;

	public final static short CATEGORY_BULLET 		= 0x0020;
	public final static short CATEGORY_SCENERY 		= 0x0040;
	
	///WHAT I COLLIDE WITH
	public final static short MASK_PLAYER = CATEGORY_SCENERY | CATEGORY_PLAYER_MASK | CATEGORY_BULLET; // or ~CATEGORY_PLAYER
	public final static short MASK_BULLET = CATEGORY_PLAYER_MASK | CATEGORY_SCENERY; // or ~CATEGORY_MONSTER
	public final static short MASK_BULLET_AIMER = CATEGORY_SCENERY; // or ~CATEGORY_MONSTER
	public final static short MASK_ROPE = CATEGORY_SCENERY; // or ~CATEGORY_MONSTER
	public final static short MASK_SCENERY = -1; //also equals to 0xFFFF

	///What I am
	public final static CollidersFilters playersCategory	= new CollidersFilters(0x0F);
	public final static CollidersFilters sceneryCategory 	= new CollidersFilters(0x0020);
	public final static CollidersFilters projectileCategory = new CollidersFilters(0x0040);
	
	///What I collide with
	public final static CollidersFilters playersCollideWith 		= sceneryCategory.add(playersCategory); // or ~CATEGORY_PLAYER
	public final static CollidersFilters projectilesCollideWith 	= sceneryCategory.add(playersCategory).add(projectileCategory); // or ~CATEGORY_MONSTER
	public final static CollidersFilters sceneriesCollideWith 		= new CollidersFilters(-1); //also equals to 0xFFFF
	
	
	private final int maskValue;
	
	
	public CollidersFilters(int maskValue)
	{
		this.maskValue = maskValue;
	}

	public static CollidersFilters getPlayerCategory(int playerID)
	{
		return playersCategory.matching(new CollidersFilters(playerID));
	}
	
	public CollidersFilters add(CollidersFilters mask)
	{
		return new CollidersFilters(maskValue | mask.maskValue);
	}
	
	public CollidersFilters except(CollidersFilters mask)
	{
		return new CollidersFilters(maskValue & ~mask.maskValue);
	}
	
	public CollidersFilters matching(CollidersFilters mask)
	{
		return new CollidersFilters(maskValue & mask.maskValue);
	}
	
	public boolean matches(CollidersFilters mask)
	{
		return this.matching(mask).value() > 0;
	}
	
	public int value()
	{
		return maskValue;
	}
}
