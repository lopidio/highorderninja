package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.hero.action.SwingFasterAction;
import br.com.guigasgame.gameobject.hero.action.SwingSlowerAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.projectile.rope.NinjaRope;
import br.com.guigasgame.side.Side;

public class NinjaRopeSwingingState extends HeroState
{
	private NinjaRope ninjaRope;
	
	public NinjaRopeSwingingState(GameHero gameHero, NinjaRope rope)
	{
		super(gameHero, HeroStateIndex.HERO_ROPE);

		setAnimationsColor(Color.CYAN);
		this.ninjaRope = rope; 
	}
	
	@Override
	protected void stateOnQuit()
	{
		releaseRope();
	}
	
	protected void releaseRope()
	{
		ninjaRope.destroy();
		setState(new FallingHeroState(gameHero));
	}
	
	@Override
	protected void stateInputReleased(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ROPE)
		{
			releaseRope();
		}
	}


	@Override
	public void stateUpdate(float deltaTime)
	{
		ninjaRope.update(deltaTime);
		if (!ninjaRope.isAlive())
			releaseRope();
	}
	
	@Override
	protected void move(Side side)
	{
		Vec2 tangent = gameHero.getCollidableHero().getBody().getLinearVelocity().clone();
		
		if ((side == Side.RIGHT && tangent.x >= 0) || (side == Side.LEFT && tangent.x <= 0))
		{
			gameHero.addAction(new SwingFasterAction(heroStatesProperties, side));
		}
		else
		{
			gameHero.addAction(new SwingSlowerAction(heroStatesProperties));
		}
		
		if (gameHero.getForwardSide() != side)
			gameHero.addAction(new SideOrientationHeroSetter(side, heroStatesProperties));
	}
	
	@Override
	protected void stateInputIsPressing(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.UP)
		{
			ninjaRope.shorten();
		}
		else if (inputValue == HeroInputKey.DOWN)
		{
			ninjaRope.increase();
		}
	}

}
