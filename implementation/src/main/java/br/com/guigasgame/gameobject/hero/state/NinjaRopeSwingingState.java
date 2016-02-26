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

		animation.setColor(Color.CYAN);
		this.ninjaRope = rope; 
	}
	
	@Override
	protected void rope()
	{
		//do nothing
	}
	
	@Override
	protected void stateOnQuit()
	{
		releaseRope();
	}
	
	protected void releaseRope()
	{
		ninjaRope.destroy();
		setState(new StandingHeroState(gameHero));
	}
	
	@Override
	protected void stateInputReleased(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ROPE)
		{
			releaseRope();
		}
	}


//	@Override
//	protected void releaseRope()
//	{
//		super.releaseRope();
//		if (gameHero.getCollidableHero().isAscending())
//			setState(new JumpingHeroState(gameHero));
//		else if (gameHero.getCollidableHero().isFallingDown())
//			setState(new FallingHeroState(gameHero));
//			
////		if (gameHero.getCollidableHero().isTouchingGround() && !gameHero.getCollidableHero().isFallingDown())
//		{
//			setState(new StandingHeroState(gameHero));
//		}
////		else
////		{
////		}
//		ninjaRope.markToDestroy();
//	}
	
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		ninjaRope.update(deltaTime);
		Side currentSide = gameHero.getForwardSide();
		Side newSide = Side.LEFT;
		if (gameHero.getCollidableHero().getBodyLinearVelocity().x > 0)
		{
			newSide = Side.RIGHT;
		}
//		if (gameHero.isTouchingGround())
//			releaseRope();

		if (newSide != currentSide)
		{
//			gameHero.addAction(new SideOrientationHeroSetter(newSide, heroStatesProperties));
		}

	}
	
	@Override
	protected void move(Side side)
	{
		if (gameHero.getCollidableHero().getPosition().y > ninjaRope.getHookPosition().y)
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
