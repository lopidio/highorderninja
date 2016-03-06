package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class JumpAction extends GameHeroAction
{

	public JumpAction(HeroStateProperties heroStateProperties)
	{
		super(heroStateProperties);
	}
	
	@Override
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return (heroStateProperties.jump != null);
	}
	
	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		Vec2 impulse = new Vec2(0, -heroStateProperties.jump.impulse);
		gameHero.getCollidableHero().applyImpulse(impulse);
	}

}
