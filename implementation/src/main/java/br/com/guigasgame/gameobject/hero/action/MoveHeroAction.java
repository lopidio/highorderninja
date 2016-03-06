package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.side.Side;


public class MoveHeroAction extends GameHeroAction
{

	Side side;

	public MoveHeroAction(Side side, HeroStateProperties heroStatesProperties)
	{
		super(heroStatesProperties);
		this.side = side;
	}
	
	@Override
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return (heroStateProperties.move != null);
	}

	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		gameHero.setForwardSide(side);
		if (side == Side.LEFT)
			gameHero.getCollidableHero().applyForce(new Vec2(-heroStateProperties.move.acceleration, 0));
		else if (side == Side.RIGHT)
			gameHero.getCollidableHero().applyForce(new Vec2(heroStateProperties.move.acceleration, 0));
	}

}
