package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.RoundGameHero;

public class DiveAction extends GameHeroAction
{

	public DiveAction(GameHeroAction gameHeroAction)
	{
		super(gameHeroAction);
	}

	@Override
	public boolean childCanExecute(RoundGameHero hero)
	{
		return heroStateProperties.property.get("diveImpulse") != null;
	}
	
	@Override
	public void childExecute(RoundGameHero gameHero)
	{
		Vec2 impulse = new Vec2(0, heroStateProperties.property.get("diveImpulse"));
		gameHero.getCollidableHero().applyImpulse(impulse);
	}

}
