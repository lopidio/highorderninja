package br.com.guigasgame.round.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

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
	
	private Queue<EventWrapper> eventsQueue;
	private List<Object> listeners;
	private EventBus eventBus;

	private EventCentralMessenger()
	{
		eventsQueue = new ConcurrentLinkedDeque<EventWrapper>();
		eventBus = new EventBus();
	}
	
	public void subscribe(Object obj)
	{
		eventBus.register(obj);
		listeners = new ArrayList<>();
	}	

	public void fireEvent(EventWrapper event)
	{
		eventsQueue.add(event);
	}

	public void update()
	{
		synchronized (eventsQueue)
		{
			for (EventWrapper event : eventsQueue) 
			{
				eventBus.post(event);
			}
			eventsQueue.clear();
		}
	}
	
	public void clearListeners()
	{
		for( Object listener : listeners )
		{
			eventBus.unregister(listener);
		}
		listeners.clear();
	}

}
