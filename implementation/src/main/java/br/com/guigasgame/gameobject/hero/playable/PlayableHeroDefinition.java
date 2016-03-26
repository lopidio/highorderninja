package br.com.guigasgame.gameobject.hero.playable;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.team.HeroTeam;

public class PlayableHeroDefinition
{
	private final GameHeroInputMap gameHeroInput;
	private final int playerId;
	
	private ColorBlender color;
	private Vec2 spawnPosition;
	private HeroTeam heroTeam;
	private RoundHeroAttributes roundHeroAttributes;

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

	public ColorBlender getColor()
	{
		return color;
	}

	public IntegerMask getHitTeamMask()
	{
		return heroTeam.getHitTeamMask(playerId);
	}

	public IntegerMask getHitEnemiesMask()
	{
		return heroTeam.getHitEnemiesMask();
	}

	public Vec2 getInitialPosition()
	{
		return spawnPosition;
	}

	public RoundHeroAttributes getRoundHeroAttributes()
	{
		return roundHeroAttributes;
	}

	public void setHeroAttributes(RoundHeroAttributes roundHeroAttributes)
	{
		this.roundHeroAttributes = roundHeroAttributes;
	}


}
