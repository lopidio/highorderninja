package br.com.guigasgame.collision;


public class CollidersFilters
{
	public enum CategoryMask
	{
		PLAYER_ONE(0x0001),
		PLAYER_TWO(0x0002),
		PLAYER_THREE(0x0004),
		PLAYER_FOUR(0x0008),
		
		BULLET(0x0020),
		SCENERY(0x0040),
		SINGLE_BLOCK(0x0080);

		
		int maskValue;

		private CategoryMask(int maskValue)
		{
			this.maskValue = maskValue;
		}

		
		public static int getAllPlayersMask()
		{
			return PLAYER_ONE.maskValue | PLAYER_TWO.maskValue | PLAYER_THREE.maskValue | PLAYER_FOUR.maskValue;
		}
		
	}
	///WHO I AM
	public final static short CATEGORY_PLAYER_ONE 	= 0x0001;
	public final static short CATEGORY_PLAYER_TWO 	= 0x0002;
	public final static short CATEGORY_PLAYER_THREE	= 0x0004;
	public final static short CATEGORY_PLAYER_FOUR 	= 0x0008;
	public final static short CATEGORY_PLAYER_MASK 	= CATEGORY_PLAYER_ONE | CATEGORY_PLAYER_TWO | CATEGORY_PLAYER_THREE | CATEGORY_PLAYER_FOUR;

	public final static short CATEGORY_BULLET 		= 0x0020;
	public final static short CATEGORY_SCENERY 		= 0x0040;
	public final static short CATEGORY_SINGLE_BLOCK = 0x0080;
	
	///WHAT I COLLIDE WITH
	public final static short MASK_PLAYER = CATEGORY_SCENERY | CATEGORY_PLAYER_MASK | CATEGORY_BULLET; // or ~CATEGORY_PLAYER
	public final static short MASK_BULLET = CATEGORY_PLAYER_MASK | CATEGORY_SCENERY | CATEGORY_SINGLE_BLOCK; // or ~CATEGORY_MONSTER
	public final static short MASK_ROPE = CATEGORY_SCENERY | CATEGORY_SINGLE_BLOCK; // or ~CATEGORY_MONSTER
	public final static short MASK_SCENERY = -1; //also equals to 0xFFFF
	
	private int maskValue;
	
	public static int getPlayerCategory(int playerID)
	{
		return 1 << (playerID - 1);
	}
	
	
	
	public CollidersFilters addMask(CollidersFilters mask)
	{
		maskValue |= mask.maskValue;
		return this;
	}
	
	public CollidersFilters removeMask(CollidersFilters mask)
	{
		maskValue &= ~mask.maskValue;
		return this;
	}
	
	public int getMaskValue()
	{
		return maskValue;
	}
}
