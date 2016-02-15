package br.com.guigasgame.collision;


public class CollidersFilters
{
	///What I am
	public final static CollidersFilters playersCategory	= new CollidersFilters(0x0F);
	public final static CollidersFilters sceneryCategory 	= new CollidersFilters(0x0020);
	public final static CollidersFilters projectileCategory = new CollidersFilters(0x0040);
	
	///What I collide with
	public final static CollidersFilters playersCollideWith 		= sceneryCategory.add(playersCategory).add(projectileCategory);
	public final static CollidersFilters projectilesCollideWith 	= sceneryCategory.add(playersCategory).add(projectileCategory);
	public final static CollidersFilters sceneriesCollideWith 		= new CollidersFilters(-1); //Everything
	
	
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
