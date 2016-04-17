package br.com.guigasgame.round.type;

import com.google.common.eventbus.Subscribe;

import br.com.guigasgame.frag.DiedFragEventWrapper;
import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.frag.SpawnEventWrapper;
import br.com.guigasgame.gamemachine.RoundGameState;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class DeathMatchRoundType implements RoundMode
{

	private final int maxDeaths;
	private RoundGameState roundGameState;

	public DeathMatchRoundType(int deaths)
	{
		this.maxDeaths = deaths;
	}

	@Subscribe public void onSpawnEvent(SpawnEventWrapper spawnEventWrapper) 
	{
		PlayableGameHero spawnedHero = ((PlayableGameHero)(spawnEventWrapper.getSender()));
	}

	@Subscribe public void onDiedEvent(DiedFragEventWrapper diedEventWrapper) 
	{
		PlayableGameHero deadHero = ((PlayableGameHero)(diedEventWrapper.getSender()));
		if (deadHero.getHeroProperties().getFragCounter().getFrag().getDeaths() <= maxDeaths)
		{
			roundGameState.addHeroToSpawn(deadHero);
//			EventCentralMessenger.getInstance().fireEvent(new EventWrapper(null));
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

	@Override
	public void onFragChange(FragStatistic statistic)
	{
		
	}
}
