package br.com.guigasgame.interpolator;

import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class InterpolatorFromTime implements UpdatableFromTime
{
	protected float current;
	protected float destiny;

	public InterpolatorFromTime(float current)
	{
		this.current = current;
		this.destiny = current;
	}
	
	public final float getCurrent()
	{
		return current;
	}
	
	public final float getDestiny()
	{
		return destiny;
	}
	
	public InterpolatorFromTime interpolateTo(float destiny)
	{
		this.destiny = destiny;
		return this;
	}
	
	public abstract boolean hasFinished();
}
