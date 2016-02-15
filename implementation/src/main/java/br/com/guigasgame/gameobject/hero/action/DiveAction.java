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
	public boolean childCanExecute(GameHero hero)
	{
		System.out.println(heroStateProperties.property.get("diveImpulse"));
		return heroStateProperties.property.get("diveImpulse") != null;
	}
	
	@Override
	public void childExecute(GameHero gameHero)
	{
		Vec2 impulse = new Vec2(0, heroStateProperties.property.get("diveImpulse"));
		gameHero.getCollidableHero().stopMovement();		
		gameHero.getCollidableHero().applyImpulse(impulse);
	}

}
