package br.com.guigasgame.gameobject.hero;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;

import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap;

public class GameHeroProperties
{
	private final GameHeroInputMap gameHeroInput;

	private final int maxLife;
	private int life;
	private final int numMaxShurikens;
	private int numShurikens;

	
	private final int playerId;
	private final int teamId;
	
	private final Color color;
	
	private final IntegerMask teamMask;
	private final IntegerMask enemiesMask;

	private final Vec2 initialPosition;

	public GameHeroProperties(GameHeroInputMap gameHeroInput, int id, Vec2 initialPosition)
	{
		this.initialPosition = initialPosition;
		this.gameHeroInput = gameHeroInput;
		this.playerId = id;
		this.teamId = id;
		teamMask = CollidableConstants.Category.getPlayerCategory(id);
		enemiesMask = CollidableConstants.Category.getOtherPlayersCategory(id);

		maxLife = 100;
		life = maxLife;
		
		numMaxShurikens = 5;
		numShurikens = numMaxShurikens;
		
		color = Color.mul(createColorFromTeamId(teamId), alterColorFromIds(id));
		
	}
	
	
	private static Color alterColorFromIds(int id)
	{
		Color retorno = Color.WHITE;
		for( int i = 0; i < id; i++ )
		{
			retorno = Color.add(retorno, Color.BLACK);
		}
		return retorno;
	}
	
	private static Color createColorFromTeamId(int teamId)
	{
		switch (teamId)
		{
			case 0:
				return Color.RED;
			case 1:
				return Color.BLUE;
			case 2:
				return Color.YELLOW;
			case 3:
				return Color.GREEN;
			case 4:
				return Color.CYAN;
			case 5:
				return Color.MAGENTA;
			case 6:
				return new Color(128, 128, 128);
			case 7:
				return new Color(255, 128, 0);
		}
		return Color.WHITE;
	}

	public GameHeroInputMap getGameHeroInput()
	{
		return gameHeroInput;
	}

	public int getMaxLife()
	{
		return maxLife;
	}
	
	public int getLife()
	{
		return life;
	}
	
	public int getNumMaxShurikens()
	{
		return numMaxShurikens;
	}
	
	public int getNumShurikens()
	{
		return numShurikens;
	}

	public int getPlayerId()
	{
		return playerId;
	}

	public int getTeamId()
	{
		return teamId;
	}

	public Color getColor()
	{
		return color;
	}

	public IntegerMask getTeamMask()
	{
		return teamMask;
	}

	public IntegerMask getEnemiesMask()
	{
		return enemiesMask;
	}


	public Vec2 getInitialPosition()
	{
		return initialPosition;
	}

}
