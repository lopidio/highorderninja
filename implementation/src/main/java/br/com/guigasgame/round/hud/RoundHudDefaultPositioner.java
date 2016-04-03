package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
import br.com.guigasgame.team.HeroTeam;

public class RoundHudDefaultPositioner implements RoundHudPositioner
{
	
//	private final Vector2i viewSize;
//	private final int numTeams;
	
	
	public RoundHudDefaultPositioner(Vector2i viewSize, int numTeams)
	{
//		this.viewSize = viewSize;
//		this.numTeams = numTeams;
	}
	
	@Override
	public Vector2f getTeamFragCounterPositioner(HeroTeam heroTeam)
	{
		final int teamId = heroTeam.getTeamId();
		float xPositionRatio = 0;
		switch (teamId)
		{
			case 0:
				xPositionRatio = 0.08f;
			break;
			case 1:
				xPositionRatio = 0.18f;
			break;
			case 2:
				xPositionRatio = 0.28f;
			break;
			case 3:
				xPositionRatio = 0.38f;
			break;
			case 4:
				xPositionRatio = 0.62f;
			break;
			case 5:
				xPositionRatio = 0.72f;
			break;
			case 6:
				xPositionRatio = 0.82f;
			break;
			case 7:
				xPositionRatio = 0.92f;
			break;
			default:
				break;
		}
		return new Vector2f(xPositionRatio, 0.03f);
	}

	@Override
	public Vector2f getHeroFragCounterPosition(PlayableHeroDefinition gameHeroProperties)
	{
		final int teamId = gameHeroProperties.getHeroTeam().getTeamId();
		float xPositionRatio = 0;
		switch (teamId)
		{
			case 0:
				xPositionRatio = 0.10f;
			break;
			case 1:
				xPositionRatio = 0.25f;
			break;
			case 2:
				xPositionRatio = 0.75f;
			break;
			case 3:
				xPositionRatio = 0.90f;
			break;
			default:
				break;
		}
		return new Vector2f(xPositionRatio, gameHeroProperties.getIdInTeam()*0.05f + 0.03f);
	}

	@Override
	public Vector2f getReverseTimeCounterPosition()
	{
		return new Vector2f(0.5f, 0.02f);
	}

}
