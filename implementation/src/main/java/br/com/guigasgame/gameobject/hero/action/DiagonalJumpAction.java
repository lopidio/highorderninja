package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.side.Side;


public class DiagonalJumpAction implements GameHeroAction
{

	GameHero gameHero;
	float impulse;
	Side side;

	public DiagonalJumpAction(GameHero gameHero, float impulse, Side side)
	{
		this.gameHero = gameHero;
		this.impulse = impulse;
		this.side = side;
	}

	@Override
	public void execute()
	{
		Vec2 jumpDirection = new Vec2(side.getHorizontalValue(), -1);
		jumpDirection.normalize();
		// Aponta para o local correto
		jumpDirection.mulLocal(impulse);

		gameHero.getCollidableHero().applyImpulse(jumpDirection);
	}

}
