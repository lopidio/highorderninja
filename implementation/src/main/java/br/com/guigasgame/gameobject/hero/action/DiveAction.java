package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;

public class DiveAction extends GameHeroAction
{

	public DiveAction(HeroStateProperties heroStateProperties)
	{
		super(heroStateProperties);
	}
	
	@Override
	public boolean canExecute(GameHero hero)
	{
		return heroStateProperties.jump != null;
	}
	
	@Override
	public void execute(GameHero gameHero)
	{
		Vec2 impulse = new Vec2(0, heroStateProperties.jump.impulse);
		gameHero.getCollidableHero().stopMovement();		
		gameHero.getCollidableHero().applyImpulse(impulse);
	}

}
