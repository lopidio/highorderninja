package br.com.guigasgame.collision;



public class CollidableCategory 
{
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
	
}
