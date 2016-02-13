package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class JumpAction extends GameHeroAction
{

	public JumpAction(HeroStateProperties heroStateProperties)
	{
		super(heroStateProperties);
	}
	
	@Override
	public boolean childCanExecute(GameHero hero)
	{
		return (heroStateProperties.jump != null);
	}
	
	@Override
	public void childExecute(GameHero gameHero)
	{
		Vec2 impulse = new Vec2(0, -heroStateProperties.jump.impulse);
		gameHero.getCollidableHero().applyImpulse(impulse);
	}

}
