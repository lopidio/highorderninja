package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.hero.action.SwingFasterAction;
import br.com.guigasgame.gameobject.hero.action.SwingSlowerAction;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.rope.NinjaRope;
import br.com.guigasgame.side.Side;

public class NinjaRopeSwingingState extends HeroState
{
	private NinjaRope ninjaRope;
	
	public NinjaRopeSwingingState(PlayableGameHero gameHero, NinjaRope rope)
	{
		super(gameHero, HeroStateIndex.HERO_ROPE);
		this.ninjaRope = rope; 
	}
	
	@Override
	protected void stateOnEnter()
	{
		gameHero.getCollidableHero().takeOutRopeSwingingProperties();
	}
	
	@Override
	protected void stateOnQuit()
	{
		gameHero.getCollidableHero().putRopeSwingingProperties();
		releaseRope();
	}
	
	protected void releaseRope()
	{
		ninjaRope.destroy();
		if (!gameHero.isTouchingGround())
		{
			if (gameHero.getCollidableHero().isFallingDown())
				setState(new FallingHeroState(gameHero));
			else if (gameHero.getCollidableHero().isAscending())
				setState(new JumpingHeroState(gameHero));
		}
		else if (gameHero.getCollidableHero().isMoving())
		{
			setState(new RunningHeroState(gameHero));
		}
		else
		{
			setState(new StandingHeroState(gameHero));
		}
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
		if (!gameHero.getCollidableHero().isMoving())
		{
			gameHero.addAction(new SwingFasterAction(heroStatesProperties, gameHero.getForwardSide()));
		}
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
