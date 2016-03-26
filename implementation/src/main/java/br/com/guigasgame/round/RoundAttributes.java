package br.com.guigasgame.round;

import java.util.List;

import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.scenery.creation.SceneryCreator;
import br.com.guigasgame.team.HeroTeam;

public class RoundAttributes
{
	private final RoundHeroAttributes heroAttributes;
	private final List<HeroTeam> teams;
	private final SceneryCreator sceneryCreator;
	private final int totalTime;
	
	public RoundAttributes(RoundHeroAttributes roundHeroAttributes, List<HeroTeam> teams, SceneryCreator sceneryCreator, int fullTime)
	{
		this.heroAttributes = roundHeroAttributes;
		this.teams = teams;
		this.sceneryCreator = sceneryCreator;
		this.totalTime = fullTime;
	}
	
	public RoundHeroAttributes getHeroAttributes()
	{
		return heroAttributes;
	}
	
	public List<HeroTeam> getTeams()
	{
		return teams;
	}
	
	public SceneryCreator getSceneryCreator()
	{
		return sceneryCreator;
	}
	
	public int getTime()
	{
		return totalTime;
	}
	
	
	
	
	
}
