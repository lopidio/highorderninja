package br.com.guigasgame.collision;


public class CollidersFilters
{
	///WHO I AM
	public final static short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
	public final static short CATEGORY_MONSTER = 0x0002; // 0000000000000010 in binary
	public final static short CATEGORY_SCENERY = 0x0004; // 0000000000000100 in binary
	
	///WHAT I COLLIDE WITH
	public final static short MASK_PLAYER = CATEGORY_MONSTER | CATEGORY_SCENERY; // or ~CATEGORY_PLAYER
	public final static short MASK_MONSTER = CATEGORY_PLAYER | CATEGORY_SCENERY; // or ~CATEGORY_MONSTER
	public final static short MASK_SCENERY = -1; //also equals to 0xFFFF	
}
