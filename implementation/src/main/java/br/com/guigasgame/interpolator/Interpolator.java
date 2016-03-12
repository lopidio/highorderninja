package br.com.guigasgame.interpolator;

import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class Interpolator implements UpdatableFromTime
{
	protected float current;
	protected float destiny;

	public Interpolator(float current, float destiny)
	{
		this.current = current;
		this.destiny = destiny;
	}
	
	public final float getCurrent()
	{
		return current;
	}
	
	public final float getDestiny()
	{
		return destiny;
	}
	
	public abstract boolean hasFinished();

}
