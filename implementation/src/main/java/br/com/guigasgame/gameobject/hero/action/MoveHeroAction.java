package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStatesProperties;
import br.com.guigasgame.side.Side;


public class MoveHeroAction implements GameHeroAction
{

	GameHero gameHero;
	Side side;
	HeroStatesProperties heroStatesProperties;

	public MoveHeroAction(GameHero gameHero, Side side, HeroStatesProperties heroStatesProperties)
	{
		this.gameHero = gameHero;
		this.side = side;
		this.heroStatesProperties = heroStatesProperties;
	}

	@Override
	public void execute()
	{
		gameHero.setForwardSide(side);
		if (side == Side.LEFT)
			gameHero.applyForce(new Vec2(-heroStatesProperties.horizontalAcceleration, 0));
		else if (side == Side.RIGHT)
			gameHero.applyForce(new Vec2(heroStatesProperties.horizontalAcceleration, 0));
	}

}
