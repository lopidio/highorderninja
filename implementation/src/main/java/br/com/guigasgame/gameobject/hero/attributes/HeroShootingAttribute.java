package br.com.guigasgame.gameobject.hero.attributes;


public class HeroShootingAttribute extends HeroAttribute
{
	
	private float maxIntervalBetweenShots;
	private float currentIntervalBetweenShots;

	public HeroShootingAttribute(float maxValue, float intervalBetweenShoot, float regeneratesPerSecond)
	{
		super(maxValue, regeneratesPerSecond);
		maxIntervalBetweenShots = intervalBetweenShoot;
	}
	
	@Override
	public HeroAttribute clone()
	{
		return new HeroShootingAttribute(getMaxValue(), maxIntervalBetweenShots, getRegeneratesPerSecond());
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if (currentIntervalBetweenShots <= maxIntervalBetweenShots)
		{
			incrementShootingInterval(deltaTime*maxIntervalBetweenShots);
		}

	}

	private boolean incrementShootingInterval(float value)
	{
		if (currentIntervalBetweenShots >= maxIntervalBetweenShots)
			return false;
		currentIntervalBetweenShots += value;
		if (currentIntervalBetweenShots >= maxIntervalBetweenShots)
			notifyShootintIsAble();

		notifyShootingIncrement(this, value);
		return true;
	}
	
	@Override
	public boolean decrement(float value)
	{
		if (super.decrement(value))
		{
			currentIntervalBetweenShots = 0;
			return true;
		}
		return false;
	}

	private void notifyShootingIncrement(HeroShootingAttribute heroShootingAttribute, float value)
	{
		for( HeroAttributeListener heroAttributeListener : listeners )
		{
			heroAttributeListener.shootingIncrement(this, value);
		}
	}

	private void notifyShootintIsAble()
	{
		for( HeroAttributeListener heroAttributeListener : listeners )
		{
			heroAttributeListener.shootingIsAble(this);
		}
	}

	
	public float getMaxIntervalBetweenShots()
	{
		return maxIntervalBetweenShots;
	}

	
	public float getCurrentIntervalBetweenShots()
	{
		return currentIntervalBetweenShots;
	}

	public boolean isAbleToShoot()
	{
		return currentIntervalBetweenShots >= maxIntervalBetweenShots && isGreaterThanOne();
	}

}
