package br.com.guigasgame.frag;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

@ApplicationScoped
public class FragEventMessenger
{
	public enum FragEventIndex
	{
		SHOOT,
		SHOOT_ON_TARGET,
		KILL,
		SUICIDE
	}
	
	public interface FragEventListener
	{
		void receiveFragEvent(FragEventWrapper eventWrapper);
		boolean acceptFragEvent(FragEventWrapper eventWrapper);
	}
	
	private static FragEventMessenger eventMessenger;
	public static FragEventMessenger getInstance()
	{
		if (eventMessenger == null)
			eventMessenger = new FragEventMessenger();
		return eventMessenger;
	}
	private final List<FragEventListener> listeners;
	
	private FragEventMessenger()
	{
		listeners = new ArrayList<>();
	}
	
	public void fireEvent(PlayableGameHero me, FragEventIndex fragEventIndex)
	{
		dispatchEvent(me, fragEventIndex, null);
	}
	
	public void fireEvent(PlayableGameHero me, FragEventIndex fragEventIndex, PlayableGameHero other)
	{
		dispatchEvent(me, fragEventIndex, other);
	}
	
	private void dispatchEvent(PlayableGameHero me, FragEventIndex fragEventIndex, PlayableGameHero other)
	{
		final int myHeroID = me.getHeroProperties().getPlayerId();
		final int myTeamID = me.getHeroProperties().getHeroTeam().getTeamId();
		int otherHeroId = -1;
		int otherTeamId = -1;
		if (other != null)
		{
			otherHeroId = other.getHeroProperties().getPlayerId();
			otherTeamId = other.getHeroProperties().getHeroTeam().getTeamId();
		}
		FragEventWrapper eventWrapper = new FragEventWrapper(myHeroID, myTeamID, otherHeroId, otherTeamId, fragEventIndex);
		
		dispatchToListeners(eventWrapper);
	}

	private void dispatchToListeners(FragEventWrapper wrapper)
	{
		for( FragEventListener listener : listeners )
		{
			if (listener.acceptFragEvent(wrapper))
				listener.receiveFragEvent(wrapper);
		}
	}
	
	
	public void subscribeOnEvents(FragEventListener listener)
	{
		listeners.add(listener);
	}
	
	
	public void clearAllListeners()
	{
		listeners.clear();
	}
}
