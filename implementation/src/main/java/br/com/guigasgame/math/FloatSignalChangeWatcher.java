package br.com.guigasgame.math;


public class FloatSignalChangeWatcher
{
	private float firstSignal;
	
	public FloatSignalChangeWatcher(float firstSignal)
	{
		this.firstSignal = firstSignal;
	}
	
	public float getFirstSignal()
	{
		return firstSignal;
	}
	
	public boolean hasTheSameSign(float other)
	{
		if (firstSignal == 0)
			firstSignal = other;
		return other/firstSignal > 0;
	}
}
