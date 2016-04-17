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
	private final RoundMode roundMode;

	public RoundProperties(RoundHeroAttributes heroAttributes, TeamsController teamsController, SceneryInitialize sceneryInitialize, int totalTime, RoundHudSkin hudPositioner, RoundMode roundMode)
	{
		this.heroAttributes = heroAttributes;
		this.teams = teamsController;
		this.sceneryInitialize = sceneryInitialize;
		this.totalTime = totalTime;
		this.hudSkin = hudPositioner;
		this.roundMode = roundMode;
	}
	
	public RoundMode getRoundMode()
	{
		return roundMode;
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

}
