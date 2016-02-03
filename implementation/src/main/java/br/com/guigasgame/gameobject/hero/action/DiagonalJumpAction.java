package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStatesProperties;
import br.com.guigasgame.side.Side;


public class DiagonalJumpAction implements GameHeroAction
{

	GameHero gameHero;
	HeroStatesProperties heroStatesProperties;
	Side side;

	public DiagonalJumpAction(GameHero gameHero, Side side, HeroStatesProperties heroStatesProperties)
	{
		this.gameHero = gameHero;
		this.heroStatesProperties = heroStatesProperties;
		this.side = side;
	}
	
	@Override
	public void execute()
	{
		if (heroStatesProperties.canJump)
		{
			Vec2 jumpDirection = new Vec2(side.getHorizontalValue(), -1);
			jumpDirection.normalize();
			// Aponta para o local correto
			jumpDirection.mulLocal(heroStatesProperties.jumpImpulse);

			gameHero.applyImpulse(jumpDirection);
		}
	}

}
