package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStatePropertiesPrototype;


public class DoubleJumpAction implements GameHeroAction
{

	GameHero gameHero;
	HeroStatePropertiesPrototype heroStatesProperties;

	public DoubleJumpAction(GameHero gameHero, HeroStatePropertiesPrototype heroStatesProperties)
	{
		this.gameHero = gameHero;
		this.heroStatesProperties = heroStatesProperties;
	}
	
	@Override
	public void execute()
	{
		if (heroStatesProperties.jump != null)
		{
			gameHero.applyImpulse(new Vec2(0, -heroStatesProperties.jump.impulse / 2));
		}
	}

}
