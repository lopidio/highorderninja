package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class DoubleJumpAction implements GameHeroAction
{

	GameHero gameHero;
	HeroStateProperties heroStatesProperties;

	public DoubleJumpAction(GameHero gameHero, HeroStateProperties heroStatesProperties)
	{
		this.gameHero = gameHero;
		this.heroStatesProperties = heroStatesProperties;
	}
	
	@Override
	public void execute()
	{
		if (heroStatesProperties.jump != null)
		{
			gameHero.getCollidableHero().applyImpulse(new Vec2(0, -heroStatesProperties.jump.impulse / 2));
		}
	}

}
