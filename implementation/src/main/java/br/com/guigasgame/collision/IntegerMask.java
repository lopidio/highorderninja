package br.com.guigasgame.collision;


public class IntegerMask 
{
	
	public final int value;
	
	public IntegerMask(int maskValue)
	{
		this.value = maskValue;
	}
	
	public IntegerMask()
	{
		this.value = 0;
	}
	
	public IntegerMask set(int value) 
	{
		return new IntegerMask(this.value | value);
	}
	
	public IntegerMask setAll() 
	{
		return new IntegerMask(-1);
	}
	
	public IntegerMask clear(int value)
	{
		return new IntegerMask(this.value & ~value);
	}
	
	public IntegerMask and(int value)
	{
		return new IntegerMask(this.value & value);
	}
	
	public boolean matches(int value)
	{
		return this.and(value).value > 0;
	}
	
}
