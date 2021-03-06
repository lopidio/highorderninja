package br.com.guigasgame.gameobject.hero.attributes;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import br.com.guigasgame.updatable.UpdatableFromTime;

@XmlAccessorType(XmlAccessType.NONE)
public class HeroAttribute implements UpdatableFromTime
{
	protected List<HeroAttributeListener> listeners;
	@XmlAttribute
	private float regeneratesPerSecond;
	@XmlAttribute(required=true)
	private float maxValue;
	private float currentValue;
	
	public HeroAttribute(float maxValue, float regeneratesPerSecond)
	{
		this.maxValue = maxValue;
		this.currentValue = maxValue;
		this.regeneratesPerSecond = regeneratesPerSecond;
		this.listeners = new ArrayList<>();
	}

	public HeroAttribute(float maxValue)
	{
		this(maxValue, 0);
	}
	
	public HeroAttribute()
	{
		this(0, 0);
	}
	
	public HeroAttribute clone()
	{
		return new HeroAttribute(maxValue, regeneratesPerSecond);
	}


	@Override
	public void update(float deltaTime)
	{
		if (currentValue <= maxValue)
		{
			increment(deltaTime*regeneratesPerSecond);
		}
	}
	
	public boolean refill()
	{
		return increment(maxValue);
	}
	
	public boolean decrement(float value)
	{
		if (currentValue <= 0)
			return false;
		currentValue -= value;
		notifyChange(this, -value);
		if (currentValue <= 0)
		{
			currentValue = 0;
			notifyIsEmpty();
		}
		return true;
	}

	public boolean increment(float value)
	{
		if (currentValue >= maxValue)
			return false;
		currentValue += value;
		if (currentValue >= maxValue)
		{
			currentValue = maxValue;
			notifyGotFull();
		}

		notifyChange(this, value);
		return true;
	}

	private void notifyChange(HeroAttribute heroAttribute, float value)
	{
		if (value == 0)
			return;
		for( HeroAttributeListener heroAttributeListener : listeners )
		{
			heroAttributeListener.changed(this, value);
		}
	}

	private void notifyIsEmpty()
	{
		for( HeroAttributeListener heroAttributeListener : listeners )
		{
			heroAttributeListener.attributeGotEmpty(this);
		}
	}

	private void notifyGotFull()
	{
		for( HeroAttributeListener heroAttributeListener : listeners )
		{
			heroAttributeListener.gotFull(this);
		}
	}
	
	public float getRegeneratesPerSecond()
	{
		return regeneratesPerSecond;
	}

	public float getMaxValue()
	{
		return maxValue;
	}
	
	public float getCurrentValue()
	{
		return currentValue;
	}
	
	public void addListener(HeroAttributeListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeListener(HeroAttributeListener listener)
	{
		listeners.remove(listener);
	}

	public boolean isGreaterThanOne()
	{
		return currentValue >= 1;
	}
}
