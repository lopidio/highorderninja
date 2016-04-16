package br.com.guigasgame.round.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.guigasgame.gamemachine.RoundGameState;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class GameHeroSpawner implements UpdatableFromTime
{
	private static class HeroSpawnerCounter
	{
		public float timeToSpawn;
		public PlayableGameHero gameHero;
		public HeroSpawnerCounter(float timeToSpawn, PlayableGameHero gameHero)
		{
			this.timeToSpawn = timeToSpawn;
			this.gameHero = gameHero;
		}
		
	}
	
	
	private List<HeroSpawnerCounter> heroSpawnerCounters;
	private RoundGameState roundGameState;
	
	public GameHeroSpawner(RoundGameState roundGameState)
	{
		heroSpawnerCounters = new ArrayList<>();
		this.roundGameState = roundGameState;
	}
	
	public void addHeroToSpawn(PlayableGameHero hero, float time)
	{
		heroSpawnerCounters.add(new HeroSpawnerCounter(time, hero));
	}

	@Override
	public void update(float deltaTime)
	{
		for( HeroSpawnerCounter counter : heroSpawnerCounters )
		{
			counter.timeToSpawn -= deltaTime;
			if (counter.timeToSpawn <= 0)
			{
				roundGameState.spawnHero(counter.gameHero);
			}
		}
		
		Iterator<HeroSpawnerCounter> iterator = heroSpawnerCounters.iterator();
		while (iterator.hasNext())
		{
			HeroSpawnerCounter toRemove = iterator.next(); // must be called before you can call iterator.remove()
			if (toRemove.timeToSpawn <= 0)
			{
				iterator.remove();
			}
		}

	}

	public void addHeroToSpawnImediatly(PlayableGameHero gameHero)
	{
		addHeroToSpawn(gameHero, 0);
	}

}
