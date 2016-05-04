package br.com.guigasgame.gameobject.hero.attributes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class HeroShootingAttribute extends HeroAttribute
{
	@XmlAttribute(required=true)
	private final float intervalBetweenShots;
	private float currentIntervalBetweenShots;

	public HeroShootingAttribute(float maxValue, float intervalBetweenShoot, float regeneratesPerSecond)
	{
		super(maxValue, regeneratesPerSecond);
		intervalBetweenShots = intervalBetweenShoot;
	}

	/**
	 * DO NOT USE
	 */
	public HeroShootingAttribute()
	{
		super();
		intervalBetweenShots = 0;
	}
	
	@Override
	public HeroAttribute clone()
	{
		return new HeroShootingAttribute(getMaxValue(), intervalBetweenShots, getRegeneratesPerSecond());
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if (currentIntervalBetweenShots <= intervalBetweenShots)
		{
			incrementShootingInterval(deltaTime);
		}

	}

	private boolean incrementShootingInterval(float value)
	{
		if (currentIntervalBetweenShots >= intervalBetweenShots)
			return false;
		currentIntervalBetweenShots += value;
		if (currentIntervalBetweenShots >= intervalBetweenShots)
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

	public boolean isAbleToShoot()
	{
		return currentIntervalBetweenShots >= intervalBetweenShots && isGreaterThanOne();
	}

}
