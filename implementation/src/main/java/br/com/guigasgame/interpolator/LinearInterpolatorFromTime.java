package br.com.guigasgame.interpolator;

public class LinearInterpolatorFromTime extends InterpolatorFromTime<Float>
{
	private float ratioPerSecond;
	private float remaining;

	public LinearInterpolatorFromTime(float source, float duration)
	{
		super(source, duration);
	}

	@Override
	public void update(float deltaTime)
	{
		if (hasFinished())
			return;
		final float value = ratioPerSecond * deltaTime;
		current += value;
		remaining -= Math.abs(value);
		limitToDestiny();
	}
	
	@Override
	public LinearInterpolatorFromTime interpolateTo(Float destiny)
	{
		super.interpolateTo(destiny);
		remaining = destiny - current;
		ratioPerSecond = remaining / duration;
		remaining = Math.abs(remaining);
		return this;
	}

	private void limitToDestiny()
	{
		if (hasFinished())
		{
			if (ratioPerSecond > 0)
			{
				current = Math.min(current, destiny);
			}
			if (ratioPerSecond < 0)
			{
				current = Math.max(current, destiny);
			}
		}
	}

	public boolean hasFinished()
	{
		return remaining <= 0;
	}
	
}
