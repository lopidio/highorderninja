package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStatesProperties;


public class DoubleJumpAction implements GameHeroAction
{

	GameHero gameHero;
	HeroStatesProperties heroStatesProperties;

	public DoubleJumpAction(GameHero gameHero, HeroStatesProperties heroStatesProperties)
	{
		this.gameHero = gameHero;
		this.heroStatesProperties = heroStatesProperties;
	}
	
	@Override
	public void execute()
	{
		if (heroStatesProperties.canJump)
		{
			gameHero.applyImpulse(new Vec2(0, -heroStatesProperties.jumpImpulse / 2));
		}
	}

}
