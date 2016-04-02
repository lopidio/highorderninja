package br.com.guigasgame.time;

import br.com.guigasgame.time.TimerEventsController.TimeListener;
import br.com.guigasgame.updatable.UpdatableFromTime;

class TimeEvent implements UpdatableFromTime
{
	private final TimeListener listener;
	private final float totalTimeToEvent;
	private final Object value;
	private float currentToEvent;
	private boolean periodic;
	
	public TimeEvent(TimeListener listener, float timeToEvent, Object value)
	{
		this.listener = listener;
		this.currentToEvent = timeToEvent;
		this.totalTimeToEvent = timeToEvent;
		this.value = value;
		periodic = false;
	}
	
	public TimeEvent(TimeListener listener, float timeToEvent, Object value, boolean periodic)
	{
		this(listener, timeToEvent, value);
		this.periodic = periodic;
	}

	@Override
	public void update(float deltaTime)
	{
		currentToEvent -= deltaTime;
		if (currentToEvent <= 0)
			fire();
	}

	public void fire()
	{
		listener.receiveTimeEvent(value);
		if (periodic)
			currentToEvent += totalTimeToEvent;
	}
	
	public boolean isUpToRemove()
	{
		return currentToEvent <= 0 && !periodic;
	}

	
}
