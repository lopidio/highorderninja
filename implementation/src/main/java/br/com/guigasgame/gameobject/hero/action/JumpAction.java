package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;


public class JumpAction implements GameHeroAction
{

	GameHero gameHero;
	Vec2 impulse;	

	public JumpAction(GameHero gameHero, Vec2 impulse)
	{
		this.gameHero = gameHero;
		this.impulse = impulse;
	}
	
	@Override
	public void execute()
	{
		gameHero.applyImpulse(impulse);
	}

}
