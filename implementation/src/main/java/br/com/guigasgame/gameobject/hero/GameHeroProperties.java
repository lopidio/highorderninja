package br.com.guigasgame.gameobject.hero;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;

import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.frag.RoundFragCounter;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap;
import br.com.guigasgame.team.HeroTeam;

public class GameHeroProperties
{
	private final GameHeroInputMap gameHeroInput;
	private final int maxLife;
	private final int numMaxShurikens;
	private final int numMaxSmokeBomb;
	private final int playerId;
	
	private Color color;
	private Vec2 spawnPosition;
	private HeroTeam heroTeam;
	private RoundFragCounter fragCounter;

	public GameHeroProperties(GameHeroInputMap gameHeroInput, int id)
	{
		this.gameHeroInput = gameHeroInput;
		this.playerId = id;

		maxLife = 100;
		
		numMaxShurikens = 5;
		numMaxSmokeBomb = 5;
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

	public int getMaxLife()
	{
		return maxLife;
	}
	
	public int getNumMaxShurikens()
	{
		return numMaxShurikens;
	}
	
	public int getNumMaxSmokeBomb()
	{
		return numMaxSmokeBomb;
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

	public void updateFragCounter(RoundFragCounter fragCounter)
	{
		this.fragCounter = fragCounter;
	}

	public RoundFragCounter getFragCounter()
	{
		return fragCounter;
	}
	
}
