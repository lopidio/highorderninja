package br.com.guigasgame.interpolator;

import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class Interpolator implements UpdatableFromTime
{
	protected float current;
	
	public Interpolator(float current)
	{
		this.current = current;
	}
	
	public final float getCurrent()
	{
		return current;
	}
	
	public abstract boolean hasFinished();

}
