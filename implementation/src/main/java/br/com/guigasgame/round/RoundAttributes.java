package br.com.guigasgame.round;

import java.util.List;

import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroAttributesHudController;
import br.com.guigasgame.round.hud.dynamic.heroattributes.barbellow.HeroAttributesCircleAndBarsBellowHudController;
import br.com.guigasgame.scenery.creation.SceneryInitialize;
import br.com.guigasgame.team.HeroTeam;


public class RoundAttributes
{

	private final RoundHeroAttributes heroAttributes;
	private final List<HeroTeam> teams;
	private final SceneryInitialize sceneryInitialize;
	private final int totalTime;

	public RoundAttributes(RoundHeroAttributes roundHeroAttributes, List<HeroTeam> teams, SceneryInitialize sceneryInitializer, int fullTime)
	{
		this.heroAttributes = roundHeroAttributes;
		this.teams = teams;
		this.sceneryInitialize = sceneryInitializer;
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

	public SceneryInitialize getSceneryInitializer()
	{
		return sceneryInitialize;
	}

	public int getTime()
	{
		return totalTime;
	}

	public HeroAttributesHudController initializeHeroAttributes(PlayableGameHero gameHero)
	{
		//TODO THERE HAS TO BE A FACTORY AS AN INSTANC ATTRIBUTE
		return new HeroAttributesCircleAndBarsBellowHudController(gameHero);
	}

}
