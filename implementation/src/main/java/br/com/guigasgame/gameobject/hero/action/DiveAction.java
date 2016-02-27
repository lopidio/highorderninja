package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;

public class DiveAction extends GameHeroAction
{

	public DiveAction(GameHeroAction gameHeroAction)
	{
		super(gameHeroAction);
	}

	@Override
	public boolean childCanExecute(GameHero hero)
	{
		return heroStateProperties.property.get("diveImpulse") != null;
	}
	
	@Override
	public void childExecute(GameHero gameHero)
	{
		Vec2 impulse = new Vec2(0, heroStateProperties.property.get("diveImpulse"));
		gameHero.getCollidableHero().applyImpulse(impulse);
	}

}
