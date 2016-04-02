package br.com.guigasgame.math;


public class FloatVariationWatcher
{
	private final float initial;
	private float current;
	private boolean prevSmaller;
	private boolean prevGreater;
	
	public FloatVariationWatcher(float initial)
	{
		this.initial = initial;
		this.current = initial;
	}
	
	public void updateValue(float value)
	{
		prevSmaller = isSmallerThanInitial();
		prevGreater = isGreaterThanInitial();
		current = value;
	}

	public boolean isSmallerThanInitial()
	{
		return current < initial;
	}
	
	public boolean notSmallerAnymoreInstant()
	{
		return prevSmaller && !isSmallerThanInitial();
	}
	
	public boolean isGreaterThanInitial()
	{
		return current > initial;
	}

	public boolean notGreaterAnymoreInstant()
	{
		return prevGreater && !isGreaterThanInitial();
	}
	
}
