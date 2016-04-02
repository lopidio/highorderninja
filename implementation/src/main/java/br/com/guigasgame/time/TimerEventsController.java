package br.com.guigasgame.time;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.guigasgame.updatable.UpdatableFromTime;

public class TimerEventsController implements UpdatableFromTime
{
	public interface TimeListener
	{
		void receiveTimeEvent(Object value);
	}

	private List<TimeEvent> events;
	
	public TimerEventsController()
	{
		events = new ArrayList<>();
	}

	@Override
	public void update(float deltaTime)
	{
		for( TimeEvent timeEvent : events )
		{
			timeEvent.update(deltaTime);
		}
		
		removeFiredEvents();
	}

	private void removeFiredEvents()
	{
		Iterator<TimeEvent> iterator = events.iterator();
		while (iterator.hasNext())
		{
			TimeEvent toRemove = iterator.next(); // must be called before you can call iterator.remove()
			if (toRemove.isUpToRemove())
			{
				iterator.remove();
			}
		}
	}

	public void addEventListener(TimeListener listener, float timeToEvent, Object value)
	{
		events.add(new TimeEvent(listener, timeToEvent, value));
	}

	public void addPeriodicEventListener(TimeListener listener, float timeToEvent, Object value)
	{
		events.add(new TimeEvent(listener, timeToEvent, value, true));		
	}

}
