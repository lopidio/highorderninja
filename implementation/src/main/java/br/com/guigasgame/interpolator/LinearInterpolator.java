package br.com.guigasgame.interpolator;

public class LinearInterpolator extends Interpolator
{
	private final float ratioPerSecond;
	private float remaining;

	public LinearInterpolator(float source, float destiny, float duration)
	{
		super(source, destiny);
		remaining = destiny - source;
		ratioPerSecond = remaining / duration;
		remaining = Math.abs(remaining);
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
