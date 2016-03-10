package br.com.guigasgame.gameobject.hero.playable;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;

import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributesController;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.team.HeroTeam;

public class PlayableHeroDefinition
{
	private final GameHeroInputMap gameHeroInput;
	private final int playerId;
	
	private Color color;
	private Vec2 spawnPosition;
	private HeroTeam heroTeam;
	private RoundHeroAttributesController roundHeroAttributesController;

	public PlayableHeroDefinition(GameHeroInputMap gameHeroInput, int id)
	{
		this.gameHeroInput = gameHeroInput;
		this.playerId = id;

		spawnPosition = new Vec2();
	}
	
	public void setSpawnPosition(Vec2 position)
	{
		spawnPosition = position;
	}
	
	public void setTeamConfigurations(HeroTeam team) 
	{
		this.heroTeam = team;
		this.color = heroTeam.getColorOfPlayer(this);
	}

	public GameHeroInputMap getGameHeroInput()
	{
		return gameHeroInput;
	}

	public int getPlayerId()
	{
		return playerId;
	}

	public Color getColor()
	{
		return color;
	}

	public IntegerMask getTeamMask()
	{
		return heroTeam.getTeamMask();
	}

	public IntegerMask getEnemiesMask()
	{
		return heroTeam.getEnemiesMask(playerId);
	}

	public Vec2 getInitialPosition()
	{
		return spawnPosition;
	}

	public RoundHeroAttributesController getAttributesController()
	{
		return roundHeroAttributesController;
	}

	public void createAttributesController(RoundHeroAttributes roundHeroAttributes)
	{
		roundHeroAttributesController = new RoundHeroAttributesController(roundHeroAttributes);		
	}

}
