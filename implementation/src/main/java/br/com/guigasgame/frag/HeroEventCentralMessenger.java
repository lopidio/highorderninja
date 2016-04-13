package br.com.guigasgame.frag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroEventCentralMessenger
{
	public interface HeroEventListener
	{
		void receiveFragEvent(HeroEventWrapper eventWrapper);
	}
	
	private static HeroEventCentralMessenger eventMessenger;
	public static HeroEventCentralMessenger getInstance()
	{
		if (eventMessenger == null)
			eventMessenger = new HeroEventCentralMessenger();
		return eventMessenger;
	}
	
	
	private Map<Class<? extends HeroEventWrapper>, List<HeroEventListener> > listenersMap;
	private List<HeroEventWrapper> eventsQueue;

	private HeroEventCentralMessenger()
	{
		listenersMap = new HashMap<>();
		eventsQueue = new ArrayList<>();
	}
	
	public <T extends HeroEventWrapper> void subscribe(Class<T> clazz, HeroEventListener listener)
	{
		List<HeroEventListener> list = listenersMap.getOrDefault(clazz, new ArrayList<>());
		list.add(listener);
		listenersMap.put(clazz, list);
	}	

	public void fireEvent(HeroEventWrapper obj)
	{
		eventsQueue.add(obj);
	}

	@SuppressWarnings("unchecked")
	public void update()
	{
		for (HeroEventWrapper event : eventsQueue) 
		{
			Class<? extends HeroEventWrapper> clazz = event.getClass();
			do 
			{
				dispatchToListeners(event, clazz);
				clazz = (Class<? extends HeroEventWrapper>) clazz.getSuperclass();
			}
			while (clazz != null);
		}
		eventsQueue.clear();
	}

	private void dispatchToListeners(HeroEventWrapper event, Class<? extends HeroEventWrapper> clazz)
	{
		List<HeroEventListener> listeners = listenersMap.get(clazz);
		if (listeners != null)
		{
			for (HeroEventListener listener : listeners) 
			{
				listener.receiveFragEvent(event);
			}
		}
	}

	public void clearAllListeners()
	{
		for( List<HeroEventListener> list :listenersMap.values() )
		{
			list.clear();
		}
	}
}
