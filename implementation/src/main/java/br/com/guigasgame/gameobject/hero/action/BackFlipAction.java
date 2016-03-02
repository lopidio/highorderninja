package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.side.Side;


public class BackFlipAction extends GameHeroAction
{

	Side side;

	public BackFlipAction(HeroStateProperties heroStateProperties, Side side)
	{
		super(heroStateProperties);
		this.side = side;
	}
	
	@Override
	public boolean childCanExecute(RoundGameHero hero)
	{
		return heroStateProperties.property.get("impulse") != null;
	}
	
	@Override
	public void childExecute(RoundGameHero gameHero)
	{
		float impulse = heroStateProperties.property.get("impulse");
		Vec2 impulseDirection = new Vec2(side.getHorizontalValue(), -1); //?
		impulseDirection.normalize();
		impulseDirection.mulLocal(impulse);
		gameHero.getCollidableHero().applyImpulse(impulseDirection);

	}
}
