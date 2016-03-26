package br.com.guigasgame.interpolator;

import org.jsfml.system.Vector2f;

public class VectorLinearInterpolator extends InterpolatorFromTime<Vector2f>
{
	private Vector2f ratioPerSecond;
	private Vector2f remaining;

	public VectorLinearInterpolator(Vector2f current, float duration)
	{
		super(current, duration);
	}

	@Override
	public void update(float deltaTime)
	{
		if (hasFinished())
			return;
		final Vector2f value = new Vector2f(ratioPerSecond.x*deltaTime, ratioPerSecond.y*deltaTime);
		current = Vector2f.add(current, value);
		remaining = new Vector2f(remaining.x - Math.abs(value.x), remaining.y - Math.abs(value.y));
		limitToDestiny();
	}

	@Override
	public VectorLinearInterpolator interpolateTo(Vector2f destiny)
	{
		super.interpolateTo(destiny);
		remaining = Vector2f.sub(destiny, current);
		ratioPerSecond = Vector2f.div(remaining, duration);
		remaining = new Vector2f(Math.abs(remaining.x), Math.abs(remaining.y));
		return this;
	}
	
	private void limitToDestiny()
	{
		if (hasFinished())
		{
			float newX = current.x;
			float newY = current.y;
			if (ratioPerSecond.x > 0)
			{
				newX = Math.min(current.x, destiny.x);
			}
			if (ratioPerSecond.x < 0)
			{
				newX = Math.max(current.x, destiny.x);
			}
			if (ratioPerSecond.y > 0)
			{
				newY = Math.min(current.y, destiny.y);
			}
			if (ratioPerSecond.y < 0)
			{
				newY = Math.max(current.y, destiny.y);
			}
			if (current.x != newX || current.y != newY)
				current = new Vector2f(newX, newY);
		}
	}

	public boolean hasFinished()
	{
		return remaining.x <= 0 && remaining.y <= 0;
	}

}
