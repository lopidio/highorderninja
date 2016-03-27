package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;

public class RoundHudDefaultPositioner implements RoundHudPositioner
{
	
	private final Vector2i viewSize;
//	private final int numTeams;
	
	
	public RoundHudDefaultPositioner(Vector2i viewSize, int numTeams)
	{
		this.viewSize = viewSize;
//		this.numTeams = numTeams;
	}
	
	@Override
	public Vector2f getFragCounterPosition(PlayableHeroDefinition gameHeroProperties)
	{
		final int teamId = gameHeroProperties.getHeroTeam().getTeamId();
		int xPosition = 0;
		switch (teamId)
		{
			case 0:
				xPosition = 100;
			break;
			case 1:
				xPosition = 300;
			break;
			case 2:
				xPosition = viewSize.x - 100;
			break;
			case 3:
				xPosition = viewSize.x - 300;
			break;
			default:
				break;
		}
		return new Vector2f(xPosition, gameHeroProperties.getIdInTeam()*50 + 20);
	}

	@Override
	public Vector2f getReverseTimeCounterPosition()
	{
		return new Vector2f(viewSize.x/2, 10);
	}


}
