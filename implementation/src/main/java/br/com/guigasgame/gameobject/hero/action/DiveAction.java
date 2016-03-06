package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;

public class DiveAction extends GameHeroAction
{

	public DiveAction(HeroStateProperties heroStatesProperties)
	{
		super(heroStatesProperties);
	}

	@Override
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return heroStateProperties.property.get("diveImpulse") != null;
	}
	
	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		Vec2 impulse = new Vec2(0, heroStateProperties.property.get("diveImpulse"));
		gameHero.getCollidableHero().applyImpulse(impulse);
	}

}
