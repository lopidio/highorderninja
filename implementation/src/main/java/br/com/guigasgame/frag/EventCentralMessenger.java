package br.com.guigasgame.frag;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;

public class EventCentralMessenger
{
	private static EventCentralMessenger eventMessenger;
	public static EventCentralMessenger getInstance()
	{
		if (eventMessenger == null)
			eventMessenger = new EventCentralMessenger();
		return eventMessenger;
	}
	
	private List<EventWrapper> eventsQueue;
	private EventBus eventBus;

	private EventCentralMessenger()
	{
		eventsQueue = new ArrayList<>();
		eventBus = new EventBus();
	}
	
	public void subscribe(Object obj)
	{
		eventBus.register(obj);
	}	

	public void fireEvent(EventWrapper event)
	{
		eventsQueue.add(event);
	}

	public void update()
	{
		for (EventWrapper event : eventsQueue) 
		{
			eventBus.post(event);
		}
		eventsQueue.clear();
	}

}
