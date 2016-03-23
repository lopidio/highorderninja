package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class VerticalJumpAction extends GameHeroAction
{

	public VerticalJumpAction(HeroStateProperties heroStatesProperties)
	{
		super(heroStatesProperties);
	}

	@Override
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return (heroStateProperties.jump != null);
	}
	
	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		Vec2 impulse = new Vec2(0, -1);
		impulse.mulLocal(heroStateProperties.jump.impulse);
		gameHero.getCollidableHero().applyImpulse(impulse);
	}

}
