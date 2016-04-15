package br.com.guigasgame.round.type;

import com.google.common.eventbus.Subscribe;

import br.com.guigasgame.frag.EventCentralMessenger;
import br.com.guigasgame.gamemachine.RoundGameState;
import br.com.guigasgame.gameobject.hero.playable.DiedFragEventWrapper;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.playable.SpawnEventWrapper;


public class DeathMatchRoundType implements RoundType
{

	private final int deaths;
	private RoundGameState roundGameState;

	public DeathMatchRoundType(int deaths)
	{
		this.deaths = deaths;
	}

	@Subscribe public void onSpawnEvent(SpawnEventWrapper spawnEventWrapper) 
	{
		PlayableGameHero spawnedHero = ((PlayableGameHero)(spawnEventWrapper.getSender()));
	}

	@Subscribe public void onDiedEvent(DiedFragEventWrapper diedEventWrapper) 
	{
		PlayableGameHero deadHero = ((PlayableGameHero)(diedEventWrapper.getSender()));
//		if (deadHero.getHeroProperties().getFragCounter().getFrag().getKills() > deaths)
		{
//			EventCentralMessenger.getInstance().fireEvent(new HeroSpawner(this, deadHero));
			System.out.println("Arrá!!! CHUPA!");
//			roundGameState.spawnHero(deadHero);
//			timerEventsController.addEventListener(roundGameState, 5.0, value);
		}
	}
	
	@Override
	public void setRoundState(RoundGameState roundGameState)
	{
		this.roundGameState = roundGameState;
	}
}
