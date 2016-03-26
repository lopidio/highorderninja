package br.com.guigasgame.interpolator;

import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class InterpolatorFromTime<T> implements UpdatableFromTime
{
	protected T current;
	protected T destiny;
	protected float duration;

	public InterpolatorFromTime(T current, float duration)
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
	
	public final T getCurrent()
	{
		return current;
	}
	
	public final T getDestiny()
	{
		return destiny;
	}
	
	public InterpolatorFromTime<T> interpolateTo(T destiny)
	{
		this.destiny = destiny;
		return this;
	}
	
	public abstract boolean hasFinished();
}
