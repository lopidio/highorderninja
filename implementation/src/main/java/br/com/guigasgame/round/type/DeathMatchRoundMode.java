package br.com.guigasgame.round.type;

import com.google.common.eventbus.Subscribe;

import br.com.guigasgame.frag.DiedFragEventWrapper;
import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.frag.KillFragEventWrapper;
import br.com.guigasgame.gamemachine.RoundOverEventWrapper;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.event.EventCentralMessenger;


public class DeathMatchRoundMode implements RoundMode
{

	private final int maxDeaths;
	private final int killsToWin;
	private final float spawnTime;

	public DeathMatchRoundMode(int deaths, int kills, float spawnTime)
	{
		this.maxDeaths = deaths;
		this.killsToWin = kills;
		this.spawnTime = spawnTime;
	}

	@Subscribe public void onDiedEvent(DiedFragEventWrapper diedEventWrapper) 
	{
		PlayableGameHero deadHero = ((PlayableGameHero)(diedEventWrapper.getSender()));
		if (deadHero.getHeroProperties().getFragCounter().getFrag().getDeaths() < maxDeaths)
		{
			EventCentralMessenger.getInstance().fireEvent(new RegisterHeroToRespawnEvent(this, deadHero, spawnTime));
		}
	}

	@Subscribe public void onKillEvent(KillFragEventWrapper killEventWrapper) 
	{
		PlayableGameHero deadHero = ((PlayableGameHero)(killEventWrapper.getSender()));
		if (deadHero.getHeroProperties().getFragCounter().getFrag().getKills() >= killsToWin)
		{
//			EventCentralMessenger.getInstance().fireEvent(new RoundOverEventWrapper(this));
		}
	}
	
	@Override
	public void onFragChange(FragStatistic statistic)
	{
		if (statistic.getKills() >= killsToWin)
		{
			EventCentralMessenger.getInstance().fireEvent(new RoundOverEventWrapper(this));
		}
	}

}
