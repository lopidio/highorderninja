package br.com.guigasgame.frag;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.team.HeroTeam;

//@ApplicationScoped
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
		void receiveEvent(FragEventWrapper eventWrapper);
	}
	
	private static FragEventMessenger eventMessenger;
	public static FragEventMessenger getInstance()
	{
		if (eventMessenger == null)
			eventMessenger = new FragEventMessenger();
		return eventMessenger;
	}
	private final Map<PlayableGameHero, FragEventListener> heroListeners;
	private final Map<HeroTeam, FragEventListener> teamListeners;
	
	private FragEventMessenger()
	{
		heroListeners = new HashMap<>();
		teamListeners = new HashMap<>();
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
		
		dispatchToTeamListeners(eventWrapper);
		dispatchToHeroListeners(eventWrapper);
	}

	private void dispatchToHeroListeners(FragEventWrapper wrapper)
	{
		for( Entry<PlayableGameHero, FragEventListener> entry : heroListeners.entrySet() )
		{
			final int myPlayerId = entry.getKey().getHeroProperties().getPlayerId();
			if (myPlayerId == wrapper.getMyId() || myPlayerId == wrapper.getOtherId())
				entry.getValue().receiveEvent(wrapper);
		}
	}

	private void dispatchToTeamListeners(FragEventWrapper wrapper)
	{
		for( Entry<HeroTeam, FragEventListener> entry : teamListeners.entrySet() )
		{
			final int myTeamId = entry.getKey().getTeamId();
			if (myTeamId == wrapper.getMyTeamId() || myTeamId == wrapper.getOtherTeamId())
				entry.getValue().receiveEvent(wrapper);
		}
	}
	
	
	public void subscribeOnTeamEvents(HeroTeam heroTeam, FragEventListener listener)
	{
		teamListeners.put(heroTeam, listener);
	}
	
	public void subscribeOnHeroEvents(PlayableGameHero gameHero, FragEventListener listener)
	{
		heroListeners.put(gameHero, listener);
	}
	
	
	public void clearAllListeners()
	{
		heroListeners.clear();
		teamListeners.clear();
	}
}
