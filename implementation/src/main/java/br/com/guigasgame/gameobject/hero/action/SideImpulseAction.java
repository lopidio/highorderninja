package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.side.Side;


public class SideImpulseAction extends GameHeroAction
{

	private Side side;

	public SideImpulseAction(HeroStateProperties heroStatesProperties, Side side)
	{
		super(heroStatesProperties);
		this.side = side;
	}
	
	@Override
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return heroStateProperties.property.get("spinImpulse") != null;
	}

	@Override
	protected void childExecute(PlayableGameHero gameHero)
	{
		float impulse = heroStateProperties.property.get("spinImpulse");
		Vec2 impulseDirection = new Vec2(side.getHorizontalValue(), 0); //?
		impulseDirection.normalize();
		// Aponta para o local correto
		impulseDirection.mulLocal(impulse);

		gameHero.setForwardSide(side);
		gameHero.getCollidableHero().applyImpulse(impulseDirection);
	}

}
