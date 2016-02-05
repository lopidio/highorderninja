package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;

public class DiveAction implements GameHeroAction
{

	GameHero gameHero;
	Vec2 impulse;	

	public DiveAction(GameHero gameHero, Vec2 impulse)
	{
		this.gameHero = gameHero;
		this.impulse = impulse;
	}
	
	@Override
	public void execute()
	{
		gameHero.getCollidableHero().stopMovement();		
		gameHero.getCollidableHero().applyImpulse(impulse);
	}

}
