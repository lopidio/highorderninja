package br.com.guigasgame.frag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventCentralMessenger
{
	public interface EventListener
	{
		void receiveFragEvent(EventWrapper eventWrapper);
	}
	
	private static EventCentralMessenger eventMessenger;
	public static EventCentralMessenger getInstance()
	{
		if (eventMessenger == null)
			eventMessenger = new EventCentralMessenger();
		return eventMessenger;
	}
	
	
	private Map<Class<? extends EventWrapper>, List<EventListener> > listenersMap;
	private List<EventWrapper> eventsQueue;

	private EventCentralMessenger()
	{
		listenersMap = new HashMap<>();
		eventsQueue = new ArrayList<>();
	}
	
	public <T extends EventWrapper> void subscribe(Class<T> channel, EventListener listener)
	{
		List<EventListener> list = listenersMap.getOrDefault(channel, new ArrayList<>());
		list.add(listener);
		listenersMap.put(channel, list);
	}	

	public void fireEvent(EventWrapper obj)
	{
		eventsQueue.add(obj);
	}

	@SuppressWarnings("unchecked")
	public void update()
	{
		for (EventWrapper event : eventsQueue) 
		{
			Class<? extends EventWrapper> clazz = event.getClass();
			do 
			{
				dispatchToListeners(event, clazz);
				clazz = (Class<? extends EventWrapper>) clazz.getSuperclass();
			}
			while (clazz != null);
		}
		eventsQueue.clear();
	}

	private void dispatchToListeners(EventWrapper event, Class<? extends EventWrapper> clazz)
	{
		System.out.println("Dispatching event: " + clazz.getSimpleName());

		List<EventListener> listeners = listenersMap.get(clazz);
		if (listeners != null)
		{
			for (EventListener listener : listeners) 
			{
				listener.receiveFragEvent(event);
			}
		}
	}

	public void clearAllListeners()
	{
		for( List<EventListener> list :listenersMap.values() )
		{
			list.clear();
		}
	}
}
