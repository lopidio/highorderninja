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
		dispatchToTeamListeners(me, fragEventIndex, other);
		dispatchToHeroListeners(me, fragEventIndex, other);
	}

	private void dispatchToHeroListeners(PlayableGameHero me, FragEventIndex fragEventIndex, PlayableGameHero other)
	{
		final int myID = me.getHeroProperties().getPlayerId();
		int otherId = -1;
		if (other != null)
			otherId = other.getHeroProperties().getPlayerId();
		
		for( Entry<PlayableGameHero, FragEventListener> entry : heroListeners.entrySet() )
		{
			final int eventPlayerId = entry.getKey().getHeroProperties().getPlayerId();
			if (eventPlayerId == myID || eventPlayerId == otherId)
				entry.getValue().receiveEvent(new FragEventWrapper(myID, otherId, fragEventIndex));
		}
	}

	private void dispatchToTeamListeners(PlayableGameHero me, FragEventIndex fragEventIndex, PlayableGameHero other)
	{
		final int myTeamID = me.getHeroProperties().getHeroTeam().getTeamId();
		int otherTeamId = -1;
		if (other != null)
			otherTeamId = other.getHeroProperties().getHeroTeam().getTeamId();
		
		for( Entry<HeroTeam, FragEventListener> entry : teamListeners.entrySet() )
		{
			final int eventTeamId = entry.getKey().getTeamId();
			if (eventTeamId == myTeamID || eventTeamId == otherTeamId)
				entry.getValue().receiveEvent(new FragEventWrapper(myTeamID, otherTeamId, fragEventIndex));
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
