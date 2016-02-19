package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class SwingFasterAction extends GameHeroAction
{

	public SwingFasterAction(HeroStateProperties heroStatesProperties)
	{
		super(heroStatesProperties);
	}

	@Override
	public boolean childCanExecute(GameHero hero)
	{
		return (heroStateProperties.move != null);
	}

	@Override
	public void childExecute(GameHero gameHero)
	{
		Vec2 tangent = gameHero.getCollidable().getBody().getLinearVelocity().clone();
		tangent.normalize();
		tangent.mulLocal(heroStateProperties.move.acceleration);
		gameHero.getCollidableHero().applyForce(tangent);
	}

}
