package br.com.guigasgame.time;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.updatable.UpdatableFromTime;

public class ReverseTimeCounter implements UpdatableFromTime
{
	public static interface ReverseTimeCounterListener
	{
		public default void onDecimalChange(int currentValue)
		{
		}
		public default void timeOut()
		{
		}
		public default void halfTime(float currentValue)
		{
		}
	}
	
	
	private float currentValue;
	private final float initialValue;
	private boolean halfTimeNotified;
	private final List<ReverseTimeCounterListener> listeners;

	public ReverseTimeCounter(float initialValue)
	{
		this.initialValue = initialValue;
		this.currentValue = initialValue;
		listeners = new ArrayList<>();
		
	}
	
	public void addListener(ReverseTimeCounterListener listener)
	{
		listeners.add(listener);
		listener.onDecimalChange((int) Math.floor(currentValue));
	}

	@Override
	public void update(float deltaTime)
	{
		currentValue -= deltaTime;
		notifyDecimalChanged(deltaTime);
		notifyTimeOut();
		notifyHalfTime();
	}

	private void notifyHalfTime()
	{
		if (currentValue/initialValue <= 0.5f && !halfTimeNotified)
		{
			halfTimeNotified = true;
			for( ReverseTimeCounterListener reverseTimeCounterListener : listeners )
			{
				reverseTimeCounterListener.halfTime(currentValue);
			}
		}
	}

	private void notifyTimeOut()
	{
		if (currentValue <= 0)
		{
			for( ReverseTimeCounterListener reverseTimeCounterListener : listeners )
			{
				reverseTimeCounterListener.timeOut();
			}
		}
	}

	private void notifyDecimalChanged(float deltaTime)
	{
		final int lastDecimal = (int) Math.floor(currentValue);
		final int currentDecimal = (int) Math.floor(currentValue - deltaTime);
		if (lastDecimal != currentDecimal)
		{
			for( ReverseTimeCounterListener reverseTimeCounterListener : listeners )
			{
				reverseTimeCounterListener.onDecimalChange(currentDecimal);
			}
		}
	}
	
	
}
