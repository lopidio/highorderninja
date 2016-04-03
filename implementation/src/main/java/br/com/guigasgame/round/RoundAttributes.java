package br.com.guigasgame.round;

import java.util.List;

import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.round.hud.RoundHudSkin;
import br.com.guigasgame.scenery.creation.SceneryInitialize;
import br.com.guigasgame.team.HeroTeam;


public class RoundAttributes
{

	private final RoundHeroAttributes heroAttributes;
	private final List<HeroTeam> teams;
	private final SceneryInitialize sceneryInitialize;
	private final int totalTime;
	private final RoundHudSkin hudSkin;

	public RoundAttributes(RoundHeroAttributes heroAttributes, List<HeroTeam> teams, SceneryInitialize sceneryInitialize, int totalTime, RoundHudSkin hudPositioner)
	{
		this.heroAttributes = heroAttributes;
		this.teams = teams;
		this.sceneryInitialize = sceneryInitialize;
		this.totalTime = totalTime;
		this.hudSkin = hudPositioner;
	}

	public RoundHeroAttributes getHeroAttributes()
	{
		return heroAttributes;
	}

	public List<HeroTeam> getTeams()
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
