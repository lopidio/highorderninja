package br.com.guigasgame.round;

import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.round.hud.RoundHudSkin;
import br.com.guigasgame.round.type.RoundMode;
import br.com.guigasgame.scenery.creation.SceneryInitialize;
import br.com.guigasgame.team.TeamsController;


public class RoundProperties
{

	private final RoundHeroAttributes heroAttributes;
	private final TeamsController teams;
	private final SceneryInitialize sceneryInitialize;
	private final int totalTime;
	private final RoundHudSkin hudSkin;
	private final RoundMode roundType;
	private float spawnTime;

	public RoundProperties(RoundHeroAttributes heroAttributes, TeamsController teamsController, SceneryInitialize sceneryInitialize, int totalTime, RoundHudSkin hudPositioner, RoundMode roundType, float spawnTime)
	{
		this.heroAttributes = heroAttributes;
		this.teams = teamsController;
		this.sceneryInitialize = sceneryInitialize;
		this.totalTime = totalTime;
		this.hudSkin = hudPositioner;
		this.roundType = roundType;
		this.spawnTime = spawnTime;
	}
	
	public RoundMode getRoundType()
	{
		return roundType;
	}

	public RoundHeroAttributes getHeroAttributes()
	{
		return heroAttributes;
	}

	public TeamsController getTeams()
	{
		return teams;
	}

	public SceneryInitialize getSceneryInitializer()
	{
		return sceneryInitialize;
	}

	public int getTotalTime()
	{
		return totalTime;
	}

	public RoundHudSkin getHudSkin()
	{
		return hudSkin;
	}

	public float getSpawnTime()
	{
		return spawnTime;
	}

}
