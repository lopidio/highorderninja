package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.side.Side;


public class SwingFasterAction extends GameHeroAction
{

	private final Side side;

	public SwingFasterAction(HeroStateProperties heroStatesProperties, Side side)
	{
		super(heroStatesProperties);
		this.side = side;
	}

	@Override
	public boolean childCanExecute(GameHero hero)
	{
		return (heroStateProperties.move != null);
	}

	@Override
	public void childExecute(GameHero gameHero)
	{
		Vec2 tangent = gameHero.getCollidableHero().getBody().getLinearVelocity().clone();
		if (tangent.lengthSquared() > 0)
		{
			tangent.normalize();
		}
		else
		{
			tangent = new Vec2(side.getHorizontalValue(), 0);
		}
		tangent.mulLocal(heroStateProperties.move.acceleration);
		gameHero.getCollidableHero().applyForce(tangent);
	}

}
