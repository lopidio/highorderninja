package br.com.guigasgame.color;

import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class ColorInterpolator implements UpdatableFromTime
{
	protected ColorBlender sourceColor;
	protected float duration;

	public ColorInterpolator(ColorBlender sourceColor)
	{
		this.sourceColor = sourceColor;
	}
	
	public abstract ColorBlender getCurrentColor();
	public abstract void interpolateToColor(ColorBlender color, float duration);
	public abstract void interpolateFromColor(ColorBlender color, float duration);
	public abstract boolean hasFinished();
}
