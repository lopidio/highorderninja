package br.com.guigasgame.interpolator;

import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class InterpolatorFromTime implements UpdatableFromTime
{
	protected float current;
	protected float destiny;
	protected float duration;

	public InterpolatorFromTime(float current, float duration)
	{
		this.current = current;
		this.destiny = current;
		setDuration(duration);
	}
	
	public final void setDuration(float duration)
	{
		if (duration <= 0)
		{
			duration = 0.0001f;
		}
		this.duration = duration;
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
