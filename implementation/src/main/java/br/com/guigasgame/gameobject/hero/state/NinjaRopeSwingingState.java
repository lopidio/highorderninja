package br.com.guigasgame.gameobject.hero.state;

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
		gameHero.getCollidableHero().putOnRopeSwingingProperties();
	}
	
	@Override
	protected void stateOnQuit()
	{
		gameHero.getCollidableHero().takeOutRopeSwingingProperties();
		releaseRope();
	}
	
	protected void releaseRope()
	{
		ninjaRope.markToDestroy();
		if (!gameHero.isTouchingGround())
		{
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
		if (!ninjaRope.isAlive())
			releaseRope();
		if (!gameHero.getCollidableHero().isMoving() && 
				ninjaRope.getHookPosition().y > gameHero.getCollidableHero().getPosition().y &&
				!isHeroInputMapPressed(HeroInputKey.LEFT) && 
				!isHeroInputMapPressed(HeroInputKey.RIGHT))//avoids perfect equilibrium
		{
			gameHero.addAction(new SwingFasterAction(heroStatesProperties, gameHero.getForwardSide()));
		}
	}
	
	@Override
	protected void move(Side side)
	{
		if (gameHero.getCollidableHero().getPosition().y > ninjaRope.getHookPosition().y)
		{
			float tangentX = gameHero.getCollidableHero().getBody().getLinearVelocity().clone().x;
			
			if ((side == Side.RIGHT && tangentX >= 0) || (side == Side.LEFT && tangentX <= 0))
			{
				gameHero.addAction(new SwingFasterAction(heroStatesProperties, side));
			}
			else
			{
				gameHero.addAction(new SwingSlowerAction(heroStatesProperties));
			}
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
