package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;

public class RoundHudDefaultPositioner implements RoundHudPositioner
{
	
	Vector2i viewSize;
	
	
	public RoundHudDefaultPositioner(Vector2i viewSize)
	{
		this.viewSize = viewSize;
	}
	
	@Override
	public Vector2f getFragCounterPosition(PlayableHeroDefinition gameHeroProperties)
	{
		return new Vector2f(gameHeroProperties.getHeroTeam().getTeamId()*200 + 100, gameHeroProperties.getIdInTeam()*50 + 50);
	}

	@Override
	public Vector2f getReverseTimeCounterPosition()
	{
		// TODO Auto-generated method stub
		return null;
	}


}
