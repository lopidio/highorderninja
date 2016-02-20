package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class JumpPressingHelp extends GameHeroAction
{

	public JumpPressingHelp(HeroStateProperties heroStateProperties)
	{
		super(heroStateProperties);
	}
	
	@Override
	public boolean childCanExecute(GameHero hero)
	{
		return heroStateProperties.property.get("jumpPressingHelp") != null;
	}
	
	@Override
	public void childExecute(GameHero gameHero)
	{
		Vec2 force = new Vec2(0, -heroStateProperties.property.get("jumpPressingHelp"));
		gameHero.getCollidableHero().applyForce(force);
	}

}
