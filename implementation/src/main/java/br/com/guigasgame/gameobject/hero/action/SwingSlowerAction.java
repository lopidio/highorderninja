package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class SwingSlowerAction extends GameHeroAction
{
	public SwingSlowerAction(HeroStateProperties heroStatesProperties)
	{
		super(heroStatesProperties);
	}

	@Override
	public boolean childCanExecute(RoundGameHero hero)
	{
		return (heroStateProperties.move != null);
	}

	@Override
	public void childExecute(RoundGameHero gameHero)
	{
		Vec2 tangent = gameHero.getCollidableHero().getBody().getLinearVelocity().clone();
		tangent.normalize();
		tangent.mulLocal(-heroStateProperties.move.acceleration);
		gameHero.getCollidableHero().applyForce(tangent);
	}

}
